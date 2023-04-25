package com.zyq.parttime.home;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.zyq.parttime.loginreg.LoginActivity;
import com.zyq.parttime.loginreg.RegActivity;
import com.zyq.parttime.sp.GetPosition;
import com.zyq.parttime.sp.StuRegister;
import com.zyq.parttime.util.Constants;
import com.zyq.parttime.util.DateData;
import com.zyq.parttime.util.PositionCategoryData;

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

public class HomeFragment extends Fragment {
    private Context context;//上下文
    private List<Position> list = new ArrayList();//存放recycleview的数据
    private RecyclerView rv; //RecyclerView布局
    private HomeAdapter homeAdapter;//适配器
    private Spinner positionSpinner;//搜索下拉框
    private ImageView down;//筛选确定图标

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

        //搜索下拉框
        positionSpinner = view.findViewById(R.id.search_position);
        down = view.findViewById(R.id.down);

//        //创建Constant常量类中的usersSignup列表
//        Constants.usersSignup=new ArrayList<>();

        //获取意向兼职
        List<String> intentions = new ArrayList<>();//存放意向兼职
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                Request request = new Request.Builder()
                        .url("http://114.55.239.213:8087/users/intention/get?telephone=" + Constants.telephone)//TODO 当前登录用户
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
                                JSONArray data_intention = JSON.parseArray(jsonObj.getString("data"));
                                Log.i("data_intention实体", data_intention.toString());

                                JSONObject isNull = data_intention.getJSONObject(0);
                                //有数据
                                if ((isNull.getString("memo")).equals("存在意向兼职")) {
                                    for (int i = 0; i < data_intention.size(); i++) {
                                        JSONObject jsonObject = data_intention.getJSONObject(i);
                                        String content = jsonObject.getString("content");
                                        intentions.add(content);//加入到列表中
                                    }
                                    Log.i("意向兼职数据", intentions.toString());
                                } else {
                                    //没数据，什么都不做
                                    Log.i("意向兼职数据为空", "意向兼职数据为空");
                                }

                                //获取兼职数据
                                new Thread(() -> {
                                    try {
                                        OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                                        GetPosition getPosition = new GetPosition();//DTO
                                        getPosition.setIntentions(intentions);//参数是意向兼职
                                        String json = JSON.toJSONString(getPosition);//dto转string
                                        Request request = new Request.Builder()
                                                .url("http://114.55.239.213:8087/parttime/stu/get_intention")
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
                                                        JSONArray data_position = JSON.parseArray(jsonObj.getString("data"));
                                                        Log.i("data_position", data_position.toString());

                                                        //有兼职数据
                                                        JSONObject obj= (JSONObject) data_position.get(0);
                                                        Log.i("obj",obj.toString());
                                                        if (obj.getString("memo").equals("兼职获取成功")) {//根据第一个item的memo的信息判断是否有数据
                                                            for (int i = 0; i < data_position.size(); i++) {
                                                                int p_id = data_position.getJSONObject(i).getIntValue("p_id");
                                                                String op_id = data_position.getJSONObject(i).getString("op_id");
                                                                String area = data_position.getJSONObject(i).getString("area");
                                                                String category = data_position.getJSONObject(i).getString("category");
                                                                String content = data_position.getJSONObject(i).getString("content");
                                                                String requirement = data_position.getJSONObject(i).getString("requirement");
                                                                String work_time = data_position.getJSONObject(i).getString("work_time");
                                                                String create_time = data_position.getJSONObject(i).getString("create_time");
                                                                String exp = data_position.getJSONObject(i).getString("exp");
                                                                String position_name = data_position.getJSONObject(i).getString("position_name");
                                                                String position_status = data_position.getJSONObject(i).getString("position_status");
                                                                String salary = data_position.getJSONObject(i).getString("salary");
                                                                String settlement = data_position.getJSONObject(i).getString("settlement");
                                                                String signup_ddl = data_position.getJSONObject(i).getString("signup_ddl");
                                                                String slogan = data_position.getJSONObject(i).getString("slogan");

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

                                                                Log.i("首页数据list", list.toString());//兼职数据输出
                                                            }
                                                        } else if (data_position.getJSONObject(0).getString("memo").equals("暂无该类型兼职")) {
                                                            //没有兼职数据
                                                            getActivity().runOnUiThread(() -> {
                                                                Toast toast = Toast.makeText(context, "暂无兼职，请耐心等待~", Toast.LENGTH_SHORT);
                                                                toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                                                toast.show();

                                                                //构造空兼职
                                                                Position position = new Position();
                                                                position.setP_id(0);
                                                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                                try {
                                                                    Date now = sdf.parse(sdf.format(new Date()));
                                                                    position.setUpdate_time(now);
                                                                } catch (ParseException e) {
                                                                    e.printStackTrace();
                                                                }
                                                                position.setContent("暂无兼职");//content标记是否有兼职
                                                                list.add(position);//加入list

                                                                Log.i("暂无兼职", "暂无兼职");//兼职数据输出
                                                            });
                                                        } else if (data_position.getJSONObject(0).getString("memo").equals("请选择兼职种类后再筛选")) {
                                                            //没有兼职数据
                                                            getActivity().runOnUiThread(() -> {
                                                                Toast toast = Toast.makeText(context, "请选择兼职种类后再筛选", Toast.LENGTH_SHORT);
                                                                toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                                                toast.show();

                                                                //构造空兼职
                                                                Position position = new Position();
                                                                position.setP_id(0);
                                                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                                try {
                                                                    Date now = sdf.parse(sdf.format(new Date()));
                                                                    position.setUpdate_time(now);
                                                                } catch (ParseException e) {
                                                                    e.printStackTrace();
                                                                }
                                                                position.setContent("暂无兼职");//content标记是否有兼职
                                                                list.add(position);//加入list

                                                                Log.i("请选择兼职种类后再筛选", "请选择兼职种类后再筛选");//兼职数据输出
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
                                Log.i("首页兼职数据", list.toString());
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

        //5s延时
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //创建PositionCategoryData类，并配置默认值
        PositionCategoryData data = new PositionCategoryData("无");

        //构造种类列表
        List<String> category = new ArrayList<>();
        category.add("");
        category.add("课程助教");
        category.add("学生助理");
        category.add("军训助理");
        category.add("体测助理");
        category.add("讲解员");
        category.add("公寓宣传员");
        category.add("班助");
        category.add("服务员");

        //适配器
        ArrayAdapter<String> positionAdapter = new ArrayAdapter<>(context,
                R.layout.position_item, category);//把种类的列表添加到适配器中，使用自定义的item样式
        positionAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);//设置下拉选项内容
        positionSpinner.setAdapter(positionAdapter);//设置适配器

        //监听下拉框
        positionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                data.setCategory(positionAdapter.getItem(pos));//用DateData对象来处理选中的年份
                getActivity().runOnUiThread(() -> {
                    positionSpinner.setSelection(pos);//设置当前选中的item
                });
                Log.i("选择的兼职种类", data.getCategory());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //筛选下拉框实现【点击图标，实现筛选，调api】
        getActivity().runOnUiThread(() -> {
            down.setOnClickListener(v -> {
                list.clear();//清空兼职列表

                new Thread(() -> {
                    try {
                        OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                        Request request;
                        if (data.getCategory().equals("")) {//获取所有兼职
                            //默认还是以意向兼职在开头
                            GetPosition getPosition = new GetPosition();//DTO
                            getPosition.setIntentions(intentions);//参数是意向兼职
                            String json = JSON.toJSONString(getPosition);//dto转string
                            request = new Request.Builder()
                                    .url("http://114.55.239.213:8087/parttime/stu/get_intention")
                                    .post(RequestBody.create(MediaType.parse("application/json"), json))
                                    .build();//创建Http请求
                        } else {//获取指定种类的兼职
                            request = new Request.Builder()
                                    .url("http://114.55.239.213:8087/parttime/stu/get_category?category="
                                            + data.getCategory())
                                    .get()
                                    .build();//创建Http请求
                        }

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
                                        JSONArray data_position = JSON.parseArray(jsonObj.getString("data"));
                                        Log.i("data_position实体", data_position.toString());

                                        //有兼职数据
                                        JSONObject obj= (JSONObject) data_position.get(0);
                                        if (obj.getString("memo").equals("兼职获取成功")) {//根据第一个item的memo的信息判断是否有数据
                                            for (int i = 0; i < data_position.size(); i++) {
                                                int p_id = data_position.getJSONObject(i).getIntValue("p_id");
                                                String op_id = data_position.getJSONObject(i).getString("op_id");
                                                String area = data_position.getJSONObject(i).getString("area");
                                                String category = data_position.getJSONObject(i).getString("category");
                                                String content = data_position.getJSONObject(i).getString("content");
                                                String requirement = data_position.getJSONObject(i).getString("requirement");
                                                String work_time = data_position.getJSONObject(i).getString("work_time");
                                                String create_time = data_position.getJSONObject(i).getString("create_time");
                                                String exp = data_position.getJSONObject(i).getString("exp");
                                                String position_name = data_position.getJSONObject(i).getString("position_name");
                                                String position_status = data_position.getJSONObject(i).getString("position_status");
                                                String salary = data_position.getJSONObject(i).getString("salary");
                                                String settlement = data_position.getJSONObject(i).getString("settlement");
                                                String signup_ddl = data_position.getJSONObject(i).getString("signup_ddl");
                                                String slogan = data_position.getJSONObject(i).getString("slogan");

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

                                                Log.i("筛选后的首页数据list", list.toString());//兼职数据输出
                                            }

                                        } else if (data_position.getJSONObject(0).getString("memo").equals("暂无该类型兼职")) {
                                            //没有兼职数据
                                            getActivity().runOnUiThread(() -> {
                                                Toast toast = Toast.makeText(context, "暂无兼职，请耐心等待~", Toast.LENGTH_SHORT);
                                                toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                                toast.show();

                                                //构造空兼职
                                                Position position = new Position();
                                                position.setP_id(0);
                                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                try {
                                                    Date now = sdf.parse(sdf.format(new Date()));
                                                    position.setUpdate_time(now);
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                position.setContent("暂无兼职");//content标记是否有兼职
                                                list.add(position);//加入list

                                                Log.i("暂无兼职", "暂无兼职");//兼职数据输出
                                            });
                                        } else if (data_position.getJSONObject(0).getString("memo").equals("请选择兼职种类后再筛选")) {
                                            //没有兼职数据
                                            getActivity().runOnUiThread(() -> {
                                                Toast toast = Toast.makeText(context, "请选择兼职种类后再筛选", Toast.LENGTH_SHORT);
                                                toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                                toast.show();

                                                //构造空兼职
                                                Position position = new Position();
                                                position.setP_id(0);
                                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                try {
                                                    Date now = sdf.parse(sdf.format(new Date()));
                                                    position.setUpdate_time(now);
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                position.setContent("暂无兼职");//content标记是否有兼职
                                                list.add(position);//加入list

                                                Log.i("请选择兼职种类后再筛选", "请选择兼职种类后再筛选");//兼职数据输出
                                            });
                                        }

                                        //刷新adapter
                                        getActivity().runOnUiThread(() -> {
                                            rv.setAdapter(homeAdapter);
                                            homeAdapter.notifyDataSetChanged();
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
