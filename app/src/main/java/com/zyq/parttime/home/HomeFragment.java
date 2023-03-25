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

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.zyq.parttime.R;
import com.zyq.parttime.form.Position;

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

        initData();//初始化数据
        Log.i("首页兼职数据",list.toString());

        //适配器的定义与设置
        //配置布局管理器、分割线、适配器
        rv = view.findViewById(R.id.rv);
        //第一步：设置布局管理器
        rv.setLayoutManager(new LinearLayoutManager(context));
        //第二步：设置适配器
        homeAdapter = new HomeAdapter(context, list);
        rv.setAdapter(homeAdapter);

        //星推榜
        TextView enter= view.findViewById(R.id.enter);
        enter.setOnClickListener(v->{
            Log.i("aaa","aa");
            //跳转

        });

        return view;
    }

    public void initData() {
        //调用后端接口，获取兼职信息列表，赋值给list
        list.clear();
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                String telephone = "13800000001";
                String json = "{\"telephone\":\"" + telephone + "\"}";
                Log.i("json", json);
                RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

                Request request = new Request.Builder()
                        .url("http://114.55.239.213:8080/users/resumes/get?telephone=13800000001")
                        .get()
                        .build();//创建Http请求
                client.newBuilder().readTimeout(15000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.i("error", "数据获取失败");
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.isSuccessful()) {//调用成功
                            try {
//                                JSONObject jsonObject = new JSONObject(response.body().string());//json
//                                JSONObject obj = jsonObject.getJSONObject("data");//获取数据
//                                Log.i("数据", obj.toString());

                                //获取obj中的数据

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

        //TODO 现在是假数据
//        list.clear();
//        for(int i=1;i<=10;i++){
//            Position position=new Position();
//            position.setP_id(i);
//            position.setPosition_name("图书馆学生助理"+i);
//            position.setArea("校图书馆A座");
//            position.setSettlement("月结");
//            position.setExp("1年");
//            position.setSalary("15/小时");
//            position.setWork_time("周一~周五8:00-17:00"+i);
//            position.setContent("1.写通讯稿或编辑文案，负责档案资料的收集整理归档及电子档案的建立。2.负责办公室日常行政事务处理。3.办公室物资的领用管理。");
//            position.setRequirement("无");
////            position.set
//            list.add(position);
//        }
    }
}
