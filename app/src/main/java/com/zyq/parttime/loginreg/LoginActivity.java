package com.zyq.parttime.loginreg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.zyq.parttime.HomeActivity;
import com.zyq.parttime.R;
import com.zyq.parttime.form.Login;
import com.zyq.parttime.form.StuLogin;
import com.zyq.parttime.util.Constants;

import java.io.IOException;
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
        e1.setInputType(InputType.TYPE_CLASS_PHONE);//只能输手机号
        EditText e2 = findViewById(R.id.password);

        StuLogin stuLogin = new StuLogin();//存放学生信息的实体

        login_btn.setOnClickListener(v -> {
            runOnUiThread(() -> {
                //验证输入的手机号、密码（前端第一次验证）

                //未输入手机号
                if (e1.getText().length() == 0) {//手机号未输入
                    e1.setError("请输入手机号");
                    stuLogin.setCanAPI(false);

                    Toast toast = Toast.makeText(getBaseContext(), "请输入手机号", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                    toast.show();
                } else if (e1.getText().length() > 0) {
                    //输入了手机号

                    //手机号不是11位
                    if (e1.getText().length() < 11) {
                        e1.setError("请输入正确的手机号");
                        stuLogin.setCanAPI(false);

                        Toast toast = Toast.makeText(getBaseContext(), "请输入正确的手机号", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                        toast.show();
                    } else {
                        stuLogin.setTelephone(e1.getText().toString());

                        //未输入密码
                        if (e2.getText().length() == 0) {
                            e2.setError("请输入密码");
                            stuLogin.setCanAPI(false);

                            Toast toast = Toast.makeText(getBaseContext(), "请输入密码", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                            toast.show();
                        } else if (e2.getText().length() > 0) {
                            stuLogin.setPwd(e2.getText().toString());
                            stuLogin.setCanAPI(true);
                        }
                    }
                }
            });

            //只有canAPI为true才能调登录接口
            if (stuLogin.isCanAPI() == true) {
                //调登录接口（后端第二次验证）
                new Thread(() -> {
                    try {
                        OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                        Login login = new Login();
                        login.setTelephone(stuLogin.getTelephone());
                        login.setPwd(stuLogin.getPwd());
                        String json = JSON.toJSONString(login);//dto转string
                        Request request = new Request.Builder()
                                .url("http://114.55.239.213:8087/login/stu")
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
                                                Toast toast = Toast.makeText(getApplicationContext(), "登录成功！请稍等片刻~", Toast.LENGTH_LONG);
                                                toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                                toast.show();

                                                //跳转到首页
                                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                                startActivity(intent);
                                                finish();
                                            });
                                        } else if (data_login.getString("memo").equals("密码或账号错误，请检查后重新输入")) {
                                            runOnUiThread(() -> {
                                                Toast toast = Toast.makeText(getApplicationContext(), "密码或账号错误，请检查后重新输入", Toast.LENGTH_SHORT);
                                                toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                                toast.show();
                                            });
                                        } else if (data_login.getString("memo").equals("不存在该学生")) {
                                            runOnUiThread(() -> {
                                                Toast toast = Toast.makeText(getApplicationContext(), "不存在该学生", Toast.LENGTH_SHORT);
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
            }
        });

        reg_btn.setOnClickListener(v -> {
            //跳转到注册页
            Intent intent = new Intent(LoginActivity.this, RegActivity.class);
            startActivity(intent);
            finish();
        });
    }

    //点击键盘外区域关闭软键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            //判断是否要隐藏
            if (isShouldHideInput(v, ev)) {
                //使用InputMethodManager管理类，隐藏键盘
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
            int bottom = top + v.getHeight();//下=上+视图高度
            int right = left + v.getWidth();//右=左+视图宽度
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