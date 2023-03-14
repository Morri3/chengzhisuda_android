package com.zyq.parttime.loginreg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.zyq.parttime.HomeActivity;
import com.zyq.parttime.R;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//取消默认标题栏
        setContentView(R.layout.activity_login);
        AppCompatButton login_btn = findViewById(R.id.btn_login);
        AppCompatButton reg_btn = findViewById(R.id.btn_reg);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //验证输入的手机号、密码（前端第一次验证）

                //调登录接口（后端第二次验证）

                //跳转到首页
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        reg_btn.setOnClickListener(v -> {
            //跳转到注册页
            Intent intent = new Intent(LoginActivity.this, RegActivity.class);
            startActivity(intent);
            finish();
        });


//        //调用接口测试
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Thread(() -> {
//                    try {
//                        OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
//                        String telephone = "13800000001";
//                        String json = "{\"telephone\":\"" + telephone + "\"}";
//                        Log.i("json", json);
//                        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
//
//                        Request request = new Request.Builder()
//                                .url("http://114.55.239.213:8080/users/resumes/get?telephone=13800000001")
//                                .get()
//                                .build();//创建Http请求
//                        client.newBuilder().readTimeout(15000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(new Callback() {
//                            @Override
//                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                                Log.i("error", "数据获取失败");
//                                e.printStackTrace();
//                            }
//
//                            @Override
//                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                                if (response.isSuccessful()) {//调用成功
//                                    try {
//                                        JSONObject jsonObject = new JSONObject(response.body().string());//json
//                                        JSONObject obj = jsonObject.getJSONObject("data");//获取数据
//                                        Log.i("数据", obj.toString());
//
//                                        //获取obj中的数据
//
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//
//                                } else {//调用失败
//                                    Log.i("error", response.toString());
//                                }
//                            }
//                        });
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }).start();//要start才会启动
//            }
//        });
    }
}