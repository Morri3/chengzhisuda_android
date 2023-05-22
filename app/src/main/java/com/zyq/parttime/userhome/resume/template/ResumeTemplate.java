package com.zyq.parttime.userhome.resume.template;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zyq.parttime.R;
import com.zyq.parttime.form.Position;
import com.zyq.parttime.userhome.resume.UploadResume;

import java.util.ArrayList;
import java.util.List;

public class ResumeTemplate extends AppCompatActivity {
    private Context context;//上下文
    private RecyclerView rv; //RecyclerView布局
    private ResumeTemplateAdapter resumeTemplateAdapter;//适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//取消默认顶部标题栏
        setContentView(R.layout.activity_resume_template);
        context = this.getBaseContext();

        //获取通过意图传来的学生id
        String telephone = getIntent().getStringExtra("telephone");

        //返回图标
        ImageView back = findViewById(R.id.back);

        //↓rv、适配器配置↓
        //第一步：配置布局管理器、分割线、适配器
        rv = findViewById(R.id.rv);
        //第二步：设置布局管理器
        rv.setLayoutManager(new LinearLayoutManager(context));
        //第三步：适配器创建，这里无需传递数据
        resumeTemplateAdapter = new ResumeTemplateAdapter(context);
        //第四步：适配器绑定
        rv.setAdapter(resumeTemplateAdapter);

        //返回
        back.setOnClickListener(v -> {
            //跳转简历上传页
            Intent i = new Intent();
            i.setClass(context, UploadResume.class);
            i.putExtra("telephone", telephone);//返回简历上传页时，把传过来的学生id传回去
            startActivity(i);
        });
    }

}
