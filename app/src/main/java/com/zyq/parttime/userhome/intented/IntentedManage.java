package com.zyq.parttime.userhome.intented;

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
import com.zyq.parttime.form.EditIntention;
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

public class IntentedManage extends AppCompatActivity {
    private Context context;
    private List<String> intented = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//取消默认顶部标题栏
        setContentView(R.layout.activity_intented);
        context = this.getBaseContext();

        //组件初始化
        ImageView back = findViewById(R.id.back);
        AppCompatButton button_save = findViewById(R.id.btn_intented);
        ImageView item1 = findViewById(R.id.item1);
        ImageView item2 = findViewById(R.id.item2);
        ImageView item3 = findViewById(R.id.item3);
        ImageView item4 = findViewById(R.id.item4);
        ImageView item5 = findViewById(R.id.item5);
        ImageView item6 = findViewById(R.id.item6);
        ImageView item7 = findViewById(R.id.item7);
        ImageView item8 = findViewById(R.id.item8);
        ImageView select1 = findViewById(R.id.select1);
        ImageView select2 = findViewById(R.id.select2);
        ImageView select3 = findViewById(R.id.select3);
        ImageView select4 = findViewById(R.id.select4);
        ImageView select5 = findViewById(R.id.select5);
        ImageView select6 = findViewById(R.id.select6);
        ImageView select7 = findViewById(R.id.select7);
        ImageView select8 = findViewById(R.id.select8);

        //不显示已选择的
        item1.setVisibility(View.VISIBLE);
        item2.setVisibility(View.VISIBLE);
        item3.setVisibility(View.VISIBLE);
        item4.setVisibility(View.VISIBLE);
        item5.setVisibility(View.VISIBLE);
        item6.setVisibility(View.VISIBLE);
        item7.setVisibility(View.VISIBLE);
        item8.setVisibility(View.VISIBLE);
        select1.setVisibility(View.INVISIBLE);
        select2.setVisibility(View.INVISIBLE);
        select3.setVisibility(View.INVISIBLE);
        select4.setVisibility(View.INVISIBLE);
        select5.setVisibility(View.INVISIBLE);
        select6.setVisibility(View.INVISIBLE);
        select7.setVisibility(View.INVISIBLE);
        select8.setVisibility(View.INVISIBLE);

        //调api，获取数据，设置对应的item  TODO
        List<String> list = new ArrayList<>();//存放意向兼职
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
                                        list.add(content);//加入到list中
                                    }
                                    Log.i("意向兼职数据", list.toString());
                                } else {
                                    //没数据，什么都不做
                                }

                                runOnUiThread(() -> {
                                    Toast toast = Toast.makeText(getBaseContext(), "意向兼职获取成功！请稍等片刻~", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                    toast.show();

                                    //设置对应的item
                                    if (!isNull.getString("memo").equals("存在意向兼职")) {
                                        //没有选过意向兼职，什么都不做
                                    } else {
                                        for (String str : list) {
                                            //判断str对应names数组中的哪个
                                            if (str.equals("课程助教")) {
                                                item1.setVisibility(View.INVISIBLE);
                                                select1.setVisibility(View.VISIBLE);
                                            } else if (str.equals("学生助理")) {
                                                item2.setVisibility(View.INVISIBLE);
                                                select2.setVisibility(View.VISIBLE);
                                            } else if (str.equals("军训助理")) {
                                                item3.setVisibility(View.INVISIBLE);
                                                select3.setVisibility(View.VISIBLE);
                                            } else if (str.equals("体测助理")) {
                                                item4.setVisibility(View.INVISIBLE);
                                                select4.setVisibility(View.VISIBLE);
                                            } else if (str.equals("讲解员")) {
                                                item5.setVisibility(View.INVISIBLE);
                                                select5.setVisibility(View.VISIBLE);
                                            } else if (str.equals("公寓宣传员")) {
                                                item6.setVisibility(View.INVISIBLE);
                                                select6.setVisibility(View.VISIBLE);
                                            } else if (str.equals("班助")) {
                                                item7.setVisibility(View.INVISIBLE);
                                                select7.setVisibility(View.VISIBLE);
                                            } else if (str.equals("服务员")) {
                                                item8.setVisibility(View.INVISIBLE);
                                                select8.setVisibility(View.VISIBLE);
                                            }
                                        }
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

        //点击事件
        item1.setOnClickListener(v -> {
            item1.setVisibility(View.INVISIBLE);
            select1.setVisibility(View.VISIBLE);
            intented.add("课程助教");
        });
        select1.setOnClickListener(v -> {
            item1.setVisibility(View.VISIBLE);
            select1.setVisibility(View.INVISIBLE);
            int idx = 0;
            for (String i : intented) {
                if (i.equals("课程助教")) {
                    intented.remove(idx);
                }
                idx++;
            }
        });
        item2.setOnClickListener(v -> {
            item2.setVisibility(View.INVISIBLE);
            select2.setVisibility(View.VISIBLE);
            intented.add("学生助理");
        });
        select2.setOnClickListener(v -> {
            item2.setVisibility(View.VISIBLE);
            select2.setVisibility(View.INVISIBLE);
            int idx = 0;
            for (String i : intented) {
                if (i.equals("学生助理")) {
                    intented.remove(idx);
                }
                idx++;
            }
        });
        item3.setOnClickListener(v -> {
            item3.setVisibility(View.INVISIBLE);
            select3.setVisibility(View.VISIBLE);
            intented.add("军训助理");
        });
        select3.setOnClickListener(v -> {
            item3.setVisibility(View.VISIBLE);
            select3.setVisibility(View.INVISIBLE);
            int idx = 0;
            for (String i : intented) {
                if (i.equals("军训助理")) {
                    intented.remove(idx);
                }
                idx++;
            }
        });
        item4.setOnClickListener(v -> {
            item4.setVisibility(View.INVISIBLE);
            select4.setVisibility(View.VISIBLE);
            intented.add("体测助理");
        });
        select4.setOnClickListener(v -> {
            item4.setVisibility(View.VISIBLE);
            select4.setVisibility(View.INVISIBLE);
            int idx = 0;
            for (String i : intented) {
                if (i.equals("体测助理")) {
                    intented.remove(idx);
                }
                idx++;
            }
        });
        item5.setOnClickListener(v -> {
            item5.setVisibility(View.INVISIBLE);
            select5.setVisibility(View.VISIBLE);
            intented.add("讲解员");
        });
        select5.setOnClickListener(v -> {
            item5.setVisibility(View.VISIBLE);
            select5.setVisibility(View.INVISIBLE);
            int idx = 0;
            for (String i : intented) {
                if (i.equals("讲解员")) {
                    intented.remove(idx);
                }
                idx++;
            }
        });
        item6.setOnClickListener(v -> {
            item6.setVisibility(View.INVISIBLE);
            select6.setVisibility(View.VISIBLE);
            intented.add("公寓宣传员");
        });
        select6.setOnClickListener(v -> {
            item6.setVisibility(View.VISIBLE);
            select6.setVisibility(View.INVISIBLE);
            int idx = 0;
            for (String i : intented) {
                if (i.equals("公寓宣传员")) {
                    intented.remove(idx);
                }
                idx++;
            }
        });
        item7.setOnClickListener(v -> {
            item7.setVisibility(View.INVISIBLE);
            select7.setVisibility(View.VISIBLE);
            intented.add("班助");
        });
        select7.setOnClickListener(v -> {
            item7.setVisibility(View.VISIBLE);
            select7.setVisibility(View.INVISIBLE);
            int idx = 0;
            for (String i : intented) {
                if (i.equals("班助")) {
                    intented.remove(idx);
                }
                idx++;
            }
        });
        item8.setOnClickListener(v -> {
            item8.setVisibility(View.INVISIBLE);
            select8.setVisibility(View.VISIBLE);
            intented.add("服务员");
        });
        select8.setOnClickListener(v -> {
            item8.setVisibility(View.VISIBLE);
            select8.setVisibility(View.INVISIBLE);
            int idx = 0;
            for (String i : intented) {
                if (i.equals("服务员")) {
                    intented.remove(idx);
                }
                idx++;
            }
        });

        button_save.setOnClickListener(v -> {
            Log.i("list", intented.toString());
            String[] intentedArr = intented.toArray(new String[intented.size()]);//List转指定格式的数组
            Log.i("listArr", intentedArr.toString());

            //调api，把数据传给后端，存入DB  TODO
            new Thread(() -> {
                try {
                    OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                    EditIntention editIntention = new EditIntention();
                    editIntention.setTelephone(Constants.telephone);//TODO  当前登录用户
                    editIntention.setIntentions(intentedArr);
                    String json = JSON.toJSONString(editIntention);//dto转string
                    Request request = new Request.Builder()
                            .url("http://114.55.239.213:8087/users/intention/edit")
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
                            Log.i("error", "数据编辑失败");
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            if (response.isSuccessful()) {//调用成功
                                try {
                                    JSONObject jsonObj = JSON.parseObject(response.body().string());
                                    JSONArray data_intention = JSON.parseArray(jsonObj.getString("data"));
                                    Log.i("data_intention实体", data_intention.toString());

                                    for (int i = 0; i < data_intention.size(); i++) {
                                        JSONObject jsonObject = data_intention.getJSONObject(i);
                                        String content = jsonObject.getString("content");
                                        int i_id = jsonObject.getIntValue("i_id");
                                        String stu_id = jsonObject.getString("stu_id");
                                        System.out.println("stu_id:" + stu_id + ";i_id:" + i_id + ";content:" + content);
                                    }

                                    runOnUiThread(() -> {
                                        Toast toast = Toast.makeText(getBaseContext(), "编辑成功！请稍等片刻~", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                        toast.show();

                                        //跳转个人中心
                                        Intent i = new Intent();
                                        i.setClass(context, HomeActivity.class);
                                        i.putExtra("id", 3);
                                        startActivity(i);
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

        back.setOnClickListener(v -> {
            Toast toast = Toast.makeText(context, "数据加载中，请稍等片刻~", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
            toast.show();

            //跳转个人中心
            Intent i = new Intent();
            i.setClass(context, HomeActivity.class);
            i.putExtra("id", 3);
            startActivity(i);
        });
    }
}
