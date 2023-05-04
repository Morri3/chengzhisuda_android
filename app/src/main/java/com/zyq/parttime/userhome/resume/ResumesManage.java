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
import com.zyq.parttime.form.CreateResume;
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
    private List<EditEducation> list2 = new ArrayList<>();
    private List<EditProject> list3 = new ArrayList<>();
    private List<EditSkills> list4 = new ArrayList<>();

    //判断是预览还是编辑状态：0为预览状态，1为编辑状态
    private int isSave = 0, isSave2 = 0, isSave3 = 0, isSave4 = 0, isSave5 = 0;

    //四个适配器
    private ResumeCampusAdapter adapter;
    private ResumeEducationAdapter adapter2;
    private ResumeProjectAdapter adapter3;
    private ResumeSkillsAdapter adapter4;

    //上下文
    private Context context;

    //页面中的控件
    private ImageView back, upload;
    private TextView upload_text, username, age, grade, phone, emails, exp, current_area;
    private ImageView edit1, save1, edit2, save2, edit3, save3, edit4, save4, edit5, save5, refresh, head;
    private EditText exp_edit, current_area_edit;
    private RecyclerView rv, rv2, rv3, rv4;

    //构造错误信息
    private String errMsg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//取消默认顶部标题栏
        setContentView(R.layout.activity_resume);
        context = this.getBaseContext();

        //初始组件显示隐藏
        runOnUiThread(() -> {
            //返回等图标控件
            back = findViewById(R.id.back);
            upload = findViewById(R.id.upload);
            upload_text = findViewById(R.id.upload_text);

            //个人信息
            edit1 = findViewById(R.id.edit1);
            save1 = findViewById(R.id.save1);
            username = findViewById(R.id.username_resume);
            age = findViewById(R.id.age_resume);
            grade = findViewById(R.id.grade_resume);
            phone = findViewById(R.id.telephone_resume);
            emails = findViewById(R.id.emails_resume);
            exp = findViewById(R.id.exp);
            exp_edit = findViewById(R.id.exp_edit);
            current_area = findViewById(R.id.current_area);
            current_area_edit = findViewById(R.id.current_area_edit);
            head = findViewById(R.id.head);//头像

            //校园经历
            edit2 = findViewById(R.id.edit2);
            save2 = findViewById(R.id.save2);
            rv = findViewById(R.id.rv_campus);

            //教育背景
            edit3 = findViewById(R.id.edit3);
            save3 = findViewById(R.id.save3);
            rv2 = findViewById(R.id.rv_education);

            //项目经历
            edit4 = findViewById(R.id.edit4);
            save4 = findViewById(R.id.save4);
            rv3 = findViewById(R.id.rv_project);

            //专业技能
            edit5 = findViewById(R.id.edit5);
            save5 = findViewById(R.id.save5);
            rv4 = findViewById(R.id.rv_skills);

            //刷新
            refresh = findViewById(R.id.refresh);

            //是否可见
            head.setVisibility(View.VISIBLE);
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
        Resume resume = new Resume();//简历数据
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                //TODO 改为当前登录用户
                String telephone = Constants.telephone;
                Request request = new Request.Builder()
                        .url("http://114.55.239.213:8087/users/info/get_stu?telephone=" + telephone)
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
                                Log.i("data_info", jsonObj.getString("data"));
                                JSONObject data = JSON.parseObject(jsonObj.getString("data"));

                                if ((data.getString("memo")).equals("获取成功")) {
                                    //获取obj中的数据
                                    String stu_name_data = data.getString("stu_name");
                                    int gender_data = data.getIntValue("gender");
                                    String sno_data = data.getString("sno");
                                    String telephone_data = data.getString("telephone");
                                    String grade_data = data.getString("grade");
                                    int age_data = data.getIntValue("age");
                                    String emails_data = data.getString("emails");

                                    //数据set到student对象中，供页面控件的数据更新
                                    student.setAge(age_data);
                                    student.setEmails(emails_data);
                                    student.setTelephone(telephone_data);
                                    student.setStu_name(stu_name_data);
                                    student.setGrade(grade_data);
                                    student.setSno(sno_data);
                                    student.setGender(gender_data);
                                    Log.i("学生个人信息", student.toString());

                                    //处理个人信息
                                    runOnUiThread(() -> {
                                        //根据性别显示默认头像
                                        if (gender_data == 1) {
                                            //是男生
                                            head.setImageResource(R.drawable.head1);//设置头像
                                        } else {
                                            //是女生
                                            head.setImageResource(R.drawable.head2);//设置头像
                                        }
                                        username.setText(student.getStu_name());
                                        age.setText(student.getAge() + "岁");
                                        emails.setText(student.getEmails());
                                        grade.setText(student.getGrade());
                                        phone.setText(student.getTelephone());
                                    });

                                    //获取简历信息
                                    new Thread(() -> {
                                        try {
                                            OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                                            //TODO 改为当前登录用户
                                            String telephone = Constants.telephone;
                                            Request request = new Request.Builder()
                                                    .url("http://114.55.239.213:8087/users/resumes/get?telephone=" + telephone)
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
                                                            JSONObject data = JSON.parseObject(jsonObj.getString("data"));

                                                            if (data.getString("memo").equals("不存在简历")) {
                                                                //简历不存在
                                                                Log.i("简历", "简历不存在");

                                                                //当前时间
                                                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                                String now = sdf.format(new Date());

//                                                                FormBody.Builder params = new FormBody.Builder();
//                                                                params.add("telephone", telephone);
//                                                                params.add("upload_time", now);

                                                                CreateResume createResume = new CreateResume();//DTO
                                                                createResume.setTelephone(telephone);
                                                                createResume.setUpload_time(now);
                                                                String json = JSON.toJSONString(createResume);//dto转string
                                                                Request request = new Request.Builder()
                                                                        .url("http://114.55.239.213:8087/users/resumes/create")
                                                                        .post(RequestBody.create(MediaType.parse("application/json"), json))
//                                                                        .post(params.build())
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
                                                                                Log.i("create_resume", jsonObj.getString("data"));
                                                                                JSONObject data = JSON.parseObject(jsonObj.getString("data"));

                                                                                if ((data.getString("memo")).equals("请检查输入") || (data.getString("memo")).equals("不存在该学生")) {
                                                                                    runOnUiThread(() -> {
                                                                                        Toast toast = Toast.makeText(context, "请检查输入", Toast.LENGTH_LONG);
                                                                                        toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                                                                        toast.show();
                                                                                    });

                                                                                    //构造错误提示信息
                                                                                    if ((data.getString("memo")).equals("请检查输入")) {
                                                                                        errMsg = "请检查输入";
                                                                                    } else if ((data.getString("memo")).equals("不存在该学生")) {
                                                                                        errMsg = "不存在该学生";
                                                                                    }

                                                                                    resume.setTelephone(telephone);//设置账号
                                                                                    resume.setR_id(0);//设置简历id

                                                                                    //填充个人信息
                                                                                    resume.setExp("");
                                                                                    resume.setCurrent_area("");

                                                                                    //填充简历信息
                                                                                    List<EditCampus> campusList = new ArrayList<>();//存放校园经历
                                                                                    EditCampus campus = new EditCampus();
                                                                                    campus.setTelephone(telephone);
                                                                                    campus.setTitle(errMsg);
                                                                                    campus.setDate(errMsg);
                                                                                    campus.setContent(errMsg);
                                                                                    campus.setInitial(0); //新增
                                                                                    campus.setR_id(0);
                                                                                    campus.setMemo("无数据");
                                                                                    campusList.add(campus);
                                                                                    resume.setCampusExpList(campusList);//把数据set到resume对象中，最后通过resume对象传到适配器中

                                                                                    List<EditEducation> educationList = new ArrayList<>();//存放教育背景
                                                                                    EditEducation education = new EditEducation();//item
                                                                                    education.setTelephone(telephone);
                                                                                    education.setTitle(errMsg);
                                                                                    education.setDate(errMsg);
                                                                                    education.setContent(errMsg);
                                                                                    education.setInitial(0); //新增
                                                                                    education.setR_id(0);
                                                                                    education.setMemo("无数据");
                                                                                    educationList.add(education);
                                                                                    resume.setEducationBgList(educationList);//把数据set到resume对象中，最后通过resume对象传到适配器中

                                                                                    List<EditProject> projectList = new ArrayList<>();//存放教育背景
                                                                                    EditProject project = new EditProject();//item
                                                                                    project.setTelephone(telephone);
                                                                                    project.setTitle(errMsg);
                                                                                    project.setDate(errMsg);
                                                                                    project.setContent(errMsg);
                                                                                    project.setInitial(0); //新增
                                                                                    project.setR_id(0);
                                                                                    project.setMemo("无数据");
                                                                                    projectList.add(project);
                                                                                    resume.setProjectExpList(projectList);//把数据set到resume对象中，最后通过resume对象传到适配器中

                                                                                    List<EditSkills> skillsList = new ArrayList<>();//存放专业技能
                                                                                    EditSkills skills = new EditSkills();//item
                                                                                    skills.setTelephone(telephone);
                                                                                    skills.setContent(errMsg);
                                                                                    skills.setInitial(0); //新增
                                                                                    skills.setR_id(0);
                                                                                    skills.setMemo("无数据");
                                                                                    skillsList.add(skills);
                                                                                    resume.setProfessionalSkillList(skillsList);//把数据set到resume对象中，最后通过resume对象传到适配器中

                                                                                } else if ((data.getString("memo")).equals("存在学生，简历创建成功")) {
                                                                                    //获取obj中的数据
                                                                                    String exp_data = data.getString("exp");
                                                                                    String current_area_data = data.getString("current_area");
                                                                                    String phone = data.getString("telephone");
                                                                                    int r_id = data.getIntValue("r_id");
                                                                                    resume.setTelephone(phone);
                                                                                    resume.setR_id(r_id);//设置简历id，供添加删除detail时用

                                                                                    //第一个list
                                                                                    JSONArray arr1 = data.getJSONArray("campusExpList");

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
                                                                                                campus.setR_id(r_id);
                                                                                                campus.setRd_id(rd_id);
                                                                                                campus.setInitial(1); //是原来就有的
                                                                                                campusList.add(campus);
                                                                                            }

                                                                                        } else if (hasContent == 0 && category.equals("校园经历")) {
                                                                                            //没数据
                                                                                            campus.setTelephone(telephone);
                                                                                            campus.setTitle("请输入标题");
                                                                                            campus.setDate("请输入日期");
                                                                                            campus.setContent("请输入内容");
                                                                                            campus.setInitial(0); //新增
                                                                                            campus.setR_id(r_id);//简历id
                                                                                            campus.setMemo("无数据");
                                                                                            campusList.add(campus);
                                                                                        }
                                                                                    }

                                                                                    //第2个list
                                                                                    JSONArray arr2 = data.getJSONArray("educationBgList");

                                                                                    List<EditEducation> educationList = new ArrayList<>();//存放教育背景
                                                                                    for (int i = 0; i < arr2.size(); i++) {
                                                                                        EditEducation education = new EditEducation();//item

                                                                                        JSONObject obj = arr2.getJSONObject(i);
                                                                                        String category = obj.getString("category");
                                                                                        String telephone = obj.getString("telephone");
                                                                                        int hasContent = obj.getIntValue("hasContent");

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
                                                                                                education.setR_id(r_id);
                                                                                                education.setInitial(1); //是原来就有的
                                                                                                educationList.add(education);
                                                                                            }

                                                                                        } else if (hasContent == 0 && category.equals("教育背景")) {
                                                                                            //没数据
                                                                                            education.setTelephone(telephone);
                                                                                            education.setTitle("请输入标题");
                                                                                            education.setDate("请输入日期");
                                                                                            education.setContent("请输入内容");
                                                                                            education.setInitial(0); //新增
                                                                                            education.setR_id(r_id);//简历id
                                                                                            education.setMemo("无数据");
                                                                                            educationList.add(education);
                                                                                        }
                                                                                    }
                                                                                    resume.setEducationBgList(educationList);
                                                                                    Log.i("educationList", resume.getEducationBgList().toString());

                                                                                    //第3个list
                                                                                    JSONArray arr3 = data.getJSONArray("projectExpList");

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
                                                                                                project.setR_id(r_id);
                                                                                                project.setInitial(1); //是原来就有的
                                                                                                projectList.add(project);
                                                                                            }

                                                                                        } else if (hasContent == 0 && category.equals("项目经历")) {
                                                                                            //没数据
                                                                                            project.setTelephone(telephone);
                                                                                            project.setTitle("请输入标题");
                                                                                            project.setDate("请输入日期");
                                                                                            project.setContent("请输入内容");
                                                                                            project.setInitial(0); //新增
                                                                                            project.setR_id(r_id);//简历id
                                                                                            project.setMemo("无数据");
                                                                                            projectList.add(project);
                                                                                        }
                                                                                    }
                                                                                    resume.setProjectExpList(projectList);
                                                                                    Log.i("projectList", resume.getProjectExpList().toString());

                                                                                    //第4个list
                                                                                    JSONArray arr4 = data.getJSONArray("professionalSkillList");

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
                                                                                            String content = obj.getString("content");
                                                                                            String rd_status = obj.getString("status");

                                                                                            if (!rd_status.equals("已删除")) {
                                                                                                //不显示已删除的item
                                                                                                skills.setTelephone(telephone);
                                                                                                skills.setContent(content);
                                                                                                skills.setRd_status(rd_status);
                                                                                                skills.setRd_id(rd_id);
                                                                                                skills.setR_id(r_id);
                                                                                                skills.setInitial(1); //是原来就有的
                                                                                                skillsList.add(skills);
                                                                                            }
                                                                                        } else if (hasContent == 0 && category.equals("专业技能")) {
                                                                                            //没数据
                                                                                            skills.setTelephone(telephone);
                                                                                            skills.setContent("请输入内容");
                                                                                            skills.setInitial(0); //新增
                                                                                            skills.setR_id(r_id);//简历id
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


                                                                                    //1.校园经历
                                                                                    //第一步：设置布局管理器
                                                                                    rv.setLayoutManager(new LinearLayoutManager(context));
                                                                                    //第二步：设置适配器
                                                                                    adapter = new ResumeCampusAdapter(context, resume.getCampusExpList(), isSave);
                                                                                    rv.setAdapter(adapter);
                                                                                    ///第三步：添加动画
                                                                                    rv.setItemAnimator(new DefaultItemAnimator());


                                                                                    //2.教育背景
                                                                                    //第一步：设置布局管理器
                                                                                    rv2.setLayoutManager(new LinearLayoutManager(context));
                                                                                    //第二步：设置适配器
                                                                                    adapter2 = new ResumeEducationAdapter(context, resume.getEducationBgList(), isSave2);
                                                                                    rv2.setAdapter(adapter2);
                                                                                    ///第三步：添加动画
                                                                                    rv2.setItemAnimator(new DefaultItemAnimator());


                                                                                    //3.项目经历
                                                                                    //第一步：设置布局管理器
                                                                                    rv3.setLayoutManager(new LinearLayoutManager(context));
                                                                                    //第二步：设置适配器
                                                                                    adapter3 = new ResumeProjectAdapter(context, resume.getProjectExpList(), isSave3);
                                                                                    rv3.setAdapter(adapter3);
                                                                                    ///第三步：添加动画
                                                                                    rv3.setItemAnimator(new DefaultItemAnimator());


                                                                                    //4.专业技能
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
                                                            } else {
                                                                //简历存在
                                                                Log.i("简历", "简历存在");

                                                                //获取obj中的数据
                                                                String exp_data = data.getString("exp");
                                                                String current_area_data = data.getString("current_area");
                                                                String phone = data.getString("telephone");
                                                                int r_id = data.getIntValue("r_id");
                                                                resume.setTelephone(phone);
                                                                resume.setR_id(r_id);//设置简历id，供添加删除detail时用

                                                                //第一个list
                                                                JSONArray arr1 = data.getJSONArray("campusExpList");

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
                                                                            campus.setR_id(r_id);
                                                                            campus.setInitial(1); //是原来就有的
                                                                            campusList.add(campus);
                                                                        }

                                                                    } else if (hasContent == 0 && category.equals("校园经历")) {
                                                                        //没数据
                                                                        campus.setTelephone(telephone);
                                                                        campus.setTitle("请输入标题");
                                                                        campus.setDate("请输入日期");
                                                                        campus.setContent("请输入内容");
                                                                        campus.setInitial(0); //新增
                                                                        campus.setR_id(r_id);
                                                                        campus.setMemo("无数据");
                                                                        campusList.add(campus);
                                                                    }
                                                                }
                                                                resume.setCampusExpList(campusList);
                                                                Log.i("campusList", resume.getCampusExpList().toString());

                                                                //第2个list
                                                                JSONArray arr2 = data.getJSONArray("educationBgList");

                                                                List<EditEducation> educationList = new ArrayList<>();//存放教育背景
                                                                for (int i = 0; i < arr2.size(); i++) {
                                                                    EditEducation education = new EditEducation();//item

                                                                    JSONObject obj = arr2.getJSONObject(i);
                                                                    String category = obj.getString("category");
                                                                    String telephone = obj.getString("telephone");
                                                                    int hasContent = obj.getIntValue("hasContent");

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
                                                                            education.setR_id(r_id);
                                                                            education.setRd_id(rd_id);
                                                                            education.setInitial(1); //是原来就有的
                                                                            educationList.add(education);
                                                                        }

                                                                    } else if (hasContent == 0 && category.equals("教育背景")) {
                                                                        //没数据
                                                                        education.setTelephone(telephone);
                                                                        education.setTitle("请输入标题");
                                                                        education.setDate("请输入日期");
                                                                        education.setContent("请输入内容");
                                                                        education.setInitial(0); //新增
                                                                        education.setR_id(r_id);
                                                                        education.setMemo("无数据");
                                                                        educationList.add(education);
                                                                    }
                                                                }
                                                                resume.setEducationBgList(educationList);
                                                                Log.i("educationList", resume.getEducationBgList().toString());


                                                                //第3个list
                                                                JSONArray arr3 = data.getJSONArray("projectExpList");

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
                                                                            project.setR_id(r_id);
                                                                            project.setInitial(1); //是原来就有的
                                                                            projectList.add(project);
                                                                        }

                                                                    } else if (hasContent == 0 && category.equals("项目经历")) {
                                                                        //没数据
                                                                        project.setTelephone(telephone);
                                                                        project.setTitle("请输入标题");
                                                                        project.setDate("请输入日期");
                                                                        project.setContent("请输入内容");
                                                                        project.setInitial(0); //新增
                                                                        project.setR_id(r_id);
                                                                        project.setMemo("无数据");
                                                                        projectList.add(project);
                                                                    }
                                                                }
                                                                resume.setProjectExpList(projectList);
                                                                Log.i("projectList", resume.getProjectExpList().toString());


                                                                //第4个list
                                                                JSONArray arr4 = data.getJSONArray("professionalSkillList");

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
                                                                        String content = obj.getString("content");
                                                                        String rd_status = obj.getString("status");

                                                                        if (!rd_status.equals("已删除")) {
                                                                            //不显示已删除的item
                                                                            skills.setTelephone(telephone);
                                                                            skills.setContent(content);
                                                                            skills.setRd_status(rd_status);
                                                                            skills.setRd_id(rd_id);
                                                                            skills.setR_id(r_id);
                                                                            skills.setInitial(1); //是原来就有的
                                                                            skillsList.add(skills);
                                                                        }

                                                                    } else if (hasContent == 0 && category.equals("专业技能")) {
                                                                        //没数据
                                                                        skills.setTelephone(telephone);
                                                                        skills.setContent("请输入内容");
                                                                        skills.setInitial(0); //新增
                                                                        skills.setR_id(r_id);
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

                                                                //设置list数据给adapter
                                                                runOnUiThread(() -> {
                                                                    exp.setText(resume.getExp());
                                                                    current_area.setText(resume.getCurrent_area());


                                                                    //1.校园经历
                                                                    //第一步：设置布局管理器
                                                                    rv.setLayoutManager(new LinearLayoutManager(context));
                                                                    //第二步：设置适配器
                                                                    adapter = new ResumeCampusAdapter(context, resume.getCampusExpList(), isSave);
                                                                    rv.setAdapter(adapter);
                                                                    ///第三步：添加动画
                                                                    rv.setItemAnimator(new DefaultItemAnimator());


                                                                    //2.教育背景
                                                                    //第一步：设置布局管理器
                                                                    rv2.setLayoutManager(new LinearLayoutManager(context));
                                                                    //第二步：设置适配器
                                                                    adapter2 = new ResumeEducationAdapter(context, resume.getEducationBgList(), isSave2);
                                                                    rv2.setAdapter(adapter2);
                                                                    ///第三步：添加动画
                                                                    rv2.setItemAnimator(new DefaultItemAnimator());


                                                                    //3.项目经历
                                                                    //第一步：设置布局管理器
                                                                    rv3.setLayoutManager(new LinearLayoutManager(context));
                                                                    //第二步：设置适配器
                                                                    adapter3 = new ResumeProjectAdapter(context, resume.getProjectExpList(), isSave3);
                                                                    rv3.setAdapter(adapter3);
                                                                    ///第三步：添加动画
                                                                    rv3.setItemAnimator(new DefaultItemAnimator());


                                                                    //4.专业技能
                                                                    //第一步：设置布局管理器
                                                                    rv4.setLayoutManager(new LinearLayoutManager(context));
                                                                    //第二步：设置适配器
                                                                    adapter4 = new ResumeSkillsAdapter(context, resume.getProfessionalSkillList(), isSave4);
                                                                    rv4.setAdapter(adapter4);
                                                                    ///第三步：添加动画
                                                                    rv4.setItemAnimator(new DefaultItemAnimator());
                                                                });
                                                            }

                                                            Log.i("学生信息2", student.toString());
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

                                } else if ((data.getString("memo")).equals("该账号不存在")) {
                                    errMsg = "不存在用户";

                                    //填充简历信息
                                    List<EditCampus> campusList = new ArrayList<>();//存放校园经历
                                    EditCampus campus = new EditCampus();
                                    campus.setTelephone(telephone);
                                    campus.setTitle("不存在用户");
                                    campus.setDate("不存在用户");
                                    campus.setContent("不存在用户");
                                    campus.setInitial(0); //新增
                                    campus.setR_id(0);
                                    campus.setMemo("无数据");
                                    campusList.add(campus);
                                    resume.setCampusExpList(campusList);

                                    List<EditEducation> educationList = new ArrayList<>();//存放教育背景
                                    EditEducation education = new EditEducation();//item
                                    education.setTelephone(telephone);
                                    education.setTitle("不存在用户");
                                    education.setDate("不存在用户");
                                    education.setContent("不存在用户");
                                    education.setInitial(0); //新增
                                    education.setR_id(0);
                                    education.setMemo("无数据");
                                    educationList.add(education);
                                    resume.setEducationBgList(educationList);

                                    List<EditProject> projectList = new ArrayList<>();//存放教育背景
                                    EditProject project = new EditProject();//item
                                    project.setTelephone(telephone);
                                    project.setTitle("不存在用户");
                                    project.setDate("不存在用户");
                                    project.setContent("不存在用户");
                                    project.setInitial(0); //新增
                                    project.setR_id(0);
                                    project.setMemo("无数据");
                                    projectList.add(project);
                                    resume.setProjectExpList(projectList);

                                    List<EditSkills> skillsList = new ArrayList<>();//存放专业技能
                                    EditSkills skills = new EditSkills();//item
                                    skills.setTelephone(telephone);
                                    skills.setContent("不存在用户");
                                    skills.setInitial(0); //新增
                                    skills.setR_id(0);
                                    skills.setMemo("无数据");
                                    skillsList.add(skills);
                                    resume.setProfessionalSkillList(skillsList);

                                    runOnUiThread(() -> {
                                        //填充个人信息
                                        head.setImageResource(R.drawable.head1);//设置头像
                                        username.setText(errMsg);
                                        age.setText("0岁");
                                        emails.setText(errMsg);
                                        grade.setText(errMsg);
                                        phone.setText(errMsg);
                                        exp.setText("");
                                        current_area.setText("");

                                        //设置list数据给adapter
                                        //1.校园经历
                                        //第一步：设置布局管理器
                                        rv.setLayoutManager(new LinearLayoutManager(context));
                                        //第二步：设置适配器
                                        adapter = new ResumeCampusAdapter(context, resume.getCampusExpList(), isSave);
                                        rv.setAdapter(adapter);
                                        ///第三步：添加动画
                                        rv.setItemAnimator(new DefaultItemAnimator());

                                        //2.教育背景
                                        //第一步：设置布局管理器
                                        rv2.setLayoutManager(new LinearLayoutManager(context));
                                        //第二步：设置适配器
                                        adapter2 = new ResumeEducationAdapter(context, resume.getEducationBgList(), isSave2);
                                        rv2.setAdapter(adapter2);
                                        ///第三步：添加动画
                                        rv2.setItemAnimator(new DefaultItemAnimator());

                                        //3.项目经历
                                        //第一步：设置布局管理器
                                        rv3.setLayoutManager(new LinearLayoutManager(context));
                                        //第二步：设置适配器
                                        adapter3 = new ResumeProjectAdapter(context, resume.getProjectExpList(), isSave3);
                                        rv3.setAdapter(adapter3);
                                        ///第三步：添加动画
                                        rv3.setItemAnimator(new DefaultItemAnimator());

                                        //4.专业技能
                                        //第一步：设置布局管理器
                                        rv4.setLayoutManager(new LinearLayoutManager(context));
                                        //第二步：设置适配器
                                        adapter4 = new ResumeSkillsAdapter(context, resume.getProfessionalSkillList(), isSave4);
                                        rv4.setAdapter(adapter4);
                                        ///第三步：添加动画
                                        rv4.setItemAnimator(new DefaultItemAnimator());

                                        //刷新
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

        //编辑按钮
        edit1.setOnClickListener(v -> {
            runOnUiThread(() -> {
                save1.setVisibility(View.VISIBLE);
                edit1.setVisibility(View.INVISIBLE);
                exp.setVisibility(View.INVISIBLE);
                exp_edit.setVisibility(View.VISIBLE);
                exp_edit.setText(resume.getExp());
                current_area.setVisibility(View.INVISIBLE);
                current_area_edit.setVisibility(View.VISIBLE);
                current_area_edit.setText(resume.getCurrent_area());
            });
        });

        //保存按钮
        save1.setOnClickListener(v -> {
            runOnUiThread(() -> {
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
            });

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
                            .url("http://114.55.239.213:8087/users/resumes/edit_personal")
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
//                                    JSONObject jsonObj = JSON.parseObject(response.body().string());
//                                    Log.i("data", jsonObj.getString("data"));
//                                    JSONObject data = JSON.parseObject(jsonObj.getString("data"));
//
//                                    //获取obj中的数据
//                                    JSONObject info = data.getJSONObject("info");
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
            runOnUiThread(() -> {
                save2.setVisibility(View.VISIBLE);
                edit2.setVisibility(View.INVISIBLE);
                adapter.setIsSave(1);
                isSave = 1;
            });
        });
        //保存按钮
        save2.setOnClickListener(v -> {
            runOnUiThread(() -> {
                save2.setVisibility(View.INVISIBLE);
                edit2.setVisibility(View.VISIBLE);
                adapter.setIsSave(0);
                isSave = 0;
            });
        });

        //编辑按钮
        edit3.setOnClickListener(v -> {
            runOnUiThread(() -> {
                save3.setVisibility(View.VISIBLE);
                edit3.setVisibility(View.INVISIBLE);
                adapter2.setIsSave(1);
                isSave2 = 1;
            });
        });
        //保存按钮
        save3.setOnClickListener(v -> {
            runOnUiThread(() -> {
                save3.setVisibility(View.INVISIBLE);
                edit3.setVisibility(View.VISIBLE);
                adapter2.setIsSave(0);
                isSave2 = 0;
            });

        });

        //编辑按钮
        edit4.setOnClickListener(v -> {
            runOnUiThread(() -> {
                save4.setVisibility(View.VISIBLE);
                edit4.setVisibility(View.INVISIBLE);
                adapter3.setIsSave(1);
                isSave3 = 1;
            });
        });
        //保存按钮
        save4.setOnClickListener(v -> {
            runOnUiThread(() -> {
                save4.setVisibility(View.INVISIBLE);
                edit4.setVisibility(View.VISIBLE);
                adapter3.setIsSave(0);
                isSave3 = 0;
            });
        });

        //编辑按钮
        edit5.setOnClickListener(v -> {
            runOnUiThread(() -> {
                save5.setVisibility(View.VISIBLE);
                edit5.setVisibility(View.INVISIBLE);
                adapter4.setIsSave(1);
                isSave4 = 1;
            });
        });
        //保存按钮
        save5.setOnClickListener(v -> {
            runOnUiThread(() -> {
                save5.setVisibility(View.INVISIBLE);
                edit5.setVisibility(View.VISIBLE);
                adapter4.setIsSave(0);
                isSave4 = 0;
            });
        });

        //上传
        upload.setOnClickListener(v -> {
            runOnUiThread(() -> {
                Log.i("telephone", resume.getTelephone());
                Intent i = new Intent();
                i.setClass(context, UploadResume.class);
                i.putExtra("telephone", resume.getTelephone());
                startActivity(i);
            });
        });
        //上传
        upload_text.setOnClickListener(v -> {
            runOnUiThread(() -> {
                Log.i("telephone", resume.getTelephone());
                Intent i = new Intent();
                i.setClass(context, UploadResume.class);
                i.putExtra("telephone", resume.getTelephone());
                startActivity(i);
            });
        });

        //返回
        back.setOnClickListener(v -> {
            runOnUiThread(() -> {
//                Toast toast = Toast.makeText(context, "数据加载中，请稍等片刻~", Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
//                toast.show();

                //跳转到个人中心页
                Intent i = new Intent();
                i.setClass(context, HomeActivity.class);
                //一定要指定是第几个pager，这里填写3
                i.putExtra("id", 3);
                startActivity(i);
            });
        });

        //刷新
        refresh.setOnClickListener(v -> {
            runOnUiThread(() -> {
                Toast toast = Toast.makeText(context, "正在刷新中，请稍等片刻~", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                toast.show();
            });

            //获取简历信息
            new Thread(() -> {
                try {
                    OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                    //TODO 改为当前登录用户
                    String telephone = Constants.telephone;
                    Request request = new Request.Builder()
                            .url("http://114.55.239.213:8087/users/resumes/get?telephone=" + telephone)
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
                                    JSONObject data = JSON.parseObject(jsonObj.getString("data"));

                                    if (data.getString("memo").equals("存在简历")) {
                                        //简历存在
                                        Log.i("简历", "简历存在");

                                        //获取obj中的数据
                                        String exp_data = data.getString("exp");
                                        String current_area_data = data.getString("current_area");
                                        String phone = data.getString("telephone");
                                        int r_id = data.getIntValue("r_id");
                                        resume.setTelephone(phone);
                                        resume.setR_id(r_id);//设置简历id，供添加删除detail时用

                                        //第一个list
                                        JSONArray arr1 = data.getJSONArray("campusExpList");

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
                                                    campus.setR_id(r_id);
                                                    campus.setInitial(1); //是原来就有的
                                                    campusList.add(campus);
                                                }

                                            } else if (hasContent == 0 && category.equals("校园经历")) {
                                                //没数据
                                                campus.setTelephone(telephone);
                                                campus.setTitle("请输入标题");
                                                campus.setDate("请输入日期");
                                                campus.setContent("请输入内容");
                                                campus.setInitial(0); //新增
                                                campus.setR_id(r_id);
                                                campus.setMemo("无数据");
                                                campusList.add(campus);
                                            }
                                        }
                                        resume.setCampusExpList(campusList);
                                        Log.i("campusList", resume.getCampusExpList().toString());

                                        //第2个list
                                        JSONArray arr2 = data.getJSONArray("educationBgList");

                                        List<EditEducation> educationList = new ArrayList<>();//存放教育背景
                                        for (int i = 0; i < arr2.size(); i++) {
                                            EditEducation education = new EditEducation();//item

                                            JSONObject obj = arr2.getJSONObject(i);
                                            String category = obj.getString("category");
                                            String telephone = obj.getString("telephone");
                                            int hasContent = obj.getIntValue("hasContent");

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
                                                    education.setR_id(r_id);
                                                    education.setRd_id(rd_id);
                                                    education.setInitial(1); //是原来就有的
                                                    educationList.add(education);
                                                }

                                            } else if (hasContent == 0 && category.equals("教育背景")) {
                                                //没数据
                                                education.setTelephone(telephone);
                                                education.setTitle("请输入标题");
                                                education.setDate("请输入日期");
                                                education.setContent("请输入内容");
                                                education.setInitial(0); //新增
                                                education.setR_id(r_id);
                                                education.setMemo("无数据");
                                                educationList.add(education);
                                            }
                                        }
                                        resume.setEducationBgList(educationList);
                                        Log.i("educationList", resume.getEducationBgList().toString());


                                        //第3个list
                                        JSONArray arr3 = data.getJSONArray("projectExpList");

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
                                                    project.setR_id(r_id);
                                                    project.setInitial(1); //是原来就有的
                                                    projectList.add(project);
                                                }

                                            } else if (hasContent == 0 && category.equals("项目经历")) {
                                                //没数据
                                                project.setTelephone(telephone);
                                                project.setTitle("请输入标题");
                                                project.setDate("请输入日期");
                                                project.setContent("请输入内容");
                                                project.setInitial(0); //新增
                                                project.setR_id(r_id);
                                                project.setMemo("无数据");
                                                projectList.add(project);
                                            }
                                        }
                                        resume.setProjectExpList(projectList);
                                        Log.i("projectList", resume.getProjectExpList().toString());


                                        //第4个list
                                        JSONArray arr4 = data.getJSONArray("professionalSkillList");

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
                                                String content = obj.getString("content");
                                                String rd_status = obj.getString("status");

                                                if (!rd_status.equals("已删除")) {
                                                    //不显示已删除的item
                                                    skills.setTelephone(telephone);
                                                    skills.setContent(content);
                                                    skills.setRd_status(rd_status);
                                                    skills.setRd_id(rd_id);
                                                    skills.setR_id(r_id);
                                                    skills.setInitial(1); //是原来就有的
                                                    skillsList.add(skills);
                                                }

                                            } else if (hasContent == 0 && category.equals("专业技能")) {
                                                //没数据
                                                skills.setTelephone(telephone);
                                                skills.setContent("请输入内容");
                                                skills.setInitial(0); //新增
                                                skills.setR_id(r_id);
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

                                        //设置list数据给adapter
                                        runOnUiThread(() -> {
                                            exp.setText(resume.getExp());
                                            current_area.setText(resume.getCurrent_area());


                                            //1.校园经历
                                            //第一步：设置布局管理器
                                            rv.setLayoutManager(new LinearLayoutManager(context));
                                            //第二步：设置适配器
                                            adapter = new ResumeCampusAdapter(context, resume.getCampusExpList(), isSave);
                                            rv.setAdapter(adapter);
                                            ///第三步：添加动画
                                            rv.setItemAnimator(new DefaultItemAnimator());


                                            //2.教育背景
                                            //第一步：设置布局管理器
                                            rv2.setLayoutManager(new LinearLayoutManager(context));
                                            //第二步：设置适配器
                                            adapter2 = new ResumeEducationAdapter(context, resume.getEducationBgList(), isSave2);
                                            rv2.setAdapter(adapter2);
                                            ///第三步：添加动画
                                            rv2.setItemAnimator(new DefaultItemAnimator());


                                            //3.项目经历
                                            //第一步：设置布局管理器
                                            rv3.setLayoutManager(new LinearLayoutManager(context));
                                            //第二步：设置适配器
                                            adapter3 = new ResumeProjectAdapter(context, resume.getProjectExpList(), isSave3);
                                            rv3.setAdapter(adapter3);
                                            ///第三步：添加动画
                                            rv3.setItemAnimator(new DefaultItemAnimator());


                                            //4.专业技能
                                            //第一步：设置布局管理器
                                            rv4.setLayoutManager(new LinearLayoutManager(context));
                                            //第二步：设置适配器
                                            adapter4 = new ResumeSkillsAdapter(context, resume.getProfessionalSkillList(), isSave4);
                                            rv4.setAdapter(adapter4);
                                            ///第三步：添加动画
                                            rv4.setItemAnimator(new DefaultItemAnimator());


                                            //刷新
                                            rv.setAdapter(adapter);
                                            adapter.notifyDataSetChanged();
                                            rv2.setAdapter(adapter2);
                                            adapter2.notifyDataSetChanged();
                                            rv3.setAdapter(adapter3);
                                            adapter3.notifyDataSetChanged();
                                            rv4.setAdapter(adapter4);
                                            adapter4.notifyDataSetChanged();
                                        });

                                        Log.i("stu", student.toString());
                                    }

                                    //这里不用判断”不存在简历“的情况，因为能进入到简历界面，就表示要么本身存在简历，
                                    //   要么本身没简历在上面创建好了简历，能看到刷新按钮时肯定存在简历
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
    }
}