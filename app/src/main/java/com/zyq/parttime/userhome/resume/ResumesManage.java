package com.zyq.parttime.userhome.resume;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zyq.parttime.R;
import com.zyq.parttime.form.EditCampus;
import com.zyq.parttime.form.EditEducation;
import com.zyq.parttime.form.EditProject;
import com.zyq.parttime.form.EditSkills;

import java.util.ArrayList;
import java.util.List;

public class ResumesManage extends AppCompatActivity {
    private List<EditCampus> list = new ArrayList<>();
    private List<EditEducation> list2 = new ArrayList<>();
    private List<EditProject> list3 = new ArrayList<>();
    private List<EditSkills> list4 = new ArrayList<>();
    private Context context;
    private int isSave = 0, isSave2 = 0, isSave3 = 0, isSave4 = 0, isSave5 = 0;//0显示text，1编辑
    private ResumeCampusAdapter adapter;
    private ResumeEducationAdapter adapter2;
    private ResumeProjectAdapter adapter3;
    private ResumeSkillsAdapter adapter4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//取消默认顶部标题栏
        setContentView(R.layout.activity_resume);
        context = this.getBaseContext();

        //个人信息




        //校园经历
        ImageView edit2 = findViewById(R.id.edit2);
        ImageView save2 = findViewById(R.id.save2);
        RecyclerView rv = findViewById(R.id.rv_campus);
        //教育背景
        ImageView edit3 = findViewById(R.id.edit3);
        ImageView save3 = findViewById(R.id.save3);
        RecyclerView rv2 = findViewById(R.id.rv_education);
        //项目经历
        ImageView edit4 = findViewById(R.id.edit4);
        ImageView save4 = findViewById(R.id.save4);
        RecyclerView rv3 = findViewById(R.id.rv_project);
        //专业技能
        ImageView edit5 = findViewById(R.id.edit5);
        ImageView save5 = findViewById(R.id.save5);
        RecyclerView rv4 = findViewById(R.id.rv_skills);

        //初始组件显示隐藏
        save2.setVisibility(View.INVISIBLE);
        edit2.setVisibility(View.VISIBLE);
        rv.setVisibility(View.VISIBLE);
        save3.setVisibility(View.INVISIBLE);
        edit3.setVisibility(View.VISIBLE);
        rv2.setVisibility(View.VISIBLE);
        save4.setVisibility(View.INVISIBLE);
        edit4.setVisibility(View.VISIBLE);
        rv3.setVisibility(View.VISIBLE);
        save5.setVisibility(View.INVISIBLE);
        edit5.setVisibility(View.VISIBLE);
        rv4.setVisibility(View.VISIBLE);

        //获取四个list的内容
        list.clear();
        list = initData();//数据
        list2.clear();
        list2 = initData2();//数据
        list3.clear();
        list3 = initData3();//数据
        list4.clear();
        list4 = initData4();//数据

        Log.i("data", list.toString());
        Log.i("isSave0", isSave + "");

        //校园经历
        //第一步：设置布局管理器
        rv.setLayoutManager(new LinearLayoutManager(context));
        //第二步：设置适配器
        adapter = new ResumeCampusAdapter(context, list, isSave);
        rv.setAdapter(adapter);
        ///第三步：添加动画
        rv.setItemAnimator(new DefaultItemAnimator());

        //编辑按钮
        edit2.setOnClickListener(v -> {
            save2.setVisibility(View.VISIBLE);
            edit2.setVisibility(View.INVISIBLE);
            adapter.setIsSave(1);
            isSave = 1;
        });
        //保存按钮
        save2.setOnClickListener(v -> {
            save2.setVisibility(View.INVISIBLE);
            edit2.setVisibility(View.VISIBLE);
            adapter.setIsSave(0);
            isSave = 0;
        });


        //教育背景
        //第一步：设置布局管理器
        rv2.setLayoutManager(new LinearLayoutManager(context));
        //第二步：设置适配器
        adapter2 = new ResumeEducationAdapter(context, list2, isSave2);
        rv2.setAdapter(adapter2);
        ///第三步：添加动画
        rv2.setItemAnimator(new DefaultItemAnimator());

        //编辑按钮
        edit3.setOnClickListener(v -> {
            save3.setVisibility(View.VISIBLE);
            edit3.setVisibility(View.INVISIBLE);
            adapter2.setIsSave(1);
            isSave2 = 1;
        });
        //保存按钮
        save3.setOnClickListener(v -> {
            save3.setVisibility(View.INVISIBLE);
            edit3.setVisibility(View.VISIBLE);
            adapter2.setIsSave(0);
            isSave2 = 0;
        });


        //项目经历
        //第一步：设置布局管理器
        rv3.setLayoutManager(new LinearLayoutManager(context));
        //第二步：设置适配器
        adapter3 = new ResumeProjectAdapter(context, list3, isSave3);
        rv3.setAdapter(adapter3);
        ///第三步：添加动画
        rv3.setItemAnimator(new DefaultItemAnimator());

        //编辑按钮
        edit4.setOnClickListener(v -> {
            save4.setVisibility(View.VISIBLE);
            edit4.setVisibility(View.INVISIBLE);
            adapter3.setIsSave(1);
            isSave3 = 1;
        });
        //保存按钮
        save4.setOnClickListener(v -> {
            save4.setVisibility(View.INVISIBLE);
            edit4.setVisibility(View.VISIBLE);
            adapter3.setIsSave(0);
            isSave3 = 0;
        });


        //专业技能
        //第一步：设置布局管理器
        rv4.setLayoutManager(new LinearLayoutManager(context));
        //第二步：设置适配器
        adapter4 = new ResumeSkillsAdapter(context, list4, isSave4);
        rv4.setAdapter(adapter4);
        ///第三步：添加动画
        rv4.setItemAnimator(new DefaultItemAnimator());

        //编辑按钮
        edit5.setOnClickListener(v -> {
            save5.setVisibility(View.VISIBLE);
            edit5.setVisibility(View.INVISIBLE);
            adapter4.setIsSave(1);
            isSave4 = 1;
        });
        //保存按钮
        save5.setOnClickListener(v -> {
            save5.setVisibility(View.INVISIBLE);
            edit5.setVisibility(View.VISIBLE);
            adapter4.setIsSave(0);
            isSave4 = 0;
        });
    }

    private List<EditCampus> initData() {
        //调api，获取数据


        List<EditCampus> lists = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            EditCampus c = new EditCampus();
            c.setRd_id(i + 1);
            c.setTelephone("13300000001");
            c.setTitle("标题" + i);
            c.setDate("2022-01-0" + i);
            c.setContent("内容" + i);
            lists.add(c);
        }
        return lists;
    }
    private List<EditEducation> initData2() {
        //调api，获取数据


        List<EditEducation> lists = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            EditEducation c = new EditEducation();
            c.setRd_id(i + 1);
            c.setTelephone("13300000001");
            c.setTitle("标题" + i);
            c.setDate("20220" + i);
            c.setContent("内容" + i);
            lists.add(c);
        }
        return lists;
    }
    private List<EditProject> initData3() {
        //调api，获取数据


        List<EditProject> lists = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            EditProject c = new EditProject();
            c.setRd_id(i + 1);
            c.setTelephone("13300000001");
            c.setTitle("标题" + i);
            c.setDate("20000" + i);
            c.setContent("内容" + i);
            lists.add(c);
        }
        return lists;
    }
    private List<EditSkills> initData4() {
        //调api，获取数据


        List<EditSkills> lists = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            EditSkills c = new EditSkills();
            c.setRd_id(i + 1);
            c.setTelephone("13300000001");
            c.setContent("内容" + i);
            lists.add(c);
        }
        return lists;
    }
}