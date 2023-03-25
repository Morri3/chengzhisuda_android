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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zyq.parttime.HomeActivity;
import com.zyq.parttime.R;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
            //调api，保存图片，传File
            Log.i("file", picFile.getAbsolutePath());
            Log.i("file", picFile.toString());

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
