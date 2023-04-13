package com.zyq.parttime.position;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.zyq.parttime.HomeActivity;
import com.zyq.parttime.R;
import com.zyq.parttime.form.Position;
import com.zyq.parttime.sp.CommentInfo;
import com.zyq.parttime.sp.EmpInfo;
import com.zyq.parttime.sp.MarkInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PositionDetail extends AppCompatActivity {
    private Context context;//上下文
    private List<Position> list = new ArrayList();//存放recycleview的数据
    private RecyclerView rv; //RecyclerView布局
    private PositionDetailAdapter positionDetailAdapter;//适配器
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//取消默认顶部标题栏
        setContentView(R.layout.position_detail);
        context = this.getBaseContext();
        Log.i("context", context.toString());

        //获取传来的数据
        Intent editpro = getIntent();
        int p_id = editpro.getIntExtra("p_id", -1);//兼职id
        int pos = editpro.getIntExtra("pos", -1);//当前选择的item的下标
        list = (List<Position>) editpro.getSerializableExtra("position_data");//获得传来的列表

        Log.i("p_id", p_id + "");
        Log.i("pos", pos + "");
        Log.i("list", list.toString());

        //调api，由op_id找到employer的信息
        EmpInfo empInfo = new EmpInfo();
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                Request request = new Request.Builder()
                        .url("http://114.55.239.213:8082/users/info/get_emp?telephone="
                                + list.get(pos).getOp_id())
                        .get()
                        .build();//创建Http请求
                client.newBuilder()
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .readTimeout(15, TimeUnit.SECONDS)
                        .writeTimeout(15, TimeUnit.SECONDS)
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
                                Log.i("data_emp", jsonObj.getString("data"));
                                com.alibaba.fastjson.JSONObject data = JSON.parseObject(jsonObj.getString("data"));

                                String emp_name = data.getString("emp_name");
                                int gender = data.getIntValue("gender");
                                String emails = data.getString("emails");
                                int age = data.getIntValue("age");
                                String telephone = data.getString("telephone");
                                String jno = data.getString("jno");
                                String unit_name = data.getString("unit_name");
                                String unit_descriptions = data.getString("unit_descriptions");
                                String unit_loc = data.getString("unit_loc");
                                int job_nums = data.getIntValue("job_nums");

                                empInfo.setEmp_name(emp_name);
                                empInfo.setUnit_name(unit_name);
                                empInfo.setUnit_loc(unit_loc);
                                empInfo.setUnit_descriptions(unit_descriptions);
                                empInfo.setJob_nums(job_nums);

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

        //3s延时
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //调api，获取该职位的评分数据
        MarkInfo markInfo = new MarkInfo();
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                Request request = new Request.Builder()
                        .url("http://114.55.239.213:8082/mark/getAll?p_id=" + list.get(pos).getP_id())
                        .get()
                        .build();//创建Http请求
                client.newBuilder()
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .readTimeout(15, TimeUnit.SECONDS)
                        .writeTimeout(15, TimeUnit.SECONDS)
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
                                Log.i("data_mark", jsonObj.getString("data"));
                                JSONObject data = JSON.parseObject(jsonObj.getString("data"));
                                Log.i("data_mark2", data.toString());

                                int m_id = data.getIntValue("m_id");
                                int p_id = data.getIntValue("p_id");
                                float total_score = data.getFloatValue("total_score");
                                int pf = data.getIntValue("pf");
                                int pl = data.getIntValue("pl");
                                int we = data.getIntValue("we");
                                int lt = data.getIntValue("lt");
                                int pt = data.getIntValue("pt");
                                int ods = data.getIntValue("ods");
                                int dsps = data.getIntValue("dsps");

                                markInfo.setM_id(m_id);
                                markInfo.setP_id(p_id);
                                markInfo.setDsps(dsps);
                                markInfo.setLt(lt);
                                markInfo.setOds(ods);
                                markInfo.setPf(pf);
                                markInfo.setPl(pl);
                                markInfo.setPt(pt);
                                markInfo.setTotal_score(total_score);
                                markInfo.setWe(we);
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

        //3s延时
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //调api，获取该职位的评论数据
        CommentInfo commentInfo = new CommentInfo();
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                Request request = new Request.Builder()
                        .url("http://114.55.239.213:8082/comments/getAll?p_id=" + list.get(pos).getP_id())
                        .get()
                        .build();//创建Http请求
                client.newBuilder()
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .readTimeout(15, TimeUnit.SECONDS)
                        .writeTimeout(15, TimeUnit.SECONDS)
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
                                JSONObject data_comment = JSON.parseObject(jsonObj.getString("data"));
                                Log.i("data_comment", data_comment.toString());

                                int p_id = data_comment.getIntValue("p_id");
                                String content = data_comment.getString("content");
                                String memo = data_comment.getString("memo");

                                commentInfo.setP_id(p_id);
                                commentInfo.setContent(content);
                                commentInfo.setMemo(memo);

                                //适配器的定义与设置
                                runOnUiThread(() -> {
                                    //配置布局管理器、分割线、适配器
                                    rv = (RecyclerView) findViewById(R.id.rv2);
                                    //第一步：设置布局管理器
                                    rv.setLayoutManager(new LinearLayoutManager(context));
                                    //第二步：设置适配器
                                    positionDetailAdapter = new PositionDetailAdapter(context, list.get(pos),
                                            empInfo, markInfo, commentInfo);//传入当前的item
                                    rv.setAdapter(positionDetailAdapter);

                                    //返回
                                    back = findViewById(R.id.back);
                                    back.setOnClickListener(v -> {
                                        Log.i("back", "返回到上一页");
                                        //跳转到首页
                                        Intent i = new Intent();
                                        i.setClass(PositionDetail.this, HomeActivity.class);
                                        //一定要指定是第几个pager，这里填写1
                                        i.putExtra("id", 1);
                                        startActivity(i);
                                    });
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
    }
}
