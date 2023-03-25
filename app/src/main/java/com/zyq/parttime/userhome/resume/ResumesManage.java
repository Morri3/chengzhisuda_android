package com.zyq.parttime.userhome.resume;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.zyq.parttime.HomeActivity;
import com.zyq.parttime.R;
import com.zyq.parttime.form.EditCampus;
import com.zyq.parttime.form.EditEducation;
import com.zyq.parttime.form.EditProject;
import com.zyq.parttime.form.EditSkills;
import com.zyq.parttime.form.Student;
import com.zyq.parttime.signup.MarkActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ResumesManage extends AppCompatActivity {
    private List<EditCampus> list = new ArrayList<>();
    private List<EditEducation> list2 = new ArrayList<>();
    private List<EditProject> list3 = new ArrayList<>();
    private List<EditSkills> list4 = new ArrayList<>();
    private int isSave = 0, isSave2 = 0, isSave3 = 0, isSave4 = 0, isSave5 = 0;//0显示text，1编辑
    private ResumeCampusAdapter adapter;
    private ResumeEducationAdapter adapter2;
    private ResumeProjectAdapter adapter3;
    private ResumeSkillsAdapter adapter4;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//取消默认顶部标题栏
        setContentView(R.layout.activity_resume);
        context = this.getBaseContext();
        ImageView back = findViewById(R.id.back);
        ImageView upload = findViewById(R.id.upload);
        TextView upload_text = findViewById(R.id.upload_text);

        //个人信息
        ImageView edit1 = findViewById(R.id.edit1);
        ImageView save1 = findViewById(R.id.save1);
        TextView username = findViewById(R.id.username_resume);
        TextView age = findViewById(R.id.age_resume);
        TextView grade = findViewById(R.id.grade_resume);
        TextView phone = findViewById(R.id.telephone_resume);
        TextView emails = findViewById(R.id.emails_resume);
        TextView exp = findViewById(R.id.exp);
        EditText exp_edit = findViewById(R.id.exp_edit);
        TextView current_area = findViewById(R.id.current_area);
        EditText current_area_edit = findViewById(R.id.current_area_edit);
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
        runOnUiThread(() -> {
            username.setVisibility(View.VISIBLE);
            age.setVisibility(View.VISIBLE);
            grade.setVisibility(View.VISIBLE);
            phone.setVisibility(View.VISIBLE);
            emails.setVisibility(View.VISIBLE);
            exp.setVisibility(View.VISIBLE);
            exp_edit.setVisibility(View.INVISIBLE);
            current_area.setVisibility(View.VISIBLE);
            current_area_edit.setVisibility(View.INVISIBLE);
            save1.setVisibility(View.INVISIBLE);
            edit1.setVisibility(View.VISIBLE);
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
        });

        //调api获取个人信息数据
        Student student=new Student();//学生数据
//        getStuInfo();
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                //TODO 后面改为当前登录用户
                String telephone = "13800000001";
//                String json = "{\"telephone\":\"" + telephone + "\"}";
//                Log.i("json", json);

//                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                Request request = new Request.Builder()
                        .url("http://114.55.239.213:8080/users/info/get_stu?telephone="+telephone)
                        .get()
                        .build();//创建Http请求
//                FormBody.Builder params = new FormBody.Builder();
//                params.add("telephone",telephone);
//                Request request = new Request.Builder()
//                        .url("http://114.55.239.213:8080/users/info/get_stu")
//                        .post(params.build())
//                        .build();
                client.newBuilder().readTimeout(5000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(new Callback() {
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
                                Log.i("data",jsonObj.getString("data"));
                                JSONObject data=JSON.parseObject(jsonObj.getString("data"));

                                //获取obj中的数据
                                String stu_name=data.getString("stu_name");
                                Integer gender=data.getInteger("gender");
                                String sno=data.getString("sno");
                                String telephone=data.getString("telephone");
                                String grade=data.getString("grade");
                                Integer age=data.getIntValue("age");
                                String emails=data.getString("emails");

                                student.setAge(age.intValue());
                                student.setEmails(emails);
                                student.setTelephone(telephone);
                                student.setStu_name(stu_name);
                                student.setGrade(grade);
                                student.setSno(sno);
                                student.setGender(gender.intValue());
                                Log.i("学生个人信息",student.toString());
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
        Log.i("stu",student.toString());

        runOnUiThread(() -> {
            //休眠6秒,让api有充足时间获取数据，避免null
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //个人信息
            Log.i("stu",student.toString());
            username.setText(student.getStu_name());
            age.setText(student.getAge()+"岁");
            emails.setText(student.getEmails());
            grade.setText(student.getGrade());
            phone.setText(student.getTelephone());
            exp.setText("暂无工作经验");//默认是这个
//            current_area.setText();

            //编辑按钮
            edit1.setOnClickListener(v -> {
                save1.setVisibility(View.VISIBLE);
                edit1.setVisibility(View.INVISIBLE);
                exp.setVisibility(View.INVISIBLE);
                exp_edit.setVisibility(View.VISIBLE);
                current_area.setVisibility(View.INVISIBLE);
                current_area_edit.setVisibility(View.VISIBLE);
            });
            //保存按钮
            save1.setOnClickListener(v -> {
                save1.setVisibility(View.INVISIBLE);
                edit1.setVisibility(View.VISIBLE);
                exp.setVisibility(View.VISIBLE);
                exp_edit.setVisibility(View.INVISIBLE);
                current_area.setVisibility(View.VISIBLE);
                current_area_edit.setVisibility(View.INVISIBLE);

                String info1 = exp_edit.getText().toString();
                String info2 = current_area_edit.getText().toString();
                exp.setText(info1);
                current_area.setText(info2);
                Log.i("exp", info1);
                Log.i("current_area", info2);

                //调api，数据更新到DB

            });


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

            //上传
            upload.setOnClickListener(v -> {
                Intent i = new Intent();
                i.setClass(context, UploadResume.class);
                startActivity(i);
            });
            upload_text.setOnClickListener(v -> {
                Intent i = new Intent();
                i.setClass(context, UploadResume.class);
                startActivity(i);
            });

            back.setOnClickListener(v -> {
                //跳转到个人中心页
                Intent i = new Intent();
                i.setClass(context, HomeActivity.class);
                //一定要指定是第几个pager，这里填写2
                i.putExtra("id", 3);
                startActivity(i);
            });
        });

    }

//    private void getStuInfo() {
//        new Thread(() -> {
//            try {
//                OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
//                //TODO 后面改为当前登录用户
//                String telephone = "13800000001";
////                String json = "{\"telephone\":\"" + telephone + "\"}";
////                Log.i("json", json);
//
////                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
//                Request request = new Request.Builder()
//                        .url("http://114.55.239.213:8080/users/info/get_stu?telephone="+telephone)
//                        .get()
//                        .build();//创建Http请求
////                FormBody.Builder params = new FormBody.Builder();
////                params.add("telephone",telephone);
////                Request request = new Request.Builder()
////                        .url("http://114.55.239.213:8080/users/info/get_stu")
////                        .post(params.build())
////                        .build();
//                client.newBuilder().readTimeout(15000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(new Callback() {
//                    @Override
//                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                        Log.i("error", "数据获取失败");
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                        if (response.isSuccessful()) {//调用成功
//                            try {
////                                Log.i("数据", response.body().string());
//                                JSONObject jsonObj = JSON.parseObject(response.body().string());
//                                Log.i("data",jsonObj.getString("data"));
//                                JSONObject data=JSON.parseObject(jsonObj.getString("data"));
//
//                                //获取obj中的数据
//                                String stu_name=data.getString("stu_name");
//                                Integer gender=data.getInteger("gender");
//                                String sno=data.getString("sno");
//                                String telephone=data.getString("telephone");
//                                String grade=data.getString("grade");
//                                Integer age=data.getIntValue("age");
//                                String emails=data.getString("emails");
//
//                                student.setAge(age.intValue());
//                                student.setEmails(emails);
//                                student.setTelephone(telephone);
//                                student.setStu_name(stu_name);
//                                student.setGrade(grade);
//                                student.setSno(sno);
//                                student.setGender(gender.intValue());
//                                Log.i("学生个人信息",student.toString());
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                        } else {//调用失败
//                            Log.i("error", response.toString());
//                        }
//                    }
//                });
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }).start();//要start才会启动
//    }

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