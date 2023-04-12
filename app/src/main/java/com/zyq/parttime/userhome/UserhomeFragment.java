package com.zyq.parttime.userhome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.zyq.parttime.R;
import com.zyq.parttime.sp.EditInfo;
import com.zyq.parttime.userhome.intented.IntentedManage;
import com.zyq.parttime.userhome.resume.ResumesManage;
import com.zyq.parttime.userhome.setting.SettingManage;
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

public class UserhomeFragment extends Fragment {
    private Context context;//上下文

    public UserhomeFragment() {
    }

    public UserhomeFragment(String context) {
//        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userhome, container, false);
        context = this.getActivity();

        TextView name = view.findViewById(R.id.username);
        ImageView head = view.findViewById(R.id.head);
        ImageView right1 = view.findViewById(R.id.right1);
        ImageView right2 = view.findViewById(R.id.right2);
        ImageView right3 = view.findViewById(R.id.right3);
        ImageView right4 = view.findViewById(R.id.right4);

        //调api获取name、head  TODO
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                Request request = new Request.Builder()
                        .url("http://114.55.239.213:8082/users/info/get_stu?telephone="
                                + Constants.telephone)
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
                                com.alibaba.fastjson.JSONObject jsonObj = JSON.parseObject(response.body().string());
                                JSONObject data_userinfo_edit = JSON.parseObject(jsonObj.getString("data"));
                                Log.i("data_userinfo_edit实体", data_userinfo_edit.toString());

                                //Toast
                                getActivity().runOnUiThread(() -> {
                                    Toast toast = Toast.makeText(context, "用户信息获取成功！请稍等片刻~", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                    toast.show();

                                    name.setText(data_userinfo_edit.getString("stu_name"));//设置名称

                                    if (data_userinfo_edit.getString("head").equals("0")) {//没有头像就设置默认头像
                                        head.setImageResource(R.drawable.head);
                                    } else {//设置用户自己的头像
                                        head.setImageResource(Integer.valueOf(data_userinfo_edit.getString("head")));//设置头像
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

        right1.setOnClickListener(v -> {
            Intent i = new Intent();
            i.setClass(context, UserInfo.class);
            startActivity(i);
        });
        right2.setOnClickListener(v -> {
            getActivity().runOnUiThread(() -> {
                Toast toast = Toast.makeText(context, "数据加载中，请耐心等待~", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                toast.show();
            });

            Intent i = new Intent();
            i.setClass(context, ResumesManage.class);
            startActivity(i);
        });
        right3.setOnClickListener(v -> {
            Intent i = new Intent();
            i.setClass(context, IntentedManage.class);
            startActivity(i);
        });
        right4.setOnClickListener(v -> {
            Intent i = new Intent();
            i.setClass(context, SettingManage.class);
            startActivity(i);
        });

        return view;
    }
}
