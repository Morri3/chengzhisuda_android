package com.zyq.parttime;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.zyq.parttime.home.HomeFragment;
import com.zyq.parttime.signup.SignupFragment;
import com.zyq.parttime.userhome.UserhomeFragment;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    //fragment
    private HomeFragment homeFragment;
    private SignupFragment signupFragment;
    private UserhomeFragment userhomeFragment;
    //文字
    private TextView text_home, text_signup, text_userhome;
    //图片
    private ImageView icon_home, icon_signup, icon_userhome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//取消默认顶部标题栏
        setContentView(R.layout.activity_main);

        //初始化组件
        initViews();

        //监听底部导航栏
        text_home.setOnClickListener(this);
        text_signup.setOnClickListener(this);
        text_userhome.setOnClickListener(this);
        icon_home.setOnClickListener(this);
        icon_signup.setOnClickListener(this);
        icon_userhome.setOnClickListener(this);

        // 初始时显示首页
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();//向Activity state增加/删除fragment
        hideFragments(transaction);//隐藏所有的fragment
        changeColors("home");//图片、文字选中的样式
        if (homeFragment == null) {//fragment为空
            homeFragment = new HomeFragment();//创建一个fragment
            transaction.add(R.id.main_container, homeFragment);//加入transaction
        } else {//fragment不为空
            transaction.show(homeFragment);//显示该fragment
        }
        transaction.commit();//提交
    }

    public void initViews() {
        text_home = (TextView) findViewById(R.id.home);
        text_signup = (TextView) findViewById(R.id.signup);
        text_userhome = (TextView) findViewById(R.id.userhome);
        icon_home = (ImageView) findViewById(R.id.icon_home);
        icon_signup = (ImageView) findViewById(R.id.icon_signup);
        icon_userhome = (ImageView) findViewById(R.id.icon_userhome);
    }

    @Override
    public void onClick(View v) {
        //FragmentTransaction实现向Activity state增加/删除fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //隐藏所有的fragment
        hideFragments(transaction);

        switch (v.getId()) {
            case R.id.home://首页
            case R.id.icon_home:
                reset();//重置文本、图片选中状态
                changeColors("home");//图片、文字选中的样式

                if (homeFragment == null) {//fragment为空
                    homeFragment = new HomeFragment();//创建一个fragment
                    transaction.add(R.id.main_container, homeFragment);//加入transaction
                } else {//fragment不为空
                    transaction.show(homeFragment);//显示该fragment
                }
                break;
            case R.id.signup://我的报名
            case R.id.icon_signup:
                reset();//重置文本、图片选中状态
                changeColors("signup");//图片、文字选中的样式

                if (signupFragment == null) {//fragment为空
                    signupFragment = new SignupFragment();//创建一个fragment
                    transaction.add(R.id.main_container, signupFragment);//加入transaction
                } else {//fragment不为空
                    transaction.show(signupFragment);//显示该fragment
                }
                break;
            case R.id.userhome://个人中心
            case R.id.icon_userhome:
                reset();//重置文本、图片选中状态
//                text_userhome.setSelected(true);//文字选中
//                icon_userhome.setSelected(true);//图片选中
                changeColors("userhome");//图片、文字选中的样式

                if (userhomeFragment == null) {//fragment为空
                    userhomeFragment = new UserhomeFragment();//创建一个fragment
                    transaction.add(R.id.main_container, userhomeFragment);//加入transaction
                } else {//fragment不为空
                    transaction.show(userhomeFragment);//显示该fragment
                }
                break;
        }
        transaction.commit();//提交
    }

    //重置文本、图片的选中状态
    public void reset() {
        text_home.setSelected(false);
        text_signup.setSelected(false);
        text_userhome.setSelected(false);
        icon_home.setSelected(false);
        icon_signup.setSelected(false);
        icon_userhome.setSelected(false);
    }

    //隐藏Fragment
    public void hideFragments(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (signupFragment != null) {
            transaction.hide(signupFragment);
        }
        if (userhomeFragment != null) {
            transaction.hide(userhomeFragment);
        }
    }

    //选中的图标、文字的样式
    private void changeColors(String item) {
        //默认样式
        icon_home.setImageResource(R.drawable.icon_home);
        icon_signup.setImageResource(R.drawable.icon_signup);
        icon_userhome.setImageResource(R.drawable.icon_userhome);
        text_home.setTextColor(getResources().getColor(R.color.text_black_color));
        text_signup.setTextColor(getResources().getColor(R.color.text_black_color));
        text_userhome.setTextColor(getResources().getColor(R.color.text_black_color));

        //选中时的样式
        switch (item) {
            case "home"://点击首页
                icon_home.setImageResource(R.drawable.icon_home_clicked);
                text_home.setTextColor(getResources().getColor(R.color.main_purple));
                break;
            case "signup"://点击我的报名
                icon_signup.setImageResource(R.drawable.icon_signup_clicked);
                text_signup.setTextColor(getResources().getColor(R.color.main_purple));
                break;
            case "userhome"://点击个人中心
                icon_userhome.setImageResource(R.drawable.icon_userhome_clicked);
                text_userhome.setTextColor(getResources().getColor(R.color.main_purple));
                break;
            default:
                break;
        }
    }

    //重写onResume方法，activity跳转到响应的fragment
    @Override
    protected void onResume() {
        //FragmentTransaction实现向Activity state增加/删除fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        int id = getIntent().getIntExtra("id", 0);
        if (id == 1) {
            changeColors("home");//图片、文字选中的样式
            transaction.show(homeFragment);//显示该fragment
        }
        if (id == 2) {
            changeColors("signup");//图片、文字选中的样式
            transaction.show(signupFragment);//显示该fragment
        }
        if (id == 3) {
            changeColors("userhome");//图片、文字选中的样式
            transaction.show(userhomeFragment);//显示该fragment
        }
        transaction.commit();

        super.onResume();
    }
}
