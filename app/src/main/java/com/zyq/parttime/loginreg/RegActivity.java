package com.zyq.parttime.loginreg;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.zyq.parttime.R;
import com.zyq.parttime.sp.StuRegister;
import com.zyq.parttime.util.DateData;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//取消默认标题栏
        setContentView(R.layout.activity_reg);

        ImageView back = findViewById(R.id.back);
        AppCompatButton reg = findViewById(R.id.btn_reg);
        EditText name_edit = findViewById(R.id.name_edit);
        RadioGroup gender_edit = findViewById(R.id.gender_edit);
        AtomicReference<RadioButton> gender = new AtomicReference<>();
        EditText telephone_edit = findViewById(R.id.telephone_edit);
        EditText emails_edit = findViewById(R.id.emails_edit);
        EditText pwd_edit = findViewById(R.id.pwd_edit);
        EditText pwd2_edit = findViewById(R.id.pwd2_edit);
        Spinner yearSpinner = findViewById(R.id.year);
        Spinner monthSpinner = findViewById(R.id.month);
        EditText school_edit = findViewById(R.id.school_edit);
        EditText sno_edit = findViewById(R.id.sno_edit);
        Spinner startYearSpinner = findViewById(R.id.start_year);
        Spinner startMonthSpinner = findViewById(R.id.start_month);
        Spinner endYearSpinner = findViewById(R.id.end_year);
        Spinner endMonthSpinner = findViewById(R.id.end_month);

        //给RadioGroup性别设置选中项改变监听
        gender_edit.setOnCheckedChangeListener((radioGroup, i) -> {

        });

        //创建DateData类，并配置默认值
        DateData dateData = new DateData(1900, 1, 1900, 1, 1900, 1);

        //构造年份列表
        List<Integer> year = new ArrayList<>();//年份列表
        for (int i = 1900; i <= 2023; i++) {
            year.add(i);
        }
        //适配器1
        ArrayAdapter<Integer> yearadapter = new ArrayAdapter<>(this,
                R.layout.date_item, year);//把年份的列表添加到适配器中，使用自定义的item样式
        yearadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);//设置下拉选项内容
        yearSpinner.setAdapter(yearadapter);//设置适配器
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

        //构造月份列表
        List<Integer> month = new ArrayList<>();//月份列表
        for (int i = 1; i <= 12; i++) {
            month.add(i);
        }
        //适配器2
        ArrayAdapter<Integer> monthadapter = new ArrayAdapter<>(this,
                R.layout.date_item, month);//把月份的列表添加到适配器中，使用自定义的item样式
        monthadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);//设置下拉选项内容
        monthSpinner.setAdapter(monthadapter);//设置适配器

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

        //适配器3
        ArrayAdapter<Integer> startYearAdapter = new ArrayAdapter<>(this,
                R.layout.date_item, year);//把年份的列表添加到适配器中，使用自定义的item样式
        startYearAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);//设置下拉选项内容
        startYearSpinner.setAdapter(startYearAdapter);//设置适配器
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
        //监听下拉框
        startMonthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dateData.setStartMonth(monthadapter.getItem(position));
                runOnUiThread(() -> {
                    monthSpinner.setSelection(position);//设置当前选中的item
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //适配器5
        ArrayAdapter<Integer> endYearAdapter = new ArrayAdapter<>(this,
                R.layout.date_item, year);//把年份的列表添加到适配器中，使用自定义的item样式
        endYearAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);//设置下拉选项内容
        endYearSpinner.setAdapter(endYearAdapter);//设置适配器
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

        //返回按钮
        back.setOnClickListener(v -> {
            //跳转到登录页
            Intent intent = new Intent(RegActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        //注册按钮
        reg.setOnClickListener(v -> {
            //获取表单数据
            gender.set((RadioButton) findViewById(gender_edit.getCheckedRadioButtonId()));//getCheckedRadioButtonId()找到RadioGroup中被选中的radiobutton
            String data_gender;
            if (gender.get() == null) {
                data_gender = "";
            } else {
                data_gender = gender.get().getText().toString();
            }
            String username = name_edit.getText().toString();
            String telephone = telephone_edit.getText().toString();
            String emails = emails_edit.getText().toString();
            String pwd = pwd_edit.getText().toString();
            String pwd2 = pwd2_edit.getText().toString();
            String school_name = school_edit.getText().toString();
            String sno = sno_edit.getText().toString();

            //前端对表单进行检验
            int a1 = 0, a2 = 0, a3 = 0, a4 = 0, a5 = 0, a6 = 0, a7 = 0;
            if (username.length() == 0) {
                a1 = 1;
            }
            if (data_gender.length() == 0) {
                a2 = 1;
            }
            if (telephone.length() == 0) {
                a3 = 1;
            }
            if (emails.length() == 0) {
                a4 = 1;
            }
            if (pwd.length() == 0) {
                a5 = 1;
            }
            if (pwd2.length() == 0) {
                a6 = 1;
            }
            if (school_name.length() == 0) {
                a7 = 1;
            }
            if (a1 == 0 && a2 == 0 && a3 == 0 && a4 == 0 && a5 == 0 && a6 == 0 && a7 == 0
                    && pwd.equals(pwd2) && telephone.length() == 11 && isEmail(emails) == true
                    && ((isSno(sno) == true && sno.length() > 0) || sno.length() == 0)) {
                //调用接口存到DB中
                new Thread(() -> {
                    try {
                        OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                        StuRegister stuRegister = new StuRegister();
                        stuRegister.setTelephone(telephone);
                        if (data_gender.equals("男")) {
                            stuRegister.setGender(1);
                        } else if (data_gender.equals("女")) {
                            stuRegister.setGender(0);
                        }
                        stuRegister.setStu_name(username);
                        stuRegister.setEmails(emails);
                        stuRegister.setPwd(pwd);
                        stuRegister.setPwd2(pwd2);
                        stuRegister.setSchool_name(school_name);
                        stuRegister.setSno(sno);

                        //构造年龄、入学、毕业时间
                        int y1 = dateData.getBirthYear();
                        int m1 = dateData.getBirthMonth();
                        int y2 = dateData.getStartYear();
                        int m2 = dateData.getStartMonth();
                        int y3 = dateData.getEndYear();
                        int m3 = dateData.getEndMonth();

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                        int newAge = 0;
                        String start = "", end = "";
                        Date birth;
                        try {
                            //出生日期
                            if (m1 >= 1 && m1 <= 9) {
                                birth = sdf.parse(y1 + "-0" + m1);
                            } else {
                                birth = sdf.parse(y1 + "-" + m1);
                            }
                            //当前
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
                        stuRegister.setAge(newAge);
                        stuRegister.setEntrance_date(start);
                        stuRegister.setGraduation_date(end);
                        //注册时间
                        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String reg_time = sdf2.format(new Date());
                        stuRegister.setReg_date(reg_time);

                        String json = JSON.toJSONString(stuRegister);//dto转string
                        Request request = new Request.Builder()
                                .url("http://114.55.239.213:8087/register/stu")
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
                                        JSONObject data_register = JSON.parseObject(jsonObj.getString("data"));
                                        Log.i("data_register实体", data_register.toString());

                                        runOnUiThread(() -> {
                                            Toast toast = Toast.makeText(getApplicationContext(), "注册成功！请稍等片刻~", Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                            toast.show();

                                            //跳转到登录页
                                            Intent intent = new Intent(RegActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            finish();
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

            //校验
            if (a1 == 1) {
                runOnUiThread(() -> {
                    name_edit.setError("不能为空");
                });
            }
            if (a2 == 1) {
                runOnUiThread(() -> {
                    Toast toast = Toast.makeText(getBaseContext(), "请选择性别", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                    toast.show();
                });
            }
            if (a3 == 1) {
                runOnUiThread(() -> {
                    telephone_edit.setError("不能为空");
                });
            }
            if (a4 == 1) {
                runOnUiThread(() -> {
                    emails_edit.setError("不能为空");
                });
            }
            if (a5 == 1) {
                runOnUiThread(() -> {
                    pwd_edit.setError("不能为空");
                });
            }
            if (a6 == 1) {
                runOnUiThread(() -> {
                    pwd2_edit.setError("不能为空");
                });
            }
            if (a7 == 1) {
                runOnUiThread(() -> {
                    school_edit.setError("不能为空");
                });
            }
            if (isEmail(emails) == false) {
                runOnUiThread(() -> {
                    emails_edit.setError("请输入正确的邮箱");
                });
            }
            if (!pwd.equals(pwd2)) {
                runOnUiThread(() -> {
                    pwd_edit.setError("请保证两次输入的密码一致");
                    pwd2_edit.setError("请保证两次输入的密码一致");
                });
            }
            if (telephone.length() != 11) {
                runOnUiThread(() -> {
                    telephone_edit.setError("请输入正确的手机号");
                });
            }
            if (isSno(sno) == false && sno.length() > 0) {
                runOnUiThread(() -> {
                    sno_edit.setError("请输入正确的学号");
                });
            }
        });
    }

    //是否是邮箱格式
    public static boolean isEmail(String emails) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(emails);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    //是否是学号
    public static boolean isSno(String sno) {
        boolean flag = false;
        if (sno.length() == 8) {
            flag = true;
        }
        return flag;
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