package com.zyq.parttime.userhome.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.zyq.parttime.HomeActivity;
import com.zyq.parttime.R;
import com.zyq.parttime.loginreg.LoginActivity;
import com.zyq.parttime.sp.EditIntention;
import com.zyq.parttime.sp.Logout;
import com.zyq.parttime.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SettingManage extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//取消默认顶部标题栏
        setContentView(R.layout.activity_setting);
        context = this.getBaseContext();

        //组件初始化
        ImageView back = findViewById(R.id.back);
        ImageView right1 = findViewById(R.id.right1);
        ImageView right2 = findViewById(R.id.right2);
        ImageView right3 = findViewById(R.id.right3);
        ImageView right4 = findViewById(R.id.right4);

        //点击事件
        right1.setOnClickListener(v -> {
            Log.i("设置页", "点击了密码管理");
        });
        right2.setOnClickListener(v -> {
            Log.i("设置页", "点击了退出登录");

            //调api
            new Thread(() -> {
                try {
                    OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                    Logout logout = new Logout();
                    logout.setInput_telephone(Constants.telephone);//TODO 当前登录用户
                    String json = JSON.toJSONString(logout);//dto转json
                    Request request = new Request.Builder()
                            .url("http://114.55.239.213:8082/logout/stu")
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
                                    JSONObject jsonObj = JSON.parseObject(response.body().string());
                                    Log.i("data_logout实体", jsonObj.toString());
                                    String str = jsonObj.getString("data");

                                    runOnUiThread(() -> {
                                        if (str.equals("用户登出成功")) {
                                            Toast toast = Toast.makeText(getBaseContext(), "登出成功！请稍等片刻~", Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                            toast.show();

                                            //返回登录页
                                            Intent i = new Intent();
                                            i.setClass(context, LoginActivity.class);
                                            startActivity(i);
                                        } else {
                                            Toast toast = Toast.makeText(getBaseContext(), "登出失败", Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                            toast.show();
                                        }
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
        right3.setOnClickListener(v -> {
            Log.i("设置页", "敬请期待");
        });
        right4.setOnClickListener(v -> {
            Log.i("设置页", "敬请期待");
        });
        back.setOnClickListener(v -> {
            //跳转个人中心
            Intent i = new Intent();
            i.setClass(context, HomeActivity.class);
            i.putExtra("id", 3);
            startActivity(i);
        });
    }
}
