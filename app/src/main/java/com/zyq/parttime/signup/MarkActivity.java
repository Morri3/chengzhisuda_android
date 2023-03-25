package com.zyq.parttime.signup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.zyq.parttime.HomeActivity;
import com.zyq.parttime.R;

public class MarkActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//取消默认顶部标题栏
        setContentView(R.layout.activity_mark);

        //定义组件
        RatingBar r1 = findViewById(R.id.rating1);
        RatingBar r2_1 = findViewById(R.id.rating2_1);
        RatingBar r2_2 = findViewById(R.id.rating2_2);
        RatingBar r3_1 = findViewById(R.id.rating3_1);
        RatingBar r3_2 = findViewById(R.id.rating3_2);
        RatingBar r4_1 = findViewById(R.id.rating4_1);
        RatingBar r4_2 = findViewById(R.id.rating4_2);
        RatingBar r4_3 = findViewById(R.id.rating4_3);
        AppCompatButton btn_mark = findViewById(R.id.btn_mark);

        //定义事件
        float point[] = new float[8];
        r1.setOnRatingBarChangeListener((ratingBar, v, b1) -> {
            point[0] = 2 * ratingBar.getRating();
            Log.i("总体评分", point[0] + "分");
            //
        });
        r2_1.setOnRatingBarChangeListener((ratingBar, v, b1) -> {
            point[1] = 2 * ratingBar.getRating();
            Log.i("专业契合度", point[1] + "分");
        });
        r2_2.setOnRatingBarChangeListener((ratingBar, v, b1) -> {
            point[2] = 2 * ratingBar.getRating();
            Log.i("薪资水平", point[2] + "分");
        });
        r3_1.setOnRatingBarChangeListener((ratingBar, v, b1) -> {
            point[3] = 2 * ratingBar.getRating();
            Log.i("工作环境", point[3] + "分");
        });
        r3_2.setOnRatingBarChangeListener((ratingBar, v, b1) -> {
            point[4] = 2 * ratingBar.getRating();
            Log.i("闲时待遇", point[4] + "分");
        });
        r4_1.setOnRatingBarChangeListener((ratingBar, v, b1) -> {
            point[5] = 2 * ratingBar.getRating();
            Log.i("岗前培训", point[5] + "分");
        });
        r4_2.setOnRatingBarChangeListener((ratingBar, v, b1) -> {
            point[6] = 2 * ratingBar.getRating();
            Log.i("专业技能满意度", point[6] + "分");
        });
        r4_3.setOnRatingBarChangeListener((ratingBar, v, b1) -> {
            point[7] = 2 * ratingBar.getRating();
            Log.i("总体收获满意度", point[7] + "分");
        });

        //确定按钮，将点击的分数存到DB中
        btn_mark.setOnClickListener(v -> {
            //将数组存到DB中
            Log.i("评分", point.toString());
            //TODO

            //跳转到我的报名页
            Intent i = new Intent();
            i.setClass(MarkActivity.this, HomeActivity.class);
            //一定要指定是第几个pager，这里填写2
            i.putExtra("id", 2);
            startActivity(i);
        });

    }
}
