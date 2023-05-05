package com.zyq.parttime.signup;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.zyq.parttime.form.Signup;
import com.zyq.parttime.form.HistoryDto;
import com.zyq.parttime.util.Constants;

import java.io.IOException;
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

public class SignupFragment extends Fragment {
    private Context context;//上下文
    private List<Signup> list = new ArrayList();//存放recycleview的数据
    private RecyclerView rv; //RecyclerView布局
    private SignupAdapter signupAdapter;//适配器
    //顶部的tab
    private View line1, line2, line3, line4;
    private TextView status1, status2, status3, status4;

    //判断是哪个状态
    private int s = 0;//1-4分别对应四个状态
    //判断"已结束"tab是否没数据，值=0表示有数据，值>0表示没数据
    private int none1 = 0, none2 = 0;

    public SignupFragment() {
    }

    public SignupFragment(String context) {
//        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        context = this.getActivity();

        getActivity().runOnUiThread(() -> {
            //初始化tab
            line1 = view.findViewById(R.id.line1);
            line2 = view.findViewById(R.id.line2);
            line3 = view.findViewById(R.id.line3);
            line4 = view.findViewById(R.id.line4);
            status1 = view.findViewById(R.id.status1);
            status2 = view.findViewById(R.id.status2);
            status3 = view.findViewById(R.id.status3);
            status4 = view.findViewById(R.id.status4);

            //初始显示第一个
            status1.setTextColor(context.getResources().getColor(R.color.main_purple));
            status2.setTextColor(context.getResources().getColor(R.color.text_black_color));
            status3.setTextColor(context.getResources().getColor(R.color.text_black_color));
            status4.setTextColor(context.getResources().getColor(R.color.text_black_color));
            line1.setVisibility(View.VISIBLE);
            line2.setVisibility(View.INVISIBLE);
            line3.setVisibility(View.INVISIBLE);
            line4.setVisibility(View.INVISIBLE);
        });

        list.clear();//每次要清空list，否则会叠加
        //调api，获取该用户的所有报名
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();//创建Okhttp客户端

                HistoryDto historyDto = new HistoryDto();
                historyDto.setTelephone(Constants.telephone);//TODO 改为当前用户
                String json = JSON.toJSONString(historyDto);//dto转json

                Request request = new Request.Builder()
                        .url("http://114.55.239.213:8087/parttime/stu/history")
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
                                JSONArray data = JSON.parseArray(jsonObj.getString("data"));
                                Log.i("data_signup", data.toString());

                                //构造list
                                list.clear();//⭐清空
                                JSONObject obj0 = (JSONObject) data.get(0);
                                if (obj0.getString("memo").equals("获取历史记录成功")) {
                                    //获取成功，遍历数组，构造signup，加到列表中
                                    for (int i = 0; i < data.size(); i++) {
                                        Signup signup = new Signup();
                                        JSONObject obj = data.getJSONObject(i);
                                        int p_id = obj.getIntValue("p_id");
                                        int s_id = obj.getIntValue("s_id");
                                        String signup_status = obj.getString("signup_status");
                                        String stu_id = obj.getString("stu_id");
                                        Date create_time = obj.getDate("create_time");
                                        signup.setSignup_status(signup_status);
                                        signup.setCreate_time(create_time);
                                        signup.setS_id(s_id);
                                        signup.setP_id(p_id);
                                        signup.setStu_id(stu_id);
                                        list.add(signup);
                                    }

                                } else if (obj0.getString("memo").equals("获取历史记录失败")) {
                                    //没数据
                                    getActivity().runOnUiThread(() -> {
                                        Toast toast = Toast.makeText(context, "暂无数据", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                        toast.show();
                                    });

                                } else if (obj0.getString("memo").equals("该账号不存在")) {
                                    getActivity().runOnUiThread(() -> {
                                        Toast toast = Toast.makeText(context, "该账号不存在", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                        toast.show();
                                    });

                                } else if (obj0.getString("memo").equals("输入有误")) {
                                    getActivity().runOnUiThread(() -> {
                                        Toast toast = Toast.makeText(context, "输入有误", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                        toast.show();
                                    });
                                }
                                Log.i("报名数据", list.toString());

//                                //2s延时
//                                try {
//                                    Thread.sleep(2000);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }

                                getActivity().runOnUiThread(() -> {
                                    //等待加载的toast
                                    Toast toast = Toast.makeText(context, "数据加载中，请稍等~", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                    toast.show();

                                    //适配器的定义与设置
                                    Log.i("报名数据2", list.toString());
                                    //配置布局管理器、分割线、适配器
                                    rv = view.findViewById(R.id.rv_signup);
                                    //第一步：设置布局管理器
                                    rv.setLayoutManager(new LinearLayoutManager(context));
                                    //第二步：设置适配器
                                    signupAdapter = new SignupAdapter(context, list);
                                    //刷新适配器
                                    signupAdapter.notifyDataSetChanged();
                                    rv.setAdapter(signupAdapter);
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

//        //4s延时
//        try {
//            Thread.sleep(4000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        //点击事件
        //状态1
        status1.setOnClickListener(v -> {
            list.clear();//清空

            Log.i("状态", "全部");
            getActivity().runOnUiThread(() -> {
                status1.setTextColor(context.getResources().getColor(R.color.main_purple));
                status2.setTextColor(context.getResources().getColor(R.color.text_black_color));
                status3.setTextColor(context.getResources().getColor(R.color.text_black_color));
                status4.setTextColor(context.getResources().getColor(R.color.text_black_color));
                line1.setVisibility(View.VISIBLE);
                line2.setVisibility(View.INVISIBLE);
                line3.setVisibility(View.INVISIBLE);
                line4.setVisibility(View.INVISIBLE);

                //等待加载的toast
                Toast toast = Toast.makeText(context, "数据加载中，请稍等~", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                toast.show();
            });

            //调用“全部”状态的数据  TODO
            s = 1;
            new Thread(() -> {
                try {
                    OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                    HistoryDto historyDto = new HistoryDto();
                    historyDto.setTelephone(Constants.telephone);//TODO 改为当前用户

                    String json = JSON.toJSONString(historyDto);//dto转json
                    Request request = new Request.Builder()
                            .url("http://114.55.239.213:8087/parttime/stu/history")
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
                                    JSONArray data = JSON.parseArray(jsonObj.getString("data"));
                                    Log.i("data_signup", data.toString());

                                    //构造list
                                    list.clear();//清空
                                    JSONObject obj0 = (JSONObject) data.get(0);
                                    if (obj0.getString("memo").equals("获取历史记录成功")) {
                                        //获取成功
                                        for (int i = 0; i < data.size(); i++) {
                                            Signup signup = new Signup();
                                            JSONObject obj = data.getJSONObject(i);
                                            int p_id = obj.getIntValue("p_id");
                                            int s_id = obj.getIntValue("s_id");
                                            String signup_status = obj.getString("signup_status");
                                            String stu_id = obj.getString("stu_id");
                                            Date create_time = obj.getDate("create_time");
                                            signup.setSignup_status(signup_status);
                                            signup.setCreate_time(create_time);
                                            signup.setS_id(s_id);
                                            signup.setP_id(p_id);
                                            signup.setStu_id(stu_id);
                                            list.add(signup);
                                        }

                                    } else if (obj0.getString("memo").equals("获取历史记录失败")) {
                                        //没数据
                                        getActivity().runOnUiThread(() -> {
                                            Toast toast = Toast.makeText(context, "暂无数据", Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                            toast.show();
                                        });

                                    } else if (obj0.getString("memo").equals("该账号不存在")) {
                                        getActivity().runOnUiThread(() -> {
                                            Toast toast = Toast.makeText(context, "该账号不存在", Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                            toast.show();
                                        });

                                    } else if (obj0.getString("memo").equals("输入有误")) {
                                        getActivity().runOnUiThread(() -> {
                                            Toast toast = Toast.makeText(context, "输入有误", Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                            toast.show();
                                        });

                                    }
                                    Log.i("报名数据", list.toString());

                                    //2s延时
                                    try {
                                        Thread.sleep(2000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    getActivity().runOnUiThread(() -> {
                                        //适配器的定义与设置
                                        Log.i("报名数据2", list.toString());
                                        //配置布局管理器、分割线、适配器
                                        rv = view.findViewById(R.id.rv_signup);
                                        //第一步：设置布局管理器
                                        rv.setLayoutManager(new LinearLayoutManager(context));
                                        //第二步：设置适配器
                                        signupAdapter = new SignupAdapter(context, list);
                                        //刷新适配器
                                        signupAdapter.notifyDataSetChanged();
                                        rv.setAdapter(signupAdapter);
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

        //状态2
        status2.setOnClickListener(v -> {
            list.clear();//清空

            Log.i("状态", "已报名");
            getActivity().runOnUiThread(() -> {
                status2.setTextColor(context.getResources().getColor(R.color.main_purple));
                status1.setTextColor(context.getResources().getColor(R.color.text_black_color));
                status3.setTextColor(context.getResources().getColor(R.color.text_black_color));
                status4.setTextColor(context.getResources().getColor(R.color.text_black_color));
                line1.setVisibility(View.INVISIBLE);
                line2.setVisibility(View.VISIBLE);
                line3.setVisibility(View.INVISIBLE);
                line4.setVisibility(View.INVISIBLE);

                //等待加载的toast
                Toast toast = Toast.makeText(context, "数据加载中，请稍等~", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                toast.show();
            });

            //调用“已报名”状态的数据
            s = 2;
            new Thread(() -> {
                try {
                    OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                    //TODO 改为当前用户
                    String telephone = Constants.telephone;
                    String signup_status = "已报名";
                    Request request = new Request.Builder()
                            .url("http://114.55.239.213:8087/parttime/stu/signup_one?telephone=" + telephone +
                                    "&signup_status=" + signup_status)
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
                                    JSONArray data = JSON.parseArray(jsonObj.getString("data"));
                                    Log.i("data_signup", data.toString());

                                    //构造list
                                    list.clear();//清空
                                    JSONObject obj0 = (JSONObject) data.get(0);
                                    if (obj0.getString("memo").equals("获取历史记录成功")) {
                                        for (int i = 0; i < data.size(); i++) {
                                            Signup signup = new Signup();
                                            JSONObject obj = data.getJSONObject(i);
                                            int p_id = obj.getIntValue("p_id");
                                            int s_id = obj.getIntValue("s_id");
                                            String signup_status = obj.getString("signup_status");
                                            String stu_id = obj.getString("stu_id");
                                            Date create_time = obj.getDate("create_time");
                                            signup.setSignup_status(signup_status);
                                            signup.setCreate_time(create_time);
                                            signup.setS_id(s_id);
                                            signup.setP_id(p_id);
                                            signup.setStu_id(stu_id);
                                            list.add(signup);
                                        }

                                    } else if (obj0.getString("memo").equals("获取历史记录失败")) {
                                        //没数据
                                        getActivity().runOnUiThread(() -> {
                                            Toast toast = Toast.makeText(context, "暂无数据", Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                            toast.show();
                                        });

                                    } else if (obj0.getString("memo").equals("该账号不存在")) {
                                        getActivity().runOnUiThread(() -> {
                                            Toast toast = Toast.makeText(context, "该账号不存在", Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                            toast.show();
                                        });

                                    } else if (obj0.getString("memo").equals("输入有误")) {
                                        getActivity().runOnUiThread(() -> {
                                            Toast toast = Toast.makeText(context, "输入有误", Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                            toast.show();
                                        });
                                    }
                                    Log.i("报名数据", list.toString());

                                    //2s延时
                                    try {
                                        Thread.sleep(2000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    getActivity().runOnUiThread(() -> {
                                        //适配器的定义与设置
                                        Log.i("报名数据2", list.toString());
                                        //配置布局管理器、分割线、适配器
                                        rv = view.findViewById(R.id.rv_signup);
                                        //第一步：设置布局管理器
                                        rv.setLayoutManager(new LinearLayoutManager(context));
                                        //第二步：设置适配器
                                        signupAdapter = new SignupAdapter(context, list);
                                        //刷新适配器
                                        signupAdapter.notifyDataSetChanged();
                                        rv.setAdapter(signupAdapter);
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

        //状态3
        status3.setOnClickListener(v -> {
            list.clear();//清空

            Log.i("状态", "已录取");
            getActivity().runOnUiThread(() -> {
                status3.setTextColor(context.getResources().getColor(R.color.main_purple));
                status1.setTextColor(context.getResources().getColor(R.color.text_black_color));
                status2.setTextColor(context.getResources().getColor(R.color.text_black_color));
                status4.setTextColor(context.getResources().getColor(R.color.text_black_color));
                line1.setVisibility(View.INVISIBLE);
                line2.setVisibility(View.INVISIBLE);
                line3.setVisibility(View.VISIBLE);
                line4.setVisibility(View.INVISIBLE);

                //等待加载的toast
                Toast toast = Toast.makeText(context, "数据加载中，请稍等~", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                toast.show();
            });

            //调用“已录取”状态的数据
            s = 3;
            new Thread(() -> {
                try {
                    OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                    //TODO 后面改为当前用户
                    String telephone = Constants.telephone;
                    String signup_status = "已录取";
                    Request request = new Request.Builder()
                            .url("http://114.55.239.213:8087/parttime/stu/signup_one?telephone=" + telephone +
                                    "&signup_status=" + signup_status)
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
                                    JSONArray data = JSON.parseArray(jsonObj.getString("data"));
                                    Log.i("data_signup", data.toString());

                                    //构造list
                                    list.clear();//清空
                                    JSONObject obj0 = (JSONObject) data.get(0);
                                    if (obj0.getString("memo").equals("获取历史记录成功")) {
                                        for (int i = 0; i < data.size(); i++) {
                                            Signup signup = new Signup();
                                            JSONObject obj = data.getJSONObject(i);
                                            int p_id = obj.getIntValue("p_id");
                                            int s_id = obj.getIntValue("s_id");
                                            String signup_status = obj.getString("signup_status");
                                            String stu_id = obj.getString("stu_id");
                                            Date create_time = obj.getDate("create_time");
                                            signup.setSignup_status(signup_status);
                                            signup.setCreate_time(create_time);
                                            signup.setS_id(s_id);
                                            signup.setP_id(p_id);
                                            signup.setStu_id(stu_id);
                                            list.add(signup);
                                        }

                                    } else if (obj0.getString("memo").equals("获取历史记录失败")) {
                                        //没数据
                                        getActivity().runOnUiThread(() -> {
                                            Toast toast = Toast.makeText(context, "暂无数据", Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                            toast.show();
                                        });

                                    } else if (obj0.getString("memo").equals("该账号不存在")) {
                                        getActivity().runOnUiThread(() -> {
                                            Toast toast = Toast.makeText(context, "该账号不存在", Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                            toast.show();
                                        });

                                    } else if (obj0.getString("memo").equals("输入有误")) {
                                        getActivity().runOnUiThread(() -> {
                                            Toast toast = Toast.makeText(context, "输入有误", Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                            toast.show();
                                        });
                                    }
                                    Log.i("报名数据", list.toString());

                                    //2s延时
                                    try {
                                        Thread.sleep(2000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    getActivity().runOnUiThread(() -> {
                                        //适配器的定义与设置
                                        Log.i("报名数据2", list.toString());
                                        //配置布局管理器、分割线、适配器
                                        rv = view.findViewById(R.id.rv_signup);
                                        //第一步：设置布局管理器
                                        rv.setLayoutManager(new LinearLayoutManager(context));
                                        //第二步：设置适配器
                                        signupAdapter = new SignupAdapter(context, list);
                                        //刷新适配器
                                        signupAdapter.notifyDataSetChanged();
                                        rv.setAdapter(signupAdapter);
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

        //状态4
        status4.setOnClickListener(v -> {
            list.clear();//清空

            Log.i("状态", "已结束");
            getActivity().runOnUiThread(() -> {
                status4.setTextColor(context.getResources().getColor(R.color.main_purple));
                status1.setTextColor(context.getResources().getColor(R.color.text_black_color));
                status2.setTextColor(context.getResources().getColor(R.color.text_black_color));
                status3.setTextColor(context.getResources().getColor(R.color.text_black_color));
                line1.setVisibility(View.INVISIBLE);
                line2.setVisibility(View.INVISIBLE);
                line3.setVisibility(View.INVISIBLE);
                line4.setVisibility(View.VISIBLE);

                //等待加载的toast
                Toast toast = Toast.makeText(context, "数据加载中，请稍等~", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                toast.show();
            });

            //调用“已结束”状态的数据
            s = 4;
            new Thread(() -> {
                try {
                    OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                    //TODO 改为当前用户
                    String telephone = Constants.telephone;
                    String signup_status = "已结束";
                    Request request = new Request.Builder()
                            .url("http://114.55.239.213:8087/parttime/stu/signup_one?telephone=" + telephone +
                                    "&signup_status=" + signup_status)
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
                                    JSONArray data = JSON.parseArray(jsonObj.getString("data"));
                                    Log.i("data_signup", data.toString());

                                    //构造list
                                    list.clear();//清空
                                    JSONObject obj0 = (JSONObject) data.get(0);
                                    if (obj0.getString("memo").equals("获取历史记录成功")) {
                                        //有数据
                                        for (int i = 0; i < data.size(); i++) {
                                            Signup signup = new Signup();
                                            JSONObject obj = data.getJSONObject(i);
                                            int p_id = obj.getIntValue("p_id");
                                            int s_id = obj.getIntValue("s_id");
                                            String signup_status = obj.getString("signup_status");
                                            String stu_id = obj.getString("stu_id");
                                            Date create_time = obj.getDate("create_time");
                                            signup.setSignup_status(signup_status);
                                            signup.setCreate_time(create_time);
                                            signup.setS_id(s_id);
                                            signup.setP_id(p_id);
                                            signup.setStu_id(stu_id);
                                            list.add(signup);
                                        }
                                        none1 = 0;

                                    } else if (obj0.getString("memo").equals("获取历史记录失败")) {
                                        //无”已结束“状态的报名数据，不做toast
                                        none1 = 1;
                                    } else if (obj0.getString("memo").equals("该账号不存在")) {
//                                        getActivity().runOnUiThread(() -> {
//                                            Toast toast = Toast.makeText(context, "该账号不存在", Toast.LENGTH_SHORT);
//                                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
//                                            toast.show();

                                        none1 = 2;
//                                        });

                                    } else if (obj0.getString("memo").equals("输入有误")) {
//                                        getActivity().runOnUiThread(() -> {
//                                            Toast toast = Toast.makeText(context, "输入有误", Toast.LENGTH_SHORT);
//                                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
//                                            toast.show();

                                        none1 = 3;
//                                        });
                                    }
                                    Log.i("报名数据", list.toString());

                                    //获取已取消的报名
                                    new Thread(() -> {
                                        try {
                                            OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                                            //TODO 改为当前用户
                                            String telephone = Constants.telephone;
                                            String signup_status = "已取消";
                                            Request request = new Request.Builder()
                                                    .url("http://114.55.239.213:8087/parttime/stu/signup_one?telephone=" + telephone +
                                                            "&signup_status=" + signup_status)
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
                                                            JSONArray data2 = JSON.parseArray(jsonObj.getString("data"));

                                                            //构造list
                                                            JSONObject obj2 = (JSONObject) data2.get(0);
                                                            if (obj2.getString("memo").equals("获取历史记录成功")) {
                                                                for (int i = 0; i < data2.size(); i++) {
                                                                    Signup signup = new Signup();
                                                                    JSONObject obj = data2.getJSONObject(i);
                                                                    int p_id = obj.getIntValue("p_id");
                                                                    int s_id = obj.getIntValue("s_id");
                                                                    String signup_status = obj.getString("signup_status");
                                                                    String stu_id = obj.getString("stu_id");
                                                                    Date create_time = obj.getDate("create_time");
                                                                    signup.setSignup_status(signup_status);
                                                                    signup.setCreate_time(create_time);
                                                                    signup.setS_id(s_id);
                                                                    signup.setP_id(p_id);
                                                                    signup.setStu_id(stu_id);
                                                                    list.add(signup);
                                                                }
                                                                none2 = 0;

                                                            } else if (obj2.getString("memo").equals("获取历史记录失败")) {
//                                                                getActivity().runOnUiThread(() -> {
//                                                                    //没数据
//                                                                    Toast toast = Toast.makeText(context, "暂无数据", Toast.LENGTH_SHORT);
//                                                                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
//                                                                    toast.show();

                                                                none2 = 1;
//                                                                });

                                                            } else if (obj2.getString("memo").equals("该账号不存在")) {
//                                                                getActivity().runOnUiThread(() -> {
//                                                                    Toast toast = Toast.makeText(context, "该账号不存在", Toast.LENGTH_SHORT);
//                                                                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
//                                                                    toast.show();

                                                                none2 = 2;
//                                                                });

                                                            } else if (obj2.getString("memo").equals("输入有误")) {
//                                                                getActivity().runOnUiThread(() -> {
//                                                                    Toast toast = Toast.makeText(context, "输入有误", Toast.LENGTH_SHORT);
//                                                                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
//                                                                    toast.show();

                                                                none2 = 3;
//                                                                });
                                                            }
                                                            Log.i("报名数据3", list.toString());

                                                            //2s延时
                                                            try {
                                                                Thread.sleep(2000);
                                                            } catch (InterruptedException e) {
                                                                e.printStackTrace();
                                                            }

                                                            getActivity().runOnUiThread(() -> {
                                                                //若没数据或获取数据出错，这里显示toast
                                                                if (none1 == 0 || none2 == 0) {
                                                                    //有一个有数据，就表示已结束tab中有数据，不做操作
                                                                } else {
                                                                    //此时none1和none2都>0
                                                                    if (none1 == 1 && none2 == 1) {
                                                                        //没数据/获取数据失败
                                                                        Toast toast = Toast.makeText(context, "暂无数据", Toast.LENGTH_SHORT);
                                                                        toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                                                        toast.show();
                                                                    } else if (none1 == 2 || none2 == 2) {
                                                                        //该账号不存在
                                                                        Toast toast = Toast.makeText(context, "该账号不存在", Toast.LENGTH_SHORT);
                                                                        toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                                                        toast.show();
                                                                    } else if (none1 == 3 || none2 == 3) {
                                                                        //该账号不存在
                                                                        Toast toast = Toast.makeText(context, "输入有误", Toast.LENGTH_SHORT);
                                                                        toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                                                        toast.show();
                                                                    }
                                                                }

                                                                //适配器的定义与设置
                                                                Log.i("报名数据3", list.toString());
                                                                //配置布局管理器、分割线、适配器
                                                                rv = view.findViewById(R.id.rv_signup);
                                                                //第一步：设置布局管理器
                                                                rv.setLayoutManager(new LinearLayoutManager(context));
                                                                //第二步：设置适配器
                                                                signupAdapter = new SignupAdapter(context, list);
                                                                //刷新适配器
                                                                signupAdapter.notifyDataSetChanged();
                                                                rv.setAdapter(signupAdapter);
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
        return view;
    }
}
