package com.zyq.parttime.userhome.resume;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.zyq.parttime.HomeActivity;
import com.zyq.parttime.R;
import com.zyq.parttime.form.EditProjectDto;
import com.zyq.parttime.util.FileUtils;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadResume extends AppCompatActivity {
    private Context context;//上下文
    private static final int REQUEST_TAKE_PHOTO_CODE = 2;//拍照请求
    private static final String FILE_PROVIDER_AUTHORITY = "com.zyq.parttime.fileProvider";//文件提供者名字
    private Uri imageUri;
    private ImageView pic;
    private File picFile;//传给Spring Boot的图片文件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//取消默认顶部标题栏
        setContentView(R.layout.activity_upload_resume);
        context = this.getBaseContext();

        //获取传递来的telephone
        String telephone = getIntent().getStringExtra("telephone");

        ImageView back = findViewById(R.id.back);
        ImageView button_save = findViewById(R.id.button_save);
        ImageView upload = findViewById(R.id.icon3);
        TextView title = findViewById(R.id.title3);
        View card = findViewById(R.id.card2);
        pic = findViewById(R.id.pic);

        //初始组件
        title.setVisibility(View.INVISIBLE);
        card.setVisibility(View.INVISIBLE);
        pic.setVisibility(View.INVISIBLE);

        //上传
        upload.setOnClickListener(v -> {
            //显示组件
            title.setVisibility(View.VISIBLE);
            card.setVisibility(View.VISIBLE);

            //获取简历图片
            takePhoto();
            Log.i("uri", imageUri.toString());

            //把从相册获得的图片set到pic控件
            pic.setImageResource(R.drawable.zyq_resume);//预设的简历图片
            pic.setVisibility(View.VISIBLE);//显示pic

        });

        //保存
        button_save.setOnClickListener(v -> {
            if (picFile != null) {
                //调api，先传 stu_id 和 upload_time
                new Thread(() -> {
                    try {
                        OkHttpClient client = new OkHttpClient();//创建Okhttp客户端

                        //当前时间
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String now = sdf.format(new Date());

                        FormBody.Builder params = new FormBody.Builder();
                        params.add("telephone", telephone);
                        params.add("upload_time", now);

                        Request request = new Request.Builder()
                                .url("http://114.55.239.213:8087/users/resumes/upload/stu_info")
                                .post(params.build())
                                .build();//创建Http请求
                        client.newBuilder()
                                .connectTimeout(20, TimeUnit.SECONDS)
                                .readTimeout(20, TimeUnit.SECONDS)
                                .writeTimeout(20, TimeUnit.SECONDS)
                                .build()
                                .newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                Log.i("error", "数据获取失败");
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                if (response.isSuccessful()) {//调用成功
                                    try {
                                        JSONObject jsonObj = JSON.parseObject(response.body().string());
                                        Log.i("data_upload实体", jsonObj.getString("data"));
//                                        JSONObject data = JSON.parseObject(jsonObj.getString("data"));

                                        //TODO  调第二个api，传File
                                        File theFile = new File(picFile.getPath());
                                        Log.i("theFile", theFile.getAbsolutePath() + " - " + theFile.getName());

                                        RequestBody body = new MultipartBody.Builder()
                                                .setType(MultipartBody.FORM) //表单类型
                                                .addFormDataPart("file", theFile.getName(),
                                                        RequestBody.create(new File(theFile.getAbsolutePath()), MediaType.parse("multipart/form-data")))
                                                .build();
                                        Request request = new Request.Builder()
                                                .url("http://114.55.239.213:8087/users/resumes/upload")
                                                .post(body)
                                                .build();//创建Http请求
                                        client.newBuilder()
                                                .connectTimeout(45, TimeUnit.SECONDS)
                                                .readTimeout(45, TimeUnit.SECONDS)
                                                .writeTimeout(45, TimeUnit.SECONDS)
                                                .build()
                                                .newCall(request).enqueue(new Callback() {
                                            @Override
                                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                                Log.i("error", "数据更新失败");
                                                e.printStackTrace();
                                            }

                                            @Override
                                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                                if (response.isSuccessful()) {//调用成功
                                                    try {
                                                        JSONObject jsonObj = JSON.parseObject(response.body().string());
                                                        Log.i("简历上传数据", jsonObj.getString("data"));
                                                        JSONObject data = JSON.parseObject(jsonObj.getString("data"));

                                                        if ((data.getString("memo")).equals("文字识别异常")) {
                                                            //有识别中的异常
                                                            runOnUiThread(() -> {
                                                                Toast toast = Toast.makeText(context, "请调整拍摄角度，重新拍摄~", Toast.LENGTH_LONG);
                                                                toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                                                toast.show();
                                                            });
                                                        } else if ((data.getString("memo")).equals("上传文件大小为空")) {
                                                            runOnUiThread(() -> {
                                                                Toast toast = Toast.makeText(context, "上传文件大小为空，请重试", Toast.LENGTH_LONG);
                                                                toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                                                toast.show();
                                                            });
                                                        } else if ((data.getString("memo")).equals("无法获取用户简历信息")) {
                                                            runOnUiThread(() -> {
                                                                Toast toast = Toast.makeText(context, "无法获取用户简历信息，请重试", Toast.LENGTH_LONG);
                                                                toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                                                toast.show();
                                                            });
                                                        } else {
                                                            //没有异常
                                                            runOnUiThread(() -> {
                                                                Toast toast = Toast.makeText(context, "数据处理中，请耐心等待~", Toast.LENGTH_LONG);
                                                                toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                                                toast.show();

                                                                //返回简历页
                                                                Intent i = new Intent();
                                                                i.setClass(context, ResumesManage.class);
                                                                startActivity(i);
                                                            });
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                } else {//调用失败
                                                    Log.i("error", response.toString());
                                                }
                                            }
                                        });
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                } else {//调用失败
                                    Log.i("error", response.toString());
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();//要start才会启动
            }
        });

        back.setOnClickListener(v -> {
            runOnUiThread(()->{
                //跳转简历页
                Intent i = new Intent();
                i.setClass(context, ResumesManage.class);
                startActivity(i);
            });
        });
    }

    //拍照，获取大图并存相册
    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File file = createImageFile();//创建用来保存图片的文件
            Log.e("file", file + "");

            if (file != null) {
                //7.0以上通过FileProvider将file转换成uri
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                    imageUri = FileProvider.getUriForFile(this, FILE_PROVIDER_AUTHORITY, file);
                } else {
                    imageUri = Uri.fromFile(file);
                }
            }

            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_TAKE_PHOTO_CODE);

            //赋值给全局变量
            picFile = file;
        }
    }

    //创建文件
    private File createImageFile() {
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String fileName = format + "_";//文件名
        File file = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        try {
            File imageFile = File.createTempFile(fileName, ".jpg", file);
            return imageFile;//返回添加文件名的File
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //拍照返回的地方
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
            try {
                Bitmap bitmap = BitmapFactory.
                        decodeStream(UploadResume.this.getContentResolver().openInputStream(imageUri));
                pic.setImageBitmap(bitmap);//把图片set到pic上
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
