package com.zyq.parttime.home;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.zyq.parttime.R;
import com.zyq.parttime.form.Position;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public class HomeFragment extends Fragment {
    private Context context;//上下文
    private List<Position> list = new ArrayList();//存放recycleview的数据
    private RecyclerView rv; //RecyclerView布局
    private HomeAdapter homeAdapter;//适配器

    public HomeFragment() {
    }

    public HomeFragment(String context) {
//        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        context = this.getActivity();

        //获取兼职数据
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                Request request = new Request.Builder()
                        .url("http://114.55.239.213:8082/parttime/stu/get")
                        .get()
                        .build();//创建Http请求
                client.newBuilder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(6, TimeUnit.SECONDS)
                        .writeTimeout(6, TimeUnit.SECONDS)
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
//                                Log.i("数据", response.body().string());
                                JSONObject jsonObj = JSON.parseObject(response.body().string());
                                Log.i("data", jsonObj.getString("data"));
                                JSONArray data = JSON.parseArray(jsonObj.getString("data"));
                                Log.i("data2", data.toString());

                                for (int i = 0; i < data.size(); i++) {
                                    int p_id = data.getJSONObject(i).getIntValue("p_id");
                                    String op_id = data.getJSONObject(i).getString("op_id");
                                    String area = data.getJSONObject(i).getString("area");
                                    String category = data.getJSONObject(i).getString("category");
                                    String content = data.getJSONObject(i).getString("content");
                                    String requirement = data.getJSONObject(i).getString("requirement");
                                    String work_time = data.getJSONObject(i).getString("work_time");
                                    String create_time = data.getJSONObject(i).getString("create_time");
                                    String exp = data.getJSONObject(i).getString("exp");
                                    String position_name = data.getJSONObject(i).getString("position_name");
                                    String position_status = data.getJSONObject(i).getString("position_status");
                                    String salary = data.getJSONObject(i).getString("salary");
                                    String settlement = data.getJSONObject(i).getString("settlement");
                                    String signup_ddl = data.getJSONObject(i).getString("signup_ddl");
                                    String slogan = data.getJSONObject(i).getString("slogan");

                                    Position position = new Position();
                                    position.setP_id(p_id);
                                    position.setOp_id(op_id);
                                    position.setCategory(category);
                                    position.setPosition_status(position_status);
                                    position.setPosition_name(position_name);
                                    position.setArea(area);
                                    position.setSettlement(settlement);
                                    position.setExp(exp);
                                    position.setSalary(salary);
                                    position.setWork_time(work_time);
                                    position.setContent(content);
                                    position.setRequirement(requirement);
                                    Date create = new Date();
                                    Date ddl = new Date();
                                    try {
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                        create = sdf.parse(create_time);
                                        position.setCreate_time(create);
                                        ddl = sdf.parse(signup_ddl);
                                        position.setSignup_ddl(ddl);
                                    } catch (ParseException e) {
                                        Log.i("error", "日期转换错误");
                                    }
                                    position.setSlogan(slogan);

                                    list.add(position);//加到列表中
                                    Log.i("首页数据list",list.toString());
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
        Log.i("首页兼职数据", list.toString());

        //5s延时
        try{
            Thread.sleep(5000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        //适配器的定义与设置
        getActivity().runOnUiThread(() -> {
            //配置布局管理器、分割线、适配器
            rv = view.findViewById(R.id.rv);
            //第一步：设置布局管理器
            rv.setLayoutManager(new LinearLayoutManager(getActivity()));
            //第二步：设置适配器
            homeAdapter = new HomeAdapter(getActivity(), list);
            rv.setAdapter(homeAdapter);

            //星推榜
            TextView enter = view.findViewById(R.id.enter);
            enter.setOnClickListener(v -> {
                Log.i("星推榜", "点击了星推榜");
                //跳转

            });
        });

        return view;
    }

//        list.clear();
//        new Thread(() -> {
//            try {
//                OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
//                String telephone = "13800000001";
//                String json = "{\"telephone\":\"" + telephone + "\"}";
//                Log.i("json", json);
//                RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
//
//                Request request = new Request.Builder()
//                        .url("http://114.55.239.213:8080/users/resumes/get?telephone=13800000001")
//                        .get()
//                        .build();//创建Http请求
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
////                                JSONObject jsonObject = new JSONObject(response.body().string());//json
////                                JSONObject obj = jsonObject.getJSONObject("data");//获取数据
////                                Log.i("数据", obj.toString());
//
//                                //获取obj中的数据
//
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
}
