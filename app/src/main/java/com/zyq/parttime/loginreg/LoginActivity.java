package com.zyq.parttime.loginreg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.zyq.parttime.HomeActivity;
import com.zyq.parttime.R;
import com.zyq.parttime.sp.Login;
import com.zyq.parttime.sp.StuRegister;
import com.zyq.parttime.util.Constants;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//取消默认标题栏
        setContentView(R.layout.activity_login);
        AppCompatButton login_btn = findViewById(R.id.btn_login);
        AppCompatButton reg_btn = findViewById(R.id.btn_reg);
        EditText e1 = findViewById(R.id.telephone);
        EditText e2 = findViewById(R.id.password);

        login_btn.setOnClickListener(v -> {
            //验证输入的手机号、密码（前端第一次验证）
            String telephone = e1.getText().toString();
            String pwd = "";
            if (e2.getText() == null) {
                e2.setError("请输入密码");
            } else {
                pwd = e2.getText().toString();
            }
            if (telephone.length() != 11) {
                e1.setError("请输入正确的手机号");
            }


            //调登录接口（后端第二次验证）
            String the_pwd = pwd;
            new Thread(() -> {
                try {
                    OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                    Login login = new Login();
                    login.setTelephone(telephone);
                    login.setPwd(the_pwd);
                    String json = JSON.toJSONString(login);//dto转string
                    Request request = new Request.Builder()
                            .url("http://114.55.239.213:8082/login/stu")
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
                                    JSONObject data_login = JSON.parseObject(jsonObj.getString("data"));
                                    Log.i("data_login实体", data_login.toString());

                                    if (data_login.getString("memo").equals("登录成功")) {
                                        Constants.telephone = data_login.getString("telephone");//存到变量中
                                        Log.i("当前账号", Constants.telephone);

                                        runOnUiThread(() -> {
                                            //跳转到首页
                                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                            startActivity(intent);
                                            finish();
                                        });
                                    } else if (data_login.getString("memo").equals("密码或账号错误，请检查后重新输入")) {
                                        runOnUiThread(() -> {
                                            Toast toast = Toast.makeText(getBaseContext(), "密码或账号错误，请检查后重新输入", Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                            toast.show();
                                        });
                                    } else if (data_login.getString("memo").equals("不存在该学生")) {
                                        runOnUiThread(() -> {
                                            Toast toast = Toast.makeText(getBaseContext(), "不存在该学生", Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                            toast.show();
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
        });

        reg_btn.setOnClickListener(v -> {
            //跳转到注册页
            Intent intent = new Intent(LoginActivity.this, RegActivity.class);
            startActivity(intent);
            finish();
        });
    }
}