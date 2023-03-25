package com.zyq.parttime.userhome.intented;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.zyq.parttime.HomeActivity;
import com.zyq.parttime.R;
import com.zyq.parttime.home.HomeFragment;
import com.zyq.parttime.userhome.resume.ResumesManage;

import java.util.ArrayList;
import java.util.List;

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

        //点击事件
        item1.setOnClickListener(v -> {
            item1.setVisibility(View.INVISIBLE);
            select1.setVisibility(View.VISIBLE);
            intented.add("课程助教");
        });
        select1.setOnClickListener(v -> {
            item1.setVisibility(View.VISIBLE);
            select1.setVisibility(View.INVISIBLE);
            for (String i : intented) {
                if (i.equals("课程助教")) {
                    intented.remove(i);
                }
            }
        });
        item2.setOnClickListener(v -> {
            item2.setVisibility(View.INVISIBLE);
            select2.setVisibility(View.VISIBLE);
            intented.add("学生助教");
        });
        select2.setOnClickListener(v -> {
            item2.setVisibility(View.VISIBLE);
            select2.setVisibility(View.INVISIBLE);
            for (String i : intented) {
                if (i.equals("学生助教")) {
                    intented.remove(i);
                }
            }
        });
        item3.setOnClickListener(v -> {
            item3.setVisibility(View.INVISIBLE);
            select3.setVisibility(View.VISIBLE);
            intented.add("军训助教");
        });
        select3.setOnClickListener(v -> {
            item3.setVisibility(View.VISIBLE);
            select3.setVisibility(View.INVISIBLE);
            for (String i : intented) {
                if (i.equals("军训助教")) {
                    intented.remove(i);
                }
            }
        });
        item4.setOnClickListener(v -> {
            item4.setVisibility(View.INVISIBLE);
            select4.setVisibility(View.VISIBLE);
            intented.add("体测助教");
        });
        select4.setOnClickListener(v -> {
            item4.setVisibility(View.VISIBLE);
            select4.setVisibility(View.INVISIBLE);
            for (String i : intented) {
                if (i.equals("体测助教")) {
                    intented.remove(i);
                }
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
            for (String i : intented) {
                if (i.equals("讲解员")) {
                    intented.remove(i);
                }
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
            for (String i : intented) {
                if (i.equals("公寓宣传员")) {
                    intented.remove(i);
                }
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
            for (String i : intented) {
                if (i.equals("班助")) {
                    intented.remove(i);
                }
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
            for (String i : intented) {
                if (i.equals("服务员")) {
                    intented.remove(i);
                }
            }
        });

        button_save.setOnClickListener(v -> {
            Log.i("list", intented.toString());
            //调api，把数据传给后端，存入DB


            //跳转个人中心
            Intent i = new Intent();
            i.setClass(context, HomeActivity.class);
            i.putExtra("id", 3);
            startActivity(i);
        });

        back.setOnClickListener(v -> {
            //跳转个人中心
            Intent i = new Intent();
            i.setClass(context, HomeActivity.class);
            i.putExtra("id", 3);
            startActivity(i);
        });
    }
}
