package com.zyq.parttime.signup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintSet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.zyq.parttime.HomeActivity;
import com.zyq.parttime.R;
import com.zyq.parttime.form.Position;
import com.zyq.parttime.sp.MarkPost;
import com.zyq.parttime.util.Utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
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

public class MarkActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//取消默认顶部标题栏
        setContentView(R.layout.activity_mark);

        //定义组件
        RatingBar r2_1 = findViewById(R.id.rating2_1);
        RatingBar r2_2 = findViewById(R.id.rating2_2);
        RatingBar r3_1 = findViewById(R.id.rating3_1);
        RatingBar r3_2 = findViewById(R.id.rating3_2);
        RatingBar r4_1 = findViewById(R.id.rating4_1);
        RatingBar r4_2 = findViewById(R.id.rating4_2);
        RatingBar r4_3 = findViewById(R.id.rating4_3);
        AppCompatButton btn_mark = findViewById(R.id.btn_mark);
        TextView num2_1 = findViewById(R.id.num2_1);
        TextView num2_2 = findViewById(R.id.num2_2);
        TextView num3_1 = findViewById(R.id.num3_1);
        TextView num3_2 = findViewById(R.id.num3_2);
        TextView num4_1 = findViewById(R.id.num4_1);
        TextView num4_2 = findViewById(R.id.num4_2);
        TextView num4_3 = findViewById(R.id.num4_3);

        //获取传来的数据
        Intent editpro = getIntent();
        int s_id = editpro.getIntExtra("pos", -1);

        //定义事件
        float point[] = new float[8];
        runOnUiThread(() -> {
            r2_1.setOnRatingBarChangeListener((ratingBar, v, b1) -> {

                point[1] = 2 * ratingBar.getRating();
                Log.i("专业契合度", point[1] + "分");
                num2_1.setText(point[1] + "");
            });
            r2_2.setOnRatingBarChangeListener((ratingBar, v, b1) -> {
                point[2] = 2 * ratingBar.getRating();
                Log.i("薪资水平", point[2] + "分");
                num2_2.setText("" + point[2]);
            });
            r3_1.setOnRatingBarChangeListener((ratingBar, v, b1) -> {
                point[3] = 2 * ratingBar.getRating();
                Log.i("工作环境", point[3] + "分");
                num3_1.setText("" + point[3]);

            });
            r3_2.setOnRatingBarChangeListener((ratingBar, v, b1) -> {
                point[4] = 2 * ratingBar.getRating();
                Log.i("闲时待遇", point[4] + "分");
                num3_2.setText("" + point[4]);

            });
            r4_1.setOnRatingBarChangeListener((ratingBar, v, b1) -> {
                point[5] = 2 * ratingBar.getRating();
                Log.i("岗前培训", point[5] + "分");
                num4_1.setText("" + point[5]);

            });
            r4_2.setOnRatingBarChangeListener((ratingBar, v, b1) -> {
                point[6] = 2 * ratingBar.getRating();
                Log.i("专业技能满意度", point[6] + "分");
                num4_2.setText("" + point[6]);

            });
            r4_3.setOnRatingBarChangeListener((ratingBar, v, b1) -> {
                point[7] = 2 * ratingBar.getRating();
                Log.i("总体收获满意度", point[7] + "分");
                num4_3.setText("" + point[7]);

            });
        });

        //确定按钮，将点击的分数存到DB中
        btn_mark.setOnClickListener(v -> {
            //调api，将数组存到DB中
            Log.i("评分", point.toString());
            new Thread(() -> {
                try {
                    OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                    MarkPost post = new MarkPost();
                    post.setPf((int) point[1]);
                    post.setPl((int) point[2]);
                    post.setWe((int) point[3]);
                    post.setLt((int) point[4]);
                    post.setPt((int) point[5]);
                    post.setOds((int) point[6]);
                    post.setDsps((int) point[7]);
                    post.setS_id(s_id);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date now = sdf.parse(sdf.format(new Date()));
                    post.setCreate_time(now);
                    String json = JSON.toJSONString(post);//dto转json
                    Request request = new Request.Builder()
                            .url("http://114.55.239.213:8082/mark/stu/post")
                            .post(RequestBody.create(MediaType.parse("application/json"), json))
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
                                    JSONObject data_mark_post = JSON.parseObject(jsonObj.getString("data"));
                                    Log.i("data_mark_post实体", data_mark_post.toString());

                                    runOnUiThread(() -> {
                                        Toast toast = Toast.makeText(getBaseContext(), "评分成功！请耐心等待~", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                        toast.show();

                                        //跳转到我的报名页
                                        Intent i = new Intent();
                                        i.setClass(MarkActivity.this, HomeActivity.class);
                                        //一定要指定是第几个pager，这里填写2
                                        i.putExtra("id", 2);
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

    }
}
