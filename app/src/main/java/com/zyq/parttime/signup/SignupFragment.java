package com.zyq.parttime.signup;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zyq.parttime.R;
import com.zyq.parttime.form.Position;
import com.zyq.parttime.form.Signup;
import com.zyq.parttime.home.HomeAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        status1.setTextColor(context.getResources().getColor(R.color.main_purple));
        status2.setTextColor(context.getResources().getColor(R.color.text_black_color));
        status3.setTextColor(context.getResources().getColor(R.color.text_black_color));
        status4.setTextColor(context.getResources().getColor(R.color.text_black_color));
        line1.setVisibility(View.VISIBLE);
        line2.setVisibility(View.INVISIBLE);
        line3.setVisibility(View.INVISIBLE);
        line4.setVisibility(View.INVISIBLE);

        //点击事件
        status1.setOnClickListener(v -> {
            Log.i("状态", "1");
            status1.setTextColor(context.getResources().getColor(R.color.main_purple));
            status2.setTextColor(context.getResources().getColor(R.color.text_black_color));
            status3.setTextColor(context.getResources().getColor(R.color.text_black_color));
            status4.setTextColor(context.getResources().getColor(R.color.text_black_color));
            line1.setVisibility(View.VISIBLE);
            line2.setVisibility(View.INVISIBLE);
            line3.setVisibility(View.INVISIBLE);
            line4.setVisibility(View.INVISIBLE);

            //调用“全部”状态的数据  TODO
            s = 1;
        });
        line1.setOnClickListener(v -> {
            Log.i("状态", "1");
            status1.setTextColor(context.getResources().getColor(R.color.main_purple));
            status2.setTextColor(context.getResources().getColor(R.color.text_black_color));
            status3.setTextColor(context.getResources().getColor(R.color.text_black_color));
            status4.setTextColor(context.getResources().getColor(R.color.text_black_color));
            line1.setVisibility(View.VISIBLE);
            line2.setVisibility(View.INVISIBLE);
            line3.setVisibility(View.INVISIBLE);
            line4.setVisibility(View.INVISIBLE);

            //调用“全部”状态的数据  TODO
            s = 1;
        });
        status2.setOnClickListener(v -> {
            Log.i("状态", "2");
            status2.setTextColor(context.getResources().getColor(R.color.main_purple));
            status1.setTextColor(context.getResources().getColor(R.color.text_black_color));
            status3.setTextColor(context.getResources().getColor(R.color.text_black_color));
            status4.setTextColor(context.getResources().getColor(R.color.text_black_color));
            line1.setVisibility(View.INVISIBLE);
            line2.setVisibility(View.VISIBLE);
            line3.setVisibility(View.INVISIBLE);
            line4.setVisibility(View.INVISIBLE);

            //调用“已报名”状态的数据  TODO
            s = 2;
        });
        line2.setOnClickListener(v -> {
            Log.i("状态", "2");
            status2.setTextColor(context.getResources().getColor(R.color.main_purple));
            status1.setTextColor(context.getResources().getColor(R.color.text_black_color));
            status3.setTextColor(context.getResources().getColor(R.color.text_black_color));
            status4.setTextColor(context.getResources().getColor(R.color.text_black_color));
            line1.setVisibility(View.INVISIBLE);
            line2.setVisibility(View.VISIBLE);
            line3.setVisibility(View.INVISIBLE);
            line4.setVisibility(View.INVISIBLE);

            //调用“已报名”状态的数据  TODO
            s = 2;
        });
        status3.setOnClickListener(v -> {
            Log.i("状态", "3");
            status3.setTextColor(context.getResources().getColor(R.color.main_purple));
            status1.setTextColor(context.getResources().getColor(R.color.text_black_color));
            status2.setTextColor(context.getResources().getColor(R.color.text_black_color));
            status4.setTextColor(context.getResources().getColor(R.color.text_black_color));
            line1.setVisibility(View.INVISIBLE);
            line2.setVisibility(View.INVISIBLE);
            line3.setVisibility(View.VISIBLE);
            line4.setVisibility(View.INVISIBLE);

            //调用“已录取”状态的数据  TODO
            s = 3;
        });
        line3.setOnClickListener(v -> {
            Log.i("状态", "3");
            status3.setTextColor(context.getResources().getColor(R.color.main_purple));
            status1.setTextColor(context.getResources().getColor(R.color.text_black_color));
            status2.setTextColor(context.getResources().getColor(R.color.text_black_color));
            status4.setTextColor(context.getResources().getColor(R.color.text_black_color));
            line1.setVisibility(View.INVISIBLE);
            line2.setVisibility(View.INVISIBLE);
            line3.setVisibility(View.VISIBLE);
            line4.setVisibility(View.INVISIBLE);

            //调用“已录取”状态的数据  TODO
            s = 3;
        });
        status4.setOnClickListener(v -> {
            Log.i("状态", "4");
            status4.setTextColor(context.getResources().getColor(R.color.main_purple));
            status1.setTextColor(context.getResources().getColor(R.color.text_black_color));
            status2.setTextColor(context.getResources().getColor(R.color.text_black_color));
            status3.setTextColor(context.getResources().getColor(R.color.text_black_color));
            line1.setVisibility(View.INVISIBLE);
            line2.setVisibility(View.INVISIBLE);
            line3.setVisibility(View.INVISIBLE);
            line4.setVisibility(View.VISIBLE);

            //调用“已结束”状态的数据  TODO
            s = 4;
        });
        line4.setOnClickListener(v -> {
            Log.i("状态", "4");
            status4.setTextColor(context.getResources().getColor(R.color.main_purple));
            status1.setTextColor(context.getResources().getColor(R.color.text_black_color));
            status2.setTextColor(context.getResources().getColor(R.color.text_black_color));
            status3.setTextColor(context.getResources().getColor(R.color.text_black_color));
            line1.setVisibility(View.INVISIBLE);
            line2.setVisibility(View.INVISIBLE);
            line3.setVisibility(View.INVISIBLE);
            line4.setVisibility(View.VISIBLE);

            //调用“已结束”状态的数据  TODO
            s = 4;
        });

        initData();//数据获取

        Log.i("报名数据", list.toString());

        //适配器的定义与设置
        //配置布局管理器、分割线、适配器
        rv = view.findViewById(R.id.rv_signup);
        //第一步：设置布局管理器
        rv.setLayoutManager(new LinearLayoutManager(context));
        //第二步：设置适配器
        signupAdapter = new SignupAdapter(context, list);
        rv.setAdapter(signupAdapter);

        return view;
    }

    public void initData() {
        //调用后端接口，获取兼职信息列表，赋值给list,分别对应地调用四个接口
        if (s == 1) {

        } else if (s == 2) {

        } else if (s == 3) {

        } else if (s == 4) {

        }

        //TODO 现在是假数据
        list.clear();
        for (int i = 1; i <= 4; i++) {
            Signup signup = new Signup();
            signup.setS_id(i);
            signup.setStu_id("morri" + i);
            signup.setP_id(i);
            signup.setSignup_status("已报名");
            signup.setCreate_time(new Date());
            signup.setUpdate_time(new Date());
            list.add(signup);
        }
    }
}
