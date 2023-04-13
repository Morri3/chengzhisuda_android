package com.zyq.parttime.userhome.resume;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.zyq.parttime.HomeActivity;
import com.zyq.parttime.R;
import com.zyq.parttime.form.EditCampus;
import com.zyq.parttime.form.EditCampusDto;
import com.zyq.parttime.form.EditEducation;
import com.zyq.parttime.form.EditPersonalDto;
import com.zyq.parttime.form.EditProject;
import com.zyq.parttime.form.EditSkills;
import com.zyq.parttime.form.Resume;
import com.zyq.parttime.form.ResumeDetail;
import com.zyq.parttime.form.Student;
import com.zyq.parttime.signup.MarkActivity;
import com.zyq.parttime.util.Constants;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    //    private List<EditCampus> list = new ArrayList<>();
    private List<EditEducation> list2 = new ArrayList<>();
    private List<EditProject> list3 = new ArrayList<>();
    private List<EditSkills> list4 = new ArrayList<>();
    private int isSave = 0, isSave2 = 0, isSave3 = 0, isSave4 = 0, isSave5 = 0;//0显示text，1编辑
    private ResumeCampusAdapter adapter;
    private ResumeEducationAdapter adapter2;
    private ResumeProjectAdapter adapter3;
    private ResumeSkillsAdapter adapter4;
//    private RecyclerView rv, rv2, rv3, rv4;
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
        //刷新
        ImageView refresh = findViewById(R.id.refresh);

        //初始组件显示隐藏
        runOnUiThread(() -> {
            username.setVisibility(View.VISIBLE);
            age.setVisibility(View.VISIBLE);
            grade.setVisibility(View.VISIBLE);
            phone.setVisibility(View.VISIBLE);
            emails.setVisibility(View.VISIBLE);
            exp.setVisibility(View.VISIBLE);
            exp_edit.setVisibility(View.INVISIBLE);//隐藏
            current_area.setVisibility(View.VISIBLE);
            current_area_edit.setVisibility(View.INVISIBLE);//隐藏
            save1.setVisibility(View.INVISIBLE);//隐藏
            edit1.setVisibility(View.VISIBLE);
            save2.setVisibility(View.INVISIBLE);//隐藏
            edit2.setVisibility(View.VISIBLE);
            rv.setVisibility(View.VISIBLE);
            save3.setVisibility(View.INVISIBLE);//隐藏
            edit3.setVisibility(View.VISIBLE);
            rv2.setVisibility(View.VISIBLE);
            save4.setVisibility(View.INVISIBLE);//隐藏
            edit4.setVisibility(View.VISIBLE);
            rv3.setVisibility(View.VISIBLE);
            save5.setVisibility(View.INVISIBLE);//隐藏
            edit5.setVisibility(View.VISIBLE);
            rv4.setVisibility(View.VISIBLE);
        });

        //调api获取个人信息数据
        Student student = new Student();//学生数据

        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                //TODO 改为当前登录用户
                String telephone = Constants.telephone;
                Request request = new Request.Builder()
                        .url("http://114.55.239.213:8082/users/info/get_stu?telephone=" + telephone)
                        .get()
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
                                Log.i("data", jsonObj.getString("data"));
                                JSONObject data = JSON.parseObject(jsonObj.getString("data"));

                                //获取obj中的数据
                                String stu_name_data = data.getString("stu_name");
                                Integer gender_data = data.getInteger("gender");
                                String sno_data = data.getString("sno");
                                String telephone_data = data.getString("telephone");
                                String grade_data = data.getString("grade");
                                Integer age_data = data.getIntValue("age");
                                String emails_data = data.getString("emails");

                                student.setAge(age_data.intValue());
                                student.setEmails(emails_data);
                                student.setTelephone(telephone_data);
                                student.setStu_name(stu_name_data);
                                student.setGrade(grade_data);
                                student.setSno(sno_data);
                                student.setGender(gender_data.intValue());
                                Log.i("学生个人信息", student.toString());

                                runOnUiThread(() -> {
                                    //个人信息
                                    Log.i("stu", student.toString());
                                    username.setText(student.getStu_name());
                                    age.setText(student.getAge() + "岁");
                                    emails.setText(student.getEmails());
                                    grade.setText(student.getGrade());
                                    phone.setText(student.getTelephone());
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

        //休眠4s
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Resume resume = new Resume();//简历数据
        //获取简历信息
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                //TODO 改为当前登录用户
                String telephone = Constants.telephone;
                Request request = new Request.Builder()
                        .url("http://114.55.239.213:8082/users/resumes/get?telephone=" + telephone)
                        .get()
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
                                JSONObject jsonObj = JSON.parseObject(response.body().string());
                                Log.i("get data", jsonObj.getString("data"));
                                JSONObject data = JSON.parseObject(jsonObj.getString("data"));

                                if (data.get("memo").equals("请填写简历")) {
                                    //简历不存在
                                    Log.i("简历", "简历不存在");

                                    //当前时间
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    String now = sdf.format(new Date());

                                    FormBody.Builder params = new FormBody.Builder();
                                    params.add("telephone", telephone);
                                    params.add("upload_time", now);

                                    Request request = new Request.Builder()
                                            .url("http://114.55.239.213:8082/users/resumes/create")
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
                                            Log.i("error", "数据创建失败");
                                            e.printStackTrace();
                                        }

                                        @Override
                                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                            if (response.isSuccessful()) {//调用成功
                                                try {
                                                    JSONObject jsonObj = JSON.parseObject(response.body().string());
                                                    Log.i("data", jsonObj.getString("data"));
                                                    JSONObject data = JSON.parseObject(jsonObj.getString("data"));

                                                    //获取obj中的数据
                                                    String exp_data = data.getString("exp");
                                                    String current_area_data = data.getString("current_area");
                                                    String phone = data.getString("telephone");
                                                    resume.setTelephone(phone);

                                                    //第一个list
                                                    JSONArray arr1 = data.getJSONArray("campusExpList");
                                                    Log.i("arr1", arr1.toString());

                                                    List<EditCampus> campusList = new ArrayList<>();//存放校园经历
                                                    for (int i = 0; i < arr1.size(); i++) {
                                                        EditCampus campus = new EditCampus();//校园item

                                                        JSONObject obj = arr1.getJSONObject(i);
                                                        String category = obj.getString("category");
                                                        String telephone = obj.getString("telephone");
                                                        int hasContent = obj.getIntValue("hasContent");

                                                        if (hasContent == 1 && category.equals("校园经历")) {
                                                            //有数据
                                                            Log.i("校园", "有数据");
                                                            int rd_id = obj.getIntValue("rd_id");
                                                            String title = obj.getString("title");
                                                            String date = obj.getString("time");
                                                            String content = obj.getString("content");
                                                            String rd_status = obj.getString("status");
                                                            if (!rd_status.equals("已删除")) {
                                                                //不显示已删除的item
                                                                campus.setTelephone(telephone);
                                                                campus.setContent(content);
                                                                campus.setTitle(title);
                                                                campus.setRd_status(rd_status);
                                                                campus.setDate(date);
                                                                campus.setRd_id(rd_id);
                                                                campusList.add(campus);
                                                            }

                                                        } else if (hasContent == 0 && category.equals("校园经历")) {
                                                            //没数据
                                                            campus.setTelephone(telephone);
                                                            campus.setTitle("请输入标题");
                                                            campus.setDate("请输入日期");
                                                            campus.setContent("请输入内容");
                                                            campus.setMemo("无数据");
                                                            campusList.add(campus);
                                                        }
                                                    }


                                                    //第2个list
                                                    JSONArray arr2 = data.getJSONArray("educationBgList");
                                                    Log.i("arr2", arr2.toString());

                                                    List<EditEducation> educationList = new ArrayList<>();//存放教育背景
                                                    for (int i = 0; i < arr2.size(); i++) {
                                                        EditEducation education = new EditEducation();//item

                                                        JSONObject obj = arr2.getJSONObject(i);
                                                        String category = obj.getString("category");
                                                        String telephone = obj.getString("telephone");
                                                        int hasContent = obj.getIntValue("hasContent");
                                                        Log.i("aavcs", hasContent + "");
                                                        Log.i("ahuiash", category);
                                                        if (hasContent == 1 && category.equals("教育背景")) {
                                                            //有数据
                                                            Log.i("教育", "有数据");
                                                            int rd_id = obj.getIntValue("rd_id");
                                                            String title = obj.getString("title");
                                                            String date = obj.getString("time");
                                                            String content = obj.getString("content");
                                                            String rd_status = obj.getString("status");
                                                            if (!rd_status.equals("已删除")) {
                                                                //不显示已删除的item
                                                                education.setTelephone(telephone);
                                                                education.setContent(content);
                                                                education.setTitle(title);
                                                                education.setRd_status(rd_status);
                                                                education.setDate(date);
                                                                education.setRd_id(rd_id);
                                                                educationList.add(education);
                                                            }

                                                        } else if (hasContent == 0 && category.equals("教育背景")) {
                                                            //没数据
                                                            education.setTelephone(telephone);
                                                            education.setTitle("请输入标题");
                                                            education.setDate("请输入日期");
                                                            education.setContent("请输入内容");
                                                            education.setMemo("无数据");
                                                            educationList.add(education);
                                                        }
                                                    }
                                                    resume.setEducationBgList(educationList);
                                                    Log.i("educationList", resume.getEducationBgList().toString());


                                                    //第3个list
                                                    JSONArray arr3 = data.getJSONArray("projectExpList");
                                                    Log.i("arr3", arr3.toString());

                                                    List<EditProject> projectList = new ArrayList<>();//存放教育背景
                                                    for (int i = 0; i < arr3.size(); i++) {
                                                        EditProject project = new EditProject();//item

                                                        JSONObject obj = arr3.getJSONObject(i);
                                                        String category = obj.getString("category");
                                                        String telephone = obj.getString("telephone");
                                                        int hasContent = obj.getIntValue("hasContent");
                                                        if (hasContent == 1 && category.equals("项目经历")) {
                                                            //有数据
                                                            Log.i("项目", "有数据");
                                                            int rd_id = obj.getIntValue("rd_id");
                                                            String title = obj.getString("title");
                                                            String date = obj.getString("time");
                                                            String content = obj.getString("content");
                                                            String rd_status = obj.getString("status");
                                                            if (!rd_status.equals("已删除")) {
                                                                //不显示已删除的item
                                                                project.setTelephone(telephone);
                                                                project.setContent(content);
                                                                project.setTitle(title);
                                                                project.setRd_status(rd_status);
                                                                project.setDate(date);
                                                                project.setRd_id(rd_id);
                                                                projectList.add(project);
                                                            }

                                                        } else if (hasContent == 0 && category.equals("项目经历")) {
                                                            //没数据
                                                            project.setTelephone(telephone);
                                                            project.setTitle("请输入标题");
                                                            project.setDate("请输入日期");
                                                            project.setContent("请输入内容");
                                                            project.setMemo("无数据");
                                                            projectList.add(project);
                                                        }
                                                    }
                                                    resume.setProjectExpList(projectList);
                                                    Log.i("projectList", resume.getProjectExpList().toString());


                                                    //第4个list
                                                    JSONArray arr4 = data.getJSONArray("professionalSkillList");
                                                    Log.i("arr4", arr4.toString());

                                                    List<EditSkills> skillsList = new ArrayList<>();//存放专业技能
                                                    for (int i = 0; i < arr4.size(); i++) {
                                                        EditSkills skills = new EditSkills();//item

                                                        JSONObject obj = arr4.getJSONObject(i);
                                                        String category = obj.getString("category");
                                                        String telephone = obj.getString("telephone");
                                                        int hasContent = obj.getIntValue("hasContent");
                                                        if (hasContent == 1 && category.equals("专业技能")) {
                                                            //有数据
                                                            Log.i("专业", "有数据");
                                                            int rd_id = obj.getIntValue("rd_id");
                                                            String title = obj.getString("title");
                                                            String date = obj.getString("time");
                                                            String content = obj.getString("content");
                                                            String rd_status = obj.getString("status");
                                                            if (!rd_status.equals("已删除")) {
                                                                //不显示已删除的item
                                                                skills.setTelephone(telephone);
                                                                skills.setContent(content);
                                                                skills.setRd_status(rd_status);
                                                                skills.setRd_id(rd_id);
                                                                skillsList.add(skills);
                                                            }

                                                        } else if (hasContent == 0 && category.equals("专业技能")) {
                                                            //没数据
                                                            skills.setTelephone(telephone);
                                                            skills.setContent("请输入内容");
                                                            skills.setMemo("无数据");
                                                            skillsList.add(skills);
                                                        }
                                                    }
                                                    resume.setProfessionalSkillList(skillsList);
                                                    Log.i("skillsList", resume.getProfessionalSkillList().toString());


                                                    //经验、地址
                                                    if (exp_data == null || exp_data.equals("")) {
                                                        resume.setExp("暂无工作经验");
                                                    } else {
                                                        resume.setExp(exp_data);
                                                    }
                                                    if (current_area_data == null || current_area_data.equals("")) {
                                                        resume.setCurrent_area("暂未填写地址");
                                                    } else {
                                                        resume.setCurrent_area(current_area_data);
                                                    }
                                                    resume.setCampusExpList(campusList);
                                                    Log.i("campusList", resume.getCampusExpList().toString());
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            } else {//调用失败
                                                Log.i("error", response.toString());
                                            }
                                        }
                                    });
                                } else {
                                    //简历存在
                                    Log.i("简历", "简历存在");

                                    //获取obj中的数据
                                    String exp_data = data.getString("exp");
                                    String current_area_data = data.getString("current_area");
                                    String phone = data.getString("telephone");
                                    resume.setTelephone(phone);

                                    //第一个list
                                    JSONArray arr1 = data.getJSONArray("campusExpList");
                                    Log.i("arr1", arr1.toString());

                                    List<EditCampus> campusList = new ArrayList<>();//存放校园经历
                                    for (int i = 0; i < arr1.size(); i++) {
                                        EditCampus campus = new EditCampus();//校园item

                                        JSONObject obj = arr1.getJSONObject(i);
                                        String category = obj.getString("category");
                                        String telephone = obj.getString("telephone");
                                        int hasContent = obj.getIntValue("hasContent");

                                        if (hasContent == 1 && category.equals("校园经历")) {
                                            //有数据
                                            Log.i("校园", "有数据");
                                            int rd_id = obj.getIntValue("rd_id");
                                            String title = obj.getString("title");
                                            String date = obj.getString("time");
                                            String content = obj.getString("content");
                                            String rd_status = obj.getString("status");
                                            if (!rd_status.equals("已删除")) {
                                                //不显示已删除的item
                                                campus.setTelephone(telephone);
                                                campus.setContent(content);
                                                campus.setTitle(title);
                                                campus.setRd_status(rd_status);
                                                campus.setDate(date);
                                                campus.setRd_id(rd_id);
                                                campusList.add(campus);
                                            }

                                        } else if (hasContent == 0 && category.equals("校园经历")) {
                                            //没数据
                                            campus.setTelephone(telephone);
                                            campus.setTitle("请输入标题");
                                            campus.setDate("请输入日期");
                                            campus.setContent("请输入内容");
                                            campus.setMemo("无数据");
                                            campusList.add(campus);
                                        }
                                    }


                                    //第2个list
                                    JSONArray arr2 = data.getJSONArray("educationBgList");
                                    Log.i("arr2", arr2.toString());

                                    List<EditEducation> educationList = new ArrayList<>();//存放教育背景
                                    for (int i = 0; i < arr2.size(); i++) {
                                        EditEducation education = new EditEducation();//item

                                        JSONObject obj = arr2.getJSONObject(i);
                                        String category = obj.getString("category");
                                        String telephone = obj.getString("telephone");
                                        int hasContent = obj.getIntValue("hasContent");
                                        Log.i("aavcs", hasContent + "");
                                        Log.i("ahuiash", category);
                                        if (hasContent == 1 && category.equals("教育背景")) {
                                            //有数据
                                            Log.i("教育", "有数据");
                                            int rd_id = obj.getIntValue("rd_id");
                                            String title = obj.getString("title");
                                            String date = obj.getString("time");
                                            String content = obj.getString("content");
                                            String rd_status = obj.getString("status");
                                            if (!rd_status.equals("已删除")) {
                                                //不显示已删除的item
                                                education.setTelephone(telephone);
                                                education.setContent(content);
                                                education.setTitle(title);
                                                education.setRd_status(rd_status);
                                                education.setDate(date);
                                                education.setRd_id(rd_id);
                                                educationList.add(education);
                                            }

                                        } else if (hasContent == 0 && category.equals("教育背景")) {
                                            //没数据
                                            education.setTelephone(telephone);
                                            education.setTitle("请输入标题");
                                            education.setDate("请输入日期");
                                            education.setContent("请输入内容");
                                            education.setMemo("无数据");
                                            educationList.add(education);
                                        }
                                    }
                                    resume.setEducationBgList(educationList);
                                    Log.i("educationList", resume.getEducationBgList().toString());


                                    //第3个list
                                    JSONArray arr3 = data.getJSONArray("projectExpList");
                                    Log.i("arr3", arr3.toString());

                                    List<EditProject> projectList = new ArrayList<>();//存放教育背景
                                    for (int i = 0; i < arr3.size(); i++) {
                                        EditProject project = new EditProject();//item

                                        JSONObject obj = arr3.getJSONObject(i);
                                        String category = obj.getString("category");
                                        String telephone = obj.getString("telephone");
                                        int hasContent = obj.getIntValue("hasContent");
                                        if (hasContent == 1 && category.equals("项目经历")) {
                                            //有数据
                                            Log.i("项目", "有数据");
                                            int rd_id = obj.getIntValue("rd_id");
                                            String title = obj.getString("title");
                                            String date = obj.getString("time");
                                            String content = obj.getString("content");
                                            String rd_status = obj.getString("status");
                                            if (!rd_status.equals("已删除")) {
                                                //不显示已删除的item
                                                project.setTelephone(telephone);
                                                project.setContent(content);
                                                project.setTitle(title);
                                                project.setRd_status(rd_status);
                                                project.setDate(date);
                                                project.setRd_id(rd_id);
                                                projectList.add(project);
                                            }

                                        } else if (hasContent == 0 && category.equals("项目经历")) {
                                            //没数据
                                            project.setTelephone(telephone);
                                            project.setTitle("请输入标题");
                                            project.setDate("请输入日期");
                                            project.setContent("请输入内容");
                                            project.setMemo("无数据");
                                            projectList.add(project);
                                        }
                                    }
                                    resume.setProjectExpList(projectList);
                                    Log.i("projectList", resume.getProjectExpList().toString());


                                    //第4个list
                                    JSONArray arr4 = data.getJSONArray("professionalSkillList");
                                    Log.i("arr4", arr4.toString());

                                    List<EditSkills> skillsList = new ArrayList<>();//存放专业技能
                                    for (int i = 0; i < arr4.size(); i++) {
                                        EditSkills skills = new EditSkills();//item

                                        JSONObject obj = arr4.getJSONObject(i);
                                        String category = obj.getString("category");
                                        String telephone = obj.getString("telephone");
                                        int hasContent = obj.getIntValue("hasContent");
                                        if (hasContent == 1 && category.equals("专业技能")) {
                                            //有数据
                                            Log.i("专业", "有数据");
                                            int rd_id = obj.getIntValue("rd_id");
                                            String title = obj.getString("title");
                                            String date = obj.getString("time");
                                            String content = obj.getString("content");
                                            String rd_status = obj.getString("status");
                                            if (!rd_status.equals("已删除")) {
                                                //不显示已删除的item
                                                skills.setTelephone(telephone);
                                                skills.setContent(content);
                                                skills.setRd_status(rd_status);
                                                skills.setRd_id(rd_id);
                                                skillsList.add(skills);
                                            }

                                        } else if (hasContent == 0 && category.equals("专业技能")) {
                                            //没数据
                                            skills.setTelephone(telephone);
                                            skills.setContent("请输入内容");
                                            skills.setMemo("无数据");
                                            skillsList.add(skills);
                                        }
                                    }
                                    resume.setProfessionalSkillList(skillsList);
                                    Log.i("skillsList", resume.getProfessionalSkillList().toString());


                                    //经验、地址
                                    if (exp_data == null || exp_data.equals("")) {
                                        resume.setExp("暂无工作经验");
                                    } else {
                                        resume.setExp(exp_data);
                                    }
                                    if (current_area_data == null || current_area_data.equals("")) {
                                        resume.setCurrent_area("暂未填写地址");
                                    } else {
                                        resume.setCurrent_area(current_area_data);
                                    }
                                    resume.setCampusExpList(campusList);
                                    Log.i("campusList", resume.getCampusExpList().toString());
                                }

                                //设置list数据给adapter
                                runOnUiThread(() -> {
                                    exp.setText(resume.getExp());
                                    current_area.setText(resume.getCurrent_area());


                                    //校园经历
                                    //第一步：设置布局管理器
                                    rv.setLayoutManager(new LinearLayoutManager(context));
                                    //第二步：设置适配器
                                    adapter = new ResumeCampusAdapter(context, resume.getCampusExpList(), isSave);
                                    rv.setAdapter(adapter);
                                    ///第三步：添加动画
                                    rv.setItemAnimator(new DefaultItemAnimator());


                                    //教育背景
                                    //第一步：设置布局管理器
                                    rv2.setLayoutManager(new LinearLayoutManager(context));
                                    //第二步：设置适配器
                                    adapter2 = new ResumeEducationAdapter(context, resume.getEducationBgList(), isSave2);
                                    rv2.setAdapter(adapter2);
                                    ///第三步：添加动画
                                    rv2.setItemAnimator(new DefaultItemAnimator());


                                    //项目经历
                                    //第一步：设置布局管理器
                                    rv3.setLayoutManager(new LinearLayoutManager(context));
                                    //第二步：设置适配器
                                    adapter3 = new ResumeProjectAdapter(context, resume.getProjectExpList(), isSave3);
                                    rv3.setAdapter(adapter3);
                                    ///第三步：添加动画
                                    rv3.setItemAnimator(new DefaultItemAnimator());


                                    //专业技能
                                    //第一步：设置布局管理器
                                    rv4.setLayoutManager(new LinearLayoutManager(context));
                                    //第二步：设置适配器
                                    adapter4 = new ResumeSkillsAdapter(context, resume.getProfessionalSkillList(), isSave4);
                                    rv4.setAdapter(adapter4);
                                    ///第三步：添加动画
                                    rv4.setItemAnimator(new DefaultItemAnimator());
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

        Log.i("stu", student.toString());

        //休眠4秒,让api有充足时间获取数据，避免null
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //编辑按钮
        edit1.setOnClickListener(v -> {
            save1.setVisibility(View.VISIBLE);
            edit1.setVisibility(View.INVISIBLE);
            exp.setVisibility(View.INVISIBLE);
            exp_edit.setVisibility(View.VISIBLE);
            exp_edit.setText(resume.getExp());
            current_area.setVisibility(View.INVISIBLE);
            current_area_edit.setVisibility(View.VISIBLE);
            current_area_edit.setText(resume.getCurrent_area());
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
            resume.setExp(info1);
            resume.setCurrent_area(info2);
            Log.i("exp", info1);
            Log.i("current_area", info2);

            //调api，数据更新到DB
            new Thread(() -> {
                try {
                    OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                    //dto
                    EditPersonalDto dto = new EditPersonalDto();
                    dto.setTelephone(student.getTelephone());
                    dto.setCurrent_area(resume.getCurrent_area());
                    dto.setExp(resume.getExp());
                    String json = JSON.toJSONString(dto);//dto转json
                    Request request = new Request.Builder()
                            .url("http://114.55.239.213:8082/users/resumes/edit_personal")
                            .post(RequestBody.create(MediaType.parse("application/json"), json))
                            .build();//创建Http请求
                    client.newBuilder()
                            .connectTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
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
                                    JSONObject data = JSON.parseObject(jsonObj.getString("data"));

                                    //获取obj中的数据
                                    JSONObject info = data.getJSONObject("info");
                                    Log.i("修改", "修改成功！");
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
        });

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
            Log.i("telephone", resume.getTelephone());
            Intent i = new Intent();
            i.setClass(context, UploadResume.class);
            i.putExtra("telephone", resume.getTelephone());
            startActivity(i);
        });
        upload_text.setOnClickListener(v -> {
            Log.i("telephone", resume.getTelephone());
            Intent i = new Intent();
            i.setClass(context, UploadResume.class);
            i.putExtra("telephone", resume.getTelephone());
            startActivity(i);
        });

        //返回
        back.setOnClickListener(v -> {
            Toast toast = Toast.makeText(context, "即将跳转...", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
            toast.show();

            //跳转到个人中心页
            Intent i = new Intent();
            i.setClass(context, HomeActivity.class);
            //一定要指定是第几个pager，这里填写3
            i.putExtra("id", 3);
            startActivity(i);
        });

        //刷新
        refresh.setOnClickListener(v -> {
            Toast toast = Toast.makeText(context, "正在刷新中", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
            toast.show();

            rv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            rv2.setAdapter(adapter2);
            adapter2.notifyDataSetChanged();
            rv3.setAdapter(adapter3);
            adapter3.notifyDataSetChanged();
            rv4.setAdapter(adapter4);
            adapter4.notifyDataSetChanged();
        });

    }

    //刷新
    public void notifyData() {
//        rv.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//        rv2.setAdapter(adapter2);
//        adapter2.notifyDataSetChanged();
//        rv3.setAdapter(adapter3);
//        adapter3.notifyDataSetChanged();
//        rv4.setAdapter(adapter4);
//        adapter4.notifyDataSetChanged();
    }
}