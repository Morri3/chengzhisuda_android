package com.zyq.parttime.signup;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.zyq.parttime.form.Signup;
import com.zyq.parttime.home.HomeAdapter;
import com.zyq.parttime.sp.HistoryDto;
import com.zyq.parttime.sp.SignupDto;

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

    //判断是哪个状态
    private int s = 0;//1-4分别对应四个状态

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

        list.clear();//每次要清空list，否则会叠加

        //初始化tab
        View line1 = view.findViewById(R.id.line1);
        View line2 = view.findViewById(R.id.line2);
        View line3 = view.findViewById(R.id.line3);
        View line4 = view.findViewById(R.id.line4);
        TextView status1 = view.findViewById(R.id.status1);
        TextView status2 = view.findViewById(R.id.status2);
        TextView status3 = view.findViewById(R.id.status3);
        TextView status4 = view.findViewById(R.id.status4);

        //初始显示第一个
        getActivity().runOnUiThread(() -> {
            status1.setTextColor(context.getResources().getColor(R.color.main_purple));
            status2.setTextColor(context.getResources().getColor(R.color.text_black_color));
            status3.setTextColor(context.getResources().getColor(R.color.text_black_color));
            status4.setTextColor(context.getResources().getColor(R.color.text_black_color));
            line1.setVisibility(View.VISIBLE);
            line2.setVisibility(View.INVISIBLE);
            line3.setVisibility(View.INVISIBLE);
            line4.setVisibility(View.INVISIBLE);
        });

        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                HistoryDto historyDto = new HistoryDto();
                historyDto.setTelephone("13800000001");//TODO 后面改为当前用户

                String json = JSON.toJSONString(historyDto);//dto转json
                Request request = new Request.Builder()
                        .url("http://114.55.239.213:8082/parttime/stu/history")
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
                                Log.i("data_signup", jsonObj.getString("data"));
                                JSONArray data = JSON.parseArray(jsonObj.getString("data"));
                                Log.i("data_signup2", data.toString());

                                //构造list
                                list.clear();//清空
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
                                Log.i("报名数据", list.toString());

                                //2s延时
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                getActivity().runOnUiThread(() -> {
                                    //5s延时
                                    try {
                                        Thread.sleep(2000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    //适配器的定义与设置
                                    Log.i("报名数据2", list.toString());
                                    //配置布局管理器、分割线、适配器
                                    rv = view.findViewById(R.id.rv_signup);
                                    //第一步：设置布局管理器
                                    rv.setLayoutManager(new LinearLayoutManager(context));
                                    //第二步：设置适配器
                                    signupAdapter = new SignupAdapter(context, list);
                                    signupAdapter.setHasStableIds(true);//解决数据重复
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

        //4s延时
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //点击事件
        status1.setOnClickListener(v -> {
            Log.i("状态", "1");
            getActivity().runOnUiThread(() -> {
                status1.setTextColor(context.getResources().getColor(R.color.main_purple));
                status2.setTextColor(context.getResources().getColor(R.color.text_black_color));
                status3.setTextColor(context.getResources().getColor(R.color.text_black_color));
                status4.setTextColor(context.getResources().getColor(R.color.text_black_color));
                line1.setVisibility(View.VISIBLE);
                line2.setVisibility(View.INVISIBLE);
                line3.setVisibility(View.INVISIBLE);
                line4.setVisibility(View.INVISIBLE);
            });

            //调用“全部”状态的数据  TODO
            s = 1;
            new Thread(() -> {
                try {
                    OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                    HistoryDto historyDto = new HistoryDto();
                    historyDto.setTelephone("13800000001");//TODO 后面改为当前用户

                    String json = JSON.toJSONString(historyDto);//dto转json
                    Request request = new Request.Builder()
                            .url("http://114.55.239.213:8082/parttime/stu/history")
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
                                    Log.i("data_signup", jsonObj.getString("data"));
                                    JSONArray data = JSON.parseArray(jsonObj.getString("data"));
                                    Log.i("data_signup2", data.toString());

                                    //构造list
                                    list.clear();//清空
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

        status2.setOnClickListener(v -> {
            Log.i("状态", "2");
            getActivity().runOnUiThread(() -> {
                status2.setTextColor(context.getResources().getColor(R.color.main_purple));
                status1.setTextColor(context.getResources().getColor(R.color.text_black_color));
                status3.setTextColor(context.getResources().getColor(R.color.text_black_color));
                status4.setTextColor(context.getResources().getColor(R.color.text_black_color));
                line1.setVisibility(View.INVISIBLE);
                line2.setVisibility(View.VISIBLE);
                line3.setVisibility(View.INVISIBLE);
                line4.setVisibility(View.INVISIBLE);
            });

            //调用“已报名”状态的数据  TODO
            s = 2;
            new Thread(() -> {
                try {
                    OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                    //TODO 后面改为当前用户
                    String telephone = "13800000001";
                    String signup_status = "已报名";
                    Request request = new Request.Builder()
                            .url("http://114.55.239.213:8082/parttime/stu/signup_one?telephone=" + telephone +
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
                                    Log.i("data_signup", jsonObj.getString("data"));
                                    JSONArray data = JSON.parseArray(jsonObj.getString("data"));
                                    Log.i("data_signup2", data.toString());

                                    //构造list
                                    list.clear();//清空
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

        status3.setOnClickListener(v -> {
            Log.i("状态", "3");
            getActivity().runOnUiThread(() -> {
                status3.setTextColor(context.getResources().getColor(R.color.main_purple));
                status1.setTextColor(context.getResources().getColor(R.color.text_black_color));
                status2.setTextColor(context.getResources().getColor(R.color.text_black_color));
                status4.setTextColor(context.getResources().getColor(R.color.text_black_color));
                line1.setVisibility(View.INVISIBLE);
                line2.setVisibility(View.INVISIBLE);
                line3.setVisibility(View.VISIBLE);
                line4.setVisibility(View.INVISIBLE);
            });

            //调用“已录取”状态的数据  TODO
            s = 3;
            new Thread(() -> {
                try {
                    OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                    //TODO 后面改为当前用户
                    String telephone = "13800000001";
                    String signup_status = "已录取";
                    Request request = new Request.Builder()
                            .url("http://114.55.239.213:8082/parttime/stu/signup_one?telephone=" + telephone +
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
                                    Log.i("data_signup", jsonObj.getString("data"));
                                    JSONArray data = JSON.parseArray(jsonObj.getString("data"));
                                    Log.i("data_signup2", data.toString());

                                    //构造list
                                    list.clear();//清空
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

        status4.setOnClickListener(v -> {
            Log.i("状态", "4");
            getActivity().runOnUiThread(() -> {
                status4.setTextColor(context.getResources().getColor(R.color.main_purple));
                status1.setTextColor(context.getResources().getColor(R.color.text_black_color));
                status2.setTextColor(context.getResources().getColor(R.color.text_black_color));
                status3.setTextColor(context.getResources().getColor(R.color.text_black_color));
                line1.setVisibility(View.INVISIBLE);
                line2.setVisibility(View.INVISIBLE);
                line3.setVisibility(View.INVISIBLE);
                line4.setVisibility(View.VISIBLE);
            });

            //调用“已结束”状态的数据  TODO
            s = 4;
            new Thread(() -> {
                try {
                    OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                    //TODO 后面改为当前用户
                    String telephone = "13800000001";
                    String signup_status = "已结束";
                    Request request = new Request.Builder()
                            .url("http://114.55.239.213:8082/parttime/stu/signup_one?telephone=" + telephone +
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
                                    Log.i("data_signup", jsonObj.getString("data"));
                                    JSONArray data = JSON.parseArray(jsonObj.getString("data"));
                                    Log.i("data_signup2", data.toString());

                                    //构造list
                                    list.clear();//清空
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
        return view;
    }

}
