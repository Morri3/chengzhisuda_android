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
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

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
    private Context context;
    private static final int REQUEST_TAKE_PHOTO_CODE = 2;//拍照请求
    private static final String FILE_PROVIDER_AUTHORITY = "com.zyq.parttime.fileProvider";//文件提供者名字
    private Uri imageUri;
    private ImageView pic;
    private File picFile;//传给springboot的图片文件

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
            pic.setImageResource(R.drawable.zyq_resume);
            pic.setVisibility(View.VISIBLE);//显示pic
        });

        //保存
        button_save.setOnClickListener(v -> {
            //调api，先传stu_id+upload_time
            if (picFile != null) {
                Log.i("file", picFile.getAbsolutePath());
                Log.i("file", picFile.getPath());

                new Thread(() -> {
                    try {
                        OkHttpClient client = new OkHttpClient();//创建Okhttp客户端

                        //当前时间
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String now = sdf.format(new Date());

                        FormBody.Builder params = new FormBody.Builder();
                        params.add("telephone",telephone);
                        params.add("upload_time",now);

                        Request request = new Request.Builder()
                                .url("http://114.55.239.213:8082/users/resumes/upload/stu_info")
                                .post(params.build())
                                .build();//创建Http请求
                        client.newBuilder()
                                .connectTimeout(30, TimeUnit.SECONDS)
                                .readTimeout(30, TimeUnit.SECONDS)
                                .writeTimeout(30, TimeUnit.SECONDS)
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
//                                Log.i("数据", response.body().string());
                                        JSONObject jsonObj = JSON.parseObject(response.body().string());
                                        Log.i("data", jsonObj.getString("data"));
//                                        JSONObject data = JSON.parseObject(jsonObj.getString("data"));


                                        //调第二个api，传File
                                        File theFile = new File(picFile.getPath());
                                        Log.i("theFile", theFile.getAbsolutePath() + " - " + theFile.getName());

                                        RequestBody body = new MultipartBody.Builder()
                                                .setType(MultipartBody.FORM)
                                                .addFormDataPart("file", theFile.getName(),
                                                        RequestBody.create(new File(theFile.getAbsolutePath()), MediaType.parse("multipart/form-data")))
                                                .build();

                                        Request request = new Request.Builder()
                                                .url("http://114.55.239.213:8082/users/resumes/upload")
                                                .post(body)
                                                .build();//创建Http请求

                                        client.newBuilder()
                                                .connectTimeout(60, TimeUnit.SECONDS)
                                                .readTimeout(60, TimeUnit.SECONDS)
                                                .writeTimeout(60, TimeUnit.SECONDS)
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
                                                        Log.i("data", jsonObj.getString("data"));
//                                                        JSONObject data = JSON.parseObject(jsonObj.getString("data"));

                                                        //获取obj中的数据
//                                                        Log.i("rd_id", data.getString("rd_id"));
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
//                        //dto
//                        EditProjectDto dto = new EditProjectDto();
//                        dto.setRd_id(item.getRd_id());
//                        dto.setTelephone(item.getTelephone());
//                        dto.setContent(item.getContent());
//                        dto.setTitle(item.getTitle());
//                        //时间处理
//                        String time = item.getDate();
//                        String[] a = time.split("-");
//                        dto.setStart_time(a[0]);
//                        dto.setEnd_time(a[1]);

//                        String json = JSON.toJSONString(dto);//dto转json

                        //文件
//                        MultipartFile multipartFile = FileUtils.fileToMultipartFile(picFile);

//                        //当前时间
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        String now = sdf.format(new Date());

                        // 请求参数
//                        Map<String, Object> paramsMap = new HashMap<String, Object>();
//                        paramsMap.put("files", picFile);
//                        paramsMap.put("telephone", telephone);
//                        paramsMap.put("upload_time", now);
////                        OkHttpClient client = new OkHttpClient()
////                                .newBuilder()
////                                .connectTimeout(60, TimeUnit.SECONDS) // 设置超时时间
////                                .readTimeout(60, TimeUnit.SECONDS) // 设置读取超时时间
////                                .writeTimeout(60, TimeUnit.SECONDS) // 设置写入超时时间
////                                .build();
//
//                        // 添加请求类型
//                        MultipartBody.Builder builder = new MultipartBody.Builder();
//                        builder.setType(MediaType.parse("multipart/form-data"));
//
//                        //  创建请求的请求体
//                        for (String key : paramsMap.keySet()) {
//                            // 追加表单信息
//                            Object object = paramsMap.get(key);
//                            if (object instanceof File) {
//                                File file = (File) object;
//                                builder.addFormDataPart(key, file.getName(), RequestBody.create(file, null));
//                            } else {
//                                builder.addFormDataPart(key, object.toString());
//                            }
//                        }
//                        RequestBody body = builder.build();
//
//                        // 创建request, 表单提交
//                        Request request = new Request.Builder()
//                                .url("http://114.55.239.213:8082/users/resumes/edit_program")
//                                .post(body)
//                                .build();

                        //0331
//                        File theFile = new File(picFile.getPath());
//                        Log.i("theFile", theFile.getAbsolutePath() + " - " + theFile.getName());

//                        String IMGUR_CLIENT_ID = "...";
//                        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");
//                        RequestBody requestBody = new MultipartBody.Builder()
//                                .setType(MultipartBody.FORM)
//                                .addFormDataPart("file", theFile.getAbsolutePath(),
//                                        RequestBody.create(MEDIA_TYPE_PNG, new File(theFile.getAbsolutePath())))
//                                .addFormDataPart("telephone", telephone)
//                                .addFormDataPart("upload_time", now)
//                                .build();
//                        Request request = new Request.Builder()
//                                .header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
//                                .url("http://114.55.239.213:8082/users/resumes/edit_program")
//                                .post(requestBody)
//                                .build();

                        //0331
//                        RequestBody body = new MultipartBody.Builder()
//                                .setType(MultipartBody.FORM)
////                                .addFormDataPart("file",theFile.getAbsolutePath(),
////                                    RequestBody.create(MediaType.parse("application/octet-stream"),
////                                    new File(theFile.getAbsolutePath())))
//                                .addFormDataPart("file", theFile.getName(),
//                                        RequestBody.create(new File(theFile.getAbsolutePath()), MediaType.parse("multipart/form-data")))
//                                .addFormDataPart("telephone", telephone)
//                                .addFormDataPart("upload_time", now)
//                                .build();
//
//                        //2349
//                        Request request = new Request.Builder()
//                                .url("http://114.55.239.213:8082/users/resumes/edit_program")
//                                .post(body)
//                                .build();//创建Http请求
//
//                        client.newBuilder()
//                                .connectTimeout(60, TimeUnit.SECONDS)
//                                .readTimeout(60, TimeUnit.SECONDS)
//                                .writeTimeout(60, TimeUnit.SECONDS)
//                                .build()
//                                .newCall(request).enqueue(new Callback() {
//                            @Override
//                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                                Log.i("error", "数据更新失败");
//                                e.printStackTrace();
//                            }
//
//                            @Override
//                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                                if (response.isSuccessful()) {//调用成功
//                                    try {
//                                        JSONObject jsonObj = JSON.parseObject(response.body().string());
//                                        Log.i("data", jsonObj.getString("data"));
//                                        JSONObject data = JSON.parseObject(jsonObj.getString("data"));
//
//                                        //获取obj中的数据
//                                        Log.i("rd_id", data.getString("rd_id"));
//                                        Log.i("修改", "修改成功！");
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//
//                                } else {//调用失败
//                                    Log.i("error", response.toString());
//                                }
//                            }
//                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();//要start才会启动
            }

            //返回简历页
            Intent i = new Intent();
            i.setClass(context, ResumesManage.class);
            startActivity(i);
        });

        back.setOnClickListener(v -> {

            //跳转简历页
            Intent i = new Intent();
            i.setClass(context, ResumesManage.class);
            startActivity(i);
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
            picFile = file;//赋值给全局变量
        }
    }

    //创建文件
    private File createImageFile() {
        String format = new SimpleDateFormat("yyyy-MM-DD").format(new Date());
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
                Bitmap bitmap = BitmapFactory.decodeStream(UploadResume.this
                        .getContentResolver().openInputStream(imageUri));
                pic.setImageBitmap(bitmap);//把图片set到pic上
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
