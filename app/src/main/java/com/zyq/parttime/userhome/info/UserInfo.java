package com.zyq.parttime.userhome.info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.zyq.parttime.HomeActivity;
import com.zyq.parttime.R;
import com.zyq.parttime.form.EditInfo;
import com.zyq.parttime.form.StudentInfo;
import com.zyq.parttime.form.UserBirth;
import com.zyq.parttime.util.Constants;
import com.zyq.parttime.util.DateData;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserInfo extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//取消默认顶部标题栏
        setContentView(R.layout.activity_userinfo);

        TextView username = findViewById(R.id.username);
        TextView gender = findViewById(R.id.gender);
        TextView emails = findViewById(R.id.emails);
        TextView age = findViewById(R.id.age);
        TextView telephone = findViewById(R.id.telephone);
        TextView school_name = findViewById(R.id.school_name);
        TextView sno = findViewById(R.id.sno);
        TextView grade = findViewById(R.id.grade);
        EditText e1 = findViewById(R.id.e1);
        EditText e2 = findViewById(R.id.e2);
        EditText e3 = findViewById(R.id.e3);
        Spinner yearSpinner = findViewById(R.id.year);
        TextView year_text = findViewById(R.id.year_text);
        Spinner monthSpinner = findViewById(R.id.month);
        TextView month_text = findViewById(R.id.month_text);
        Spinner startYearSpinner = findViewById(R.id.start_year);
        TextView start_year_text = findViewById(R.id.start_year_text);
        Spinner startMonthSpinner = findViewById(R.id.start_month);
        TextView start_month_text = findViewById(R.id.start_month_text);
        Spinner endYearSpinner = findViewById(R.id.end_year);
        TextView end_year_text = findViewById(R.id.end_year_text);
        Spinner endMonthSpinner = findViewById(R.id.end_month);
        TextView end_month_text = findViewById(R.id.end_month_text);
        EditText e5 = findViewById(R.id.e5);
        EditText e6 = findViewById(R.id.e6);
        EditText e7 = findViewById(R.id.e7);
        ImageView modify = findViewById(R.id.modify);
        TextView modify_text = findViewById(R.id.modify_text);
        ImageView btn_save = findViewById(R.id.btn_save);
        ImageView back = findViewById(R.id.back);

        runOnUiThread(() -> {
            e1.setVisibility(View.INVISIBLE);
            e2.setVisibility(View.INVISIBLE);
            e3.setVisibility(View.INVISIBLE);
            yearSpinner.setVisibility(View.INVISIBLE);
            year_text.setVisibility(View.INVISIBLE);
            monthSpinner.setVisibility(View.INVISIBLE);
            month_text.setVisibility(View.INVISIBLE);
            startYearSpinner.setVisibility(View.INVISIBLE);
            start_year_text.setVisibility(View.INVISIBLE);
            startMonthSpinner.setVisibility(View.INVISIBLE);
            start_month_text.setVisibility(View.INVISIBLE);
            e5.setVisibility(View.INVISIBLE);
            e6.setVisibility(View.INVISIBLE);
            e7.setVisibility(View.INVISIBLE);
            endYearSpinner.setVisibility(View.INVISIBLE);
            end_year_text.setVisibility(View.INVISIBLE);
            endMonthSpinner.setVisibility(View.INVISIBLE);
            end_month_text.setVisibility(View.INVISIBLE);
            btn_save.setVisibility(View.INVISIBLE);
        });

        //调api获取个人信息，set到textview中
        StudentInfo studentInfo = new StudentInfo();
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                String phone = Constants.telephone;//TODO  改为当前登录用户
                Request request = new Request.Builder()
                        .url("http://114.55.239.213:8087/users/info/get_stu?telephone=" + phone)
                        .get()
                        .build();//创建Http请求
                client.newBuilder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
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
                                JSONObject data_userinfo = JSON.parseObject(jsonObj.getString("data"));
                                Log.i("data_userinfo实体", data_userinfo.toString());

                                int data_age = data_userinfo.getIntValue("age");
                                String data_birth_year = data_userinfo.getString("birth_year");
                                String data_birth_month = data_userinfo.getString("birth_month");
                                String data_emails = data_userinfo.getString("emails");
                                int data_gender = data_userinfo.getIntValue("gender");
                                String data_grade = data_userinfo.getString("grade");
                                String data_school_name = data_userinfo.getString("school_name");
                                String data_sno = data_userinfo.getString("sno");
                                String data_stu_name = data_userinfo.getString("stu_name");
                                String data_telephone = data_userinfo.getString("telephone");
                                Date data_entrance_date = data_userinfo.getDate("entrance_date");
                                Date data_graduation_date = data_userinfo.getDate("graduation_date");

                                studentInfo.setAge(data_age);
                                studentInfo.setBirth_year(data_birth_year);
                                studentInfo.setBirth_month(data_birth_month);
                                studentInfo.setEmails(data_emails);
                                studentInfo.setGender(data_gender);
                                studentInfo.setGrade(data_grade);
                                studentInfo.setSchool_name(data_school_name);
                                studentInfo.setSno(data_sno);
                                studentInfo.setStu_name(data_stu_name);
                                studentInfo.setTelephone(data_telephone);
                                studentInfo.setEntrance_date(data_entrance_date);
                                studentInfo.setGraduation_date(data_graduation_date);

                                runOnUiThread(() -> {
                                    Toast toast = Toast.makeText(getBaseContext(), "用户信息获取成功！请稍等片刻~", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                    toast.show();

                                    //set到组件中
                                    age.setText(data_age + "");
                                    emails.setText(data_emails);
                                    if (studentInfo.getGender() == 1) {
                                        gender.setText("男");
                                    } else if (studentInfo.getGender() == 0) {
                                        gender.setText("女");
                                    }
                                    grade.setText(data_grade);
                                    school_name.setText(data_school_name);
                                    sno.setText(data_sno);
                                    username.setText(data_stu_name);
                                    telephone.setText(data_telephone);
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

        //修改
        modify.setOnClickListener(v -> {
            //是否可见
            username.setVisibility(View.INVISIBLE);
            gender.setVisibility(View.INVISIBLE);
            emails.setVisibility(View.INVISIBLE);
            age.setVisibility(View.INVISIBLE);
            telephone.setVisibility(View.INVISIBLE);
            school_name.setVisibility(View.INVISIBLE);
            sno.setVisibility(View.INVISIBLE);
            grade.setVisibility(View.INVISIBLE);
            modify.setVisibility(View.INVISIBLE);
            modify_text.setVisibility(View.INVISIBLE);

            e1.setVisibility(View.VISIBLE);
            e2.setVisibility(View.VISIBLE);
            e3.setVisibility(View.VISIBLE);
            yearSpinner.setVisibility(View.VISIBLE);
            monthSpinner.setVisibility(View.VISIBLE);
            startYearSpinner.setVisibility(View.VISIBLE);
            startMonthSpinner.setVisibility(View.VISIBLE);
            year_text.setVisibility(View.VISIBLE);
            month_text.setVisibility(View.VISIBLE);
            start_year_text.setVisibility(View.VISIBLE);
            start_month_text.setVisibility(View.VISIBLE);
            end_year_text.setVisibility(View.VISIBLE);
            end_month_text.setVisibility(View.VISIBLE);
            e5.setVisibility(View.VISIBLE);
            e6.setVisibility(View.VISIBLE);
            e7.setVisibility(View.VISIBLE);
            endYearSpinner.setVisibility(View.VISIBLE);
            endMonthSpinner.setVisibility(View.VISIBLE);
            btn_save.setVisibility(View.VISIBLE);

//            //将年龄转为年、月
//            Calendar calendar0 = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
//            String[] a = new String[2];
//            try {
//                calendar0.setTime(sdf.parse(sdf.format(new Date())));
//                calendar0.add(Calendar.YEAR, -studentInfo.getAge());//往前推
//                a = sdf.format(calendar0.getTime()).split("-");
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//            //1.获取出生年月
//            String birth_year = a[0];
//            String birth_month = a[1];

            //2.获取入学年月
            String b[] = (sdf.format(studentInfo.getEntrance_date())).split("-");
            String enter_year = b[0];
            String enter_month = b[1];
            //3.获取毕业年月
            String c[] = (sdf.format(studentInfo.getGraduation_date())).split("-");
            String end_year = c[0];
            String end_month = c[1];
            Log.i("出生年月", studentInfo.getBirth_year() + "-" + studentInfo.getBirth_month());
            Log.i("入学年月", sdf.format(studentInfo.getEntrance_date()));
            Log.i("毕业年月", sdf.format(studentInfo.getGraduation_date()));

            //把api中的数据set到对应的editview中
            e1.setText(studentInfo.getStu_name());
            if (studentInfo.getGender() == 1) {
                e2.setText("男");
            } else if (studentInfo.getGender() == 0) {
                e2.setText("女");
            }
            e3.setText(studentInfo.getEmails());
            e5.setText(studentInfo.getTelephone());
            e6.setText(studentInfo.getSchool_name());
            e7.setText(studentInfo.getSno());

            //获取出生月份
            int theYear = Integer.parseInt(studentInfo.getBirth_year());
            int theMonth;//出生月份初始化
            if (((studentInfo.getBirth_month()).charAt(0)) == '0') {
                //1到9月
                theMonth = Integer.parseInt(String.valueOf((studentInfo.getBirth_month()).charAt(1)));
            } else {
                //10、11、12月
                theMonth = Integer.parseInt(studentInfo.getBirth_month());
            }
            //创建DateData类，并配置默认值（设置DB中的值）
            DateData dateData = new DateData(theYear, theMonth,
                    Integer.parseInt(enter_year), Integer.parseInt(enter_month),
                    Integer.parseInt(end_year), Integer.parseInt(end_month));

            //获取当前年份
            Calendar calendar = Calendar.getInstance();
            int curYear = calendar.get(Calendar.YEAR);
            //构造年份列表
            List<Integer> year = new ArrayList<>();//年份列表
            for (int i = curYear - 30; i <= curYear + 4; i++) {
                year.add(i);
            }
            //构造月份列表
            List<Integer> month = new ArrayList<>();//月份列表
            for (int i = 1; i <= 12; i++) {
                month.add(i);
            }
            //找到对应的年份
            int idx1 = 0;
            for (int i = curYear - 30; i <= curYear + 4; i++) {
//                if (i == Integer.parseInt(birth_year)) {
                if (i == Integer.parseInt(studentInfo.getBirth_year())) {
                    break;
                }
                idx1++;
            }
            //找到对应的月份
            int idx2 = 0;
            for (int i = 1; i <= 12; i++) {
//                if (i == Integer.parseInt(birth_month)) {
                if (i == theMonth) {
                    break;
                }
                idx2++;
            }

            //适配器1
            ArrayAdapter<Integer> yearadapter = new ArrayAdapter<>(this,
                    R.layout.date_item, year);//把年份的列表添加到适配器中，使用自定义的item样式
            yearadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);//设置下拉选项内容
            yearSpinner.setAdapter(yearadapter);//设置适配器
            yearSpinner.setSelection(idx1);//设置当前选中项
            //监听下拉框
            yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    dateData.setBirthYear(yearadapter.getItem(position));//用DateData对象来处理选中的年份
                    runOnUiThread(() -> {
                        yearSpinner.setSelection(position);//设置当前选中的item
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            //适配器2
            ArrayAdapter<Integer> monthadapter = new ArrayAdapter<>(this,
                    R.layout.date_item, month);//把月份的列表添加到适配器中，使用自定义的item样式
            monthadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);//设置下拉选项内容
            monthSpinner.setAdapter(monthadapter);//设置适配器
            monthSpinner.setSelection(idx2);//设置当前选中项
            //监听下拉框
            monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    dateData.setBirthMonth(monthadapter.getItem(position));
                    runOnUiThread(() -> {
                        monthSpinner.setSelection(position);//设置当前选中的item
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            //找到对应的年份
            int idx3 = 0;
            for (int i = curYear - 30; i <= curYear + 4; i++) {
                if (i == Integer.parseInt(enter_year)) {
                    break;
                }
                idx3++;
            }
            //找到对应的月份
            int idx4 = 0;
            for (int i = 1; i <= 12; i++) {
                if (i == Integer.parseInt(enter_month)) {
                    break;
                }
                idx4++;
            }

            //适配器3
            ArrayAdapter<Integer> startYearAdapter = new ArrayAdapter<>(this,
                    R.layout.date_item, year);//把年份的列表添加到适配器中，使用自定义的item样式
            startYearAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);//设置下拉选项内容
            startYearSpinner.setAdapter(startYearAdapter);//设置适配器
            startYearSpinner.setSelection(idx3);//设置当前选中项
            //监听下拉框
            startYearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    dateData.setStartYear(startYearAdapter.getItem(position));//用DateData对象来处理选中的年份
                    runOnUiThread(() -> {
                        startYearSpinner.setSelection(position);//设置当前选中的item
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            //适配器4
            ArrayAdapter<Integer> startMonthAdapter = new ArrayAdapter<>(this,
                    R.layout.date_item, month);//把月份的列表添加到适配器中，使用自定义的item样式
            startMonthAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);//设置下拉选项内容
            startMonthSpinner.setAdapter(startMonthAdapter);//设置适配器
            startMonthSpinner.setSelection(idx4);//设置当前选中项
            //监听下拉框
            startMonthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    dateData.setStartMonth(startMonthAdapter.getItem(position));
                    runOnUiThread(() -> {
                        startMonthSpinner.setSelection(position);//设置当前选中的item
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            //找到对应的年份
            int idx5 = 0;
            for (int i = curYear - 30; i <= curYear + 4; i++) {
                if (i == Integer.parseInt(end_year)) {
                    break;
                }
                idx5++;
            }
            //找到对应的月份
            int idx6 = 0;
            for (int i = 1; i <= 12; i++) {
                if (i == Integer.parseInt(end_month)) {
                    break;
                }
                idx6++;
            }

            //适配器5
            ArrayAdapter<Integer> endYearAdapter = new ArrayAdapter<>(this,
                    R.layout.date_item, year);//把年份的列表添加到适配器中，使用自定义的item样式
            endYearAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);//设置下拉选项内容
            endYearSpinner.setAdapter(endYearAdapter);//设置适配器
            endYearSpinner.setSelection(idx5);
            //监听下拉框
            endYearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    dateData.setEndYear(endYearAdapter.getItem(position));//用DateData对象来处理选中的年份
                    runOnUiThread(() -> {
                        endYearSpinner.setSelection(position);//设置当前选中的item
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            //适配器6
            ArrayAdapter<Integer> endMonthAdapter = new ArrayAdapter<>(this,
                    R.layout.date_item, month);//把月份的列表添加到适配器中，使用自定义的item样式
            endMonthAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);//设置下拉选项内容
            endMonthSpinner.setAdapter(endMonthAdapter);//设置适配器
            endMonthSpinner.setSelection(idx6);
            //监听下拉框
            endMonthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    dateData.setEndMonth(endMonthAdapter.getItem(position));
                    runOnUiThread(() -> {
                        endMonthSpinner.setSelection(position);//设置当前选中的item
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            //保存按钮
            UserBirth userBirth = new UserBirth();//用于存放出生年月，供保存时使用
            btn_save.setOnClickListener(v1 -> {
                String t2 = e2.getText().toString();
                String t3 = e3.getText().toString();
                int y1 = dateData.getBirthYear();
                int m1 = dateData.getBirthMonth();
                int y2 = dateData.getStartYear();
                int m2 = dateData.getStartMonth();
                int y3 = dateData.getEndYear();
                int m3 = dateData.getEndMonth();

                int newAge = 0;
                String birth_year = "", birth_month = "";//出生年月
                String start = "", end = "";//入学年月、毕业年月
                Date birth;//是出生年月
                try {
                    //出生日期
                    birth_year = y1 + "";//年份
                    if (m1 >= 1 && m1 <= 9) {
                        //一位数要左添0
                        birth = sdf.parse(y1 + "-0" + m1);
                        birth_month = "0" + m1;//月份
                    } else {
                        //两位数不变
                        birth = sdf.parse(y1 + "-" + m1);
                        birth_month = "" + m1;//月份
                    }
                    //出生年月set到Dto中
                    userBirth.setTelephone(Constants.telephone);
                    userBirth.setBirth_year(birth_year);
                    userBirth.setBirth_month(birth_month);

                    //当前时间，使用Calendar类计算年龄
                    Date now = new Date();
                    Calendar c1 = Calendar.getInstance();
                    Calendar c2 = Calendar.getInstance();
                    c1.setTime(birth);
                    c2.setTime(now);
                    int tmp1 = c2.get(Calendar.DATE) - c1.get(Calendar.DATE);
                    int tmp2 = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
                    int tmp3 = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
                    if (tmp2 > 0) {//月份更大
                        tmp2 = 1;//最后要+1
                    } else if (tmp2 == 0) {//月份相同
                        tmp2 = tmp1 <= 0 ? 0 : 1;//判断日期，前面日期更小，最后月份要+1
                    } else {//月份更小，不用+1
                        tmp2 = 0;
                    }
                    newAge = tmp3 + tmp2;
                    dateData.setAge(newAge);

                    //入学时间，毕业时间
                    if (m2 >= 1 && m2 <= 9) {
                        start = y2 + "-0" + m2;
                        dateData.setStart(start);
                    } else {
                        start = y2 + "-" + m2;
                        dateData.setStart(start);
                    }
                    if (m3 >= 1 && m3 <= 9) {
                        end = y3 + "-0" + m3;
                        dateData.setEnd(end);
                    } else {
                        end = y3 + "-" + m3;
                        dateData.setEnd(end);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.i("age", newAge + "");
                Log.i("start", start);
                Log.i("end", end);

                //调api，保存
                new Thread(() -> {
                    try {
                        OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                        EditInfo editInfo = new EditInfo();
                        editInfo.setTelephone(Constants.telephone);//TODO 改为当前用户
                        editInfo.setAge(dateData.getAge());
                        if (t2.equals("男")) {
                            editInfo.setGender(1);
                        } else if (t2.equals("女")) {
                            editInfo.setGender(0);
                        }
                        editInfo.setBirth_year(userBirth.getBirth_year());//出生年份
                        editInfo.setBirth_month(userBirth.getBirth_month());//出生月份
                        editInfo.setEmails(t3);
                        editInfo.setEntrance_date(dateData.getStart());
                        editInfo.setGraduation_date(dateData.getEnd());
                        String json = JSON.toJSONString(editInfo);
                        Request request = new Request.Builder()
                                .url("http://114.55.239.213:8087/users/info/edit_stu")
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
                                Log.i("error", "数据获取失败");
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                if (response.isSuccessful()) {//调用成功
                                    try {
                                        com.alibaba.fastjson.JSONObject jsonObj = JSON.parseObject(response.body().string());
                                        JSONObject data_userinfo_edit = JSON.parseObject(jsonObj.getString("data"));
                                        Log.i("data_userinfo_edit实体", data_userinfo_edit.toString());

                                        //切回原来的
                                        runOnUiThread(() -> {
                                            Toast toast = Toast.makeText(getApplicationContext(), "保存成功！请稍等片刻~", Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                            toast.show();

                                            //控件是否可见
                                            username.setVisibility(View.VISIBLE);
                                            gender.setVisibility(View.VISIBLE);
                                            emails.setVisibility(View.VISIBLE);
                                            age.setVisibility(View.VISIBLE);
                                            telephone.setVisibility(View.VISIBLE);
                                            school_name.setVisibility(View.VISIBLE);
                                            sno.setVisibility(View.VISIBLE);
                                            grade.setVisibility(View.VISIBLE);
                                            e1.setVisibility(View.INVISIBLE);
                                            e2.setVisibility(View.INVISIBLE);
                                            e3.setVisibility(View.INVISIBLE);
                                            yearSpinner.setVisibility(View.INVISIBLE);
                                            monthSpinner.setVisibility(View.INVISIBLE);
                                            startYearSpinner.setVisibility(View.INVISIBLE);
                                            startMonthSpinner.setVisibility(View.INVISIBLE);
                                            year_text.setVisibility(View.INVISIBLE);
                                            month_text.setVisibility(View.INVISIBLE);
                                            start_year_text.setVisibility(View.INVISIBLE);
                                            start_month_text.setVisibility(View.INVISIBLE);
                                            end_year_text.setVisibility(View.INVISIBLE);
                                            end_month_text.setVisibility(View.INVISIBLE);
                                            e5.setVisibility(View.INVISIBLE);
                                            e6.setVisibility(View.INVISIBLE);
                                            e7.setVisibility(View.INVISIBLE);
                                            endYearSpinner.setVisibility(View.INVISIBLE);
                                            endMonthSpinner.setVisibility(View.INVISIBLE);
                                            btn_save.setVisibility(View.INVISIBLE);
                                            modify.setVisibility(View.VISIBLE);
                                            modify_text.setVisibility(View.VISIBLE);

                                            if (data_userinfo_edit.getIntValue("gender") == 0) {
                                                gender.setText("女");
                                            } else if (data_userinfo_edit.getIntValue("gender") == 1) {
                                                gender.setText("男");
                                            }
                                            emails.setText(data_userinfo_edit.getString("emails"));
                                            age.setText(data_userinfo_edit.getIntValue("age") + "");
                                            grade.setText(data_userinfo_edit.getString("grade"));

                                            //刷新，获取最新数据
                                            new Thread(() -> {
                                                try {
                                                    OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                                                    String phone = Constants.telephone;//TODO  改为当前登录用户
                                                    Request request = new Request.Builder()
                                                            .url("http://114.55.239.213:8087/users/info/get_stu?telephone=" + phone)
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
                                                                    JSONObject data_userinfo = JSON.parseObject(jsonObj.getString("data"));
                                                                    Log.i("data_userinfo实体", data_userinfo.toString());

                                                                    int data_age = data_userinfo.getIntValue("age");
                                                                    String data_birth_year = data_userinfo.getString("birth_year");
                                                                    String data_birth_month = data_userinfo.getString("birth_month");
                                                                    String data_emails = data_userinfo.getString("emails");
                                                                    int data_gender = data_userinfo.getIntValue("gender");
                                                                    String data_grade = data_userinfo.getString("grade");
                                                                    String data_school_name = data_userinfo.getString("school_name");
                                                                    String data_sno = data_userinfo.getString("sno");
                                                                    String data_stu_name = data_userinfo.getString("stu_name");
                                                                    String data_telephone = data_userinfo.getString("telephone");
                                                                    Date data_entrance_date = data_userinfo.getDate("entrance_date");
                                                                    Date data_graduation_date = data_userinfo.getDate("graduation_date");

                                                                    studentInfo.setAge(data_age);
                                                                    studentInfo.setBirth_year(data_birth_year);
                                                                    studentInfo.setBirth_month(data_birth_month);
                                                                    studentInfo.setEmails(data_emails);
                                                                    studentInfo.setGender(data_gender);
                                                                    studentInfo.setGrade(data_grade);
                                                                    studentInfo.setSchool_name(data_school_name);
                                                                    studentInfo.setSno(data_sno);
                                                                    studentInfo.setStu_name(data_stu_name);
                                                                    studentInfo.setTelephone(data_telephone);
                                                                    studentInfo.setEntrance_date(data_entrance_date);
                                                                    studentInfo.setGraduation_date(data_graduation_date);

                                                                    runOnUiThread(() -> {
                                                                        //set到组件中
                                                                        age.setText(data_age + "");
                                                                        emails.setText(data_emails);
                                                                        if (studentInfo.getGender() == 1) {
                                                                            gender.setText("男");
                                                                        } else if (studentInfo.getGender() == 0) {
                                                                            gender.setText("女");
                                                                        }
                                                                        grade.setText(data_grade);
                                                                        school_name.setText(data_school_name);
                                                                        sno.setText(data_sno);
                                                                        username.setText(data_stu_name);
                                                                        telephone.setText(data_telephone);

                                                                        //toast
                                                                        Toast toast = Toast.makeText(getBaseContext(), "刷新成功", Toast.LENGTH_SHORT);
                                                                        toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                                                        toast.show();
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
            });
        });

        //返回
        back.setOnClickListener(v -> {
            Toast toast = Toast.makeText(this.getBaseContext(), "数据加载中，请稍等片刻~", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
            toast.show();

            //跳转到首页
            Intent i = new Intent();
            i.setClass(UserInfo.this, HomeActivity.class);
            //一定要指定是第几个pager，这里填写3
            i.putExtra("id", 3);
            startActivity(i);
        });
    }

    //点击键盘外区域关闭软键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            //判断是否要隐藏
            if (isShouldHideInput(v, ev)) {
                //使用InputMethodManager管理类，进行隐藏
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        if (getWindow().superDispatchTouchEvent(ev)) {//不能缺少这步，以免所有组件丢失点击事件
            return true;
        }
        return onTouchEvent(ev);
    }

    //是否需要隐藏
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};//获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];//左
            int top = leftTop[1];//上
            int bottom = top + v.getHeight();//下=上+高度
            int right = left + v.getWidth();//右=左+宽度
            if (event.getX() > left && event.getX() < right && event.getY() > top
                    && event.getY() < bottom) {//点击区域在输入框内，保留点击EditText的事件
                return false;
            } else {//否则不保留点击事件
                return true;
            }
        }
        return false;
    }
}