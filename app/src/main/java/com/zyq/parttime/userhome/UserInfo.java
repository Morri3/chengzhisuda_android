package com.zyq.parttime.userhome;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.zyq.parttime.HomeActivity;
import com.zyq.parttime.R;
import com.zyq.parttime.position.PositionDetail;
import com.zyq.parttime.util.DateData;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UserInfo extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//取消默认顶部标题栏
        setContentView(R.layout.activity_userinfo);

        TextView username = findViewById(R.id.username);
        TextView gender = findViewById(R.id.gender);
        TextView emails = findViewById(R.id.emails);
        TextView age = findViewById(R.id.age);
        TextView telephone = findViewById(R.id.telephone);
        TextView school_name = findViewById(R.id.school_name);
        TextView sno = findViewById(R.id.sno);
        TextView grade = findViewById(R.id.grade);
        EditText e1 = findViewById(R.id.e1);
        EditText e2 = findViewById(R.id.e2);
        EditText e3 = findViewById(R.id.e3);
        Spinner yearSpinner = findViewById(R.id.year);
        TextView year_text = findViewById(R.id.year_text);
        Spinner monthSpinner = findViewById(R.id.month);
        TextView month_text = findViewById(R.id.month_text);
        Spinner startYearSpinner = findViewById(R.id.start_year);
        TextView start_year_text = findViewById(R.id.start_year_text);
        Spinner startMonthSpinner = findViewById(R.id.start_month);
        TextView start_month_text = findViewById(R.id.start_month_text);
        Spinner endYearSpinner = findViewById(R.id.end_year);
        TextView end_year_text = findViewById(R.id.end_year_text);
        Spinner endMonthSpinner = findViewById(R.id.end_month);
        TextView end_month_text = findViewById(R.id.end_month_text);
        EditText e5 = findViewById(R.id.e5);
        EditText e6 = findViewById(R.id.e6);
        EditText e7 = findViewById(R.id.e7);
        ImageView modify = findViewById(R.id.modify);
        TextView modify_text = findViewById(R.id.modify_text);
        ImageView btn_save = findViewById(R.id.btn_save);
        ImageView back = findViewById(R.id.back);

        e1.setVisibility(View.INVISIBLE);
        e2.setVisibility(View.INVISIBLE);
        e3.setVisibility(View.INVISIBLE);
        yearSpinner.setVisibility(View.INVISIBLE);
        year_text.setVisibility(View.INVISIBLE);
        monthSpinner.setVisibility(View.INVISIBLE);
        month_text.setVisibility(View.INVISIBLE);
        startYearSpinner.setVisibility(View.INVISIBLE);
        start_year_text.setVisibility(View.INVISIBLE);
        startMonthSpinner.setVisibility(View.INVISIBLE);
        start_month_text.setVisibility(View.INVISIBLE);
        e5.setVisibility(View.INVISIBLE);
        e6.setVisibility(View.INVISIBLE);
        e7.setVisibility(View.INVISIBLE);
        endYearSpinner.setVisibility(View.INVISIBLE);
        end_year_text.setVisibility(View.INVISIBLE);
        endMonthSpinner.setVisibility(View.INVISIBLE);
        end_month_text.setVisibility(View.INVISIBLE);
        btn_save.setVisibility(View.INVISIBLE);

        //调api获取个人信息，set到textview中


        //修改
        modify.setOnClickListener(v -> {
            username.setVisibility(View.INVISIBLE);
            gender.setVisibility(View.INVISIBLE);
            emails.setVisibility(View.INVISIBLE);
            age.setVisibility(View.INVISIBLE);
            telephone.setVisibility(View.INVISIBLE);
            school_name.setVisibility(View.INVISIBLE);
            sno.setVisibility(View.INVISIBLE);
            grade.setVisibility(View.INVISIBLE);
            e1.setVisibility(View.VISIBLE);
            e2.setVisibility(View.VISIBLE);
            e3.setVisibility(View.VISIBLE);
            yearSpinner.setVisibility(View.VISIBLE);
            monthSpinner.setVisibility(View.VISIBLE);
            startYearSpinner.setVisibility(View.VISIBLE);
            startMonthSpinner.setVisibility(View.VISIBLE);
            year_text.setVisibility(View.VISIBLE);
            month_text.setVisibility(View.VISIBLE);
            start_year_text.setVisibility(View.VISIBLE);
            start_month_text.setVisibility(View.VISIBLE);
            end_year_text.setVisibility(View.VISIBLE);
            end_month_text.setVisibility(View.VISIBLE);
            e5.setVisibility(View.VISIBLE);
            e6.setVisibility(View.VISIBLE);
            e7.setVisibility(View.VISIBLE);
            endYearSpinner.setVisibility(View.VISIBLE);
            endMonthSpinner.setVisibility(View.VISIBLE);
            btn_save.setVisibility(View.VISIBLE);
            modify.setVisibility(View.INVISIBLE);
            modify_text.setVisibility(View.INVISIBLE);

            //创建DateData类，并配置默认值
            DateData dateData = new DateData(1900, 1, 1900, 1, 1900, 1);

            //构造年份列表
            List<Integer> year = new ArrayList<>();//年份列表
            for (int i = 1900; i <= 2023; i++) {
                year.add(i);
            }
            //适配器1
            ArrayAdapter<Integer> yearadapter = new ArrayAdapter<>(this,
                    R.layout.date_item, year);//把年份的列表添加到适配器中，使用自定义的item样式
            yearadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);//设置下拉选项内容
            yearSpinner.setAdapter(yearadapter);//设置适配器
            //监听下拉框
            yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    dateData.setBirthYear(yearadapter.getItem(position));//用DateData对象来处理选中的年份
                    runOnUiThread(() -> {
                        yearSpinner.setSelection(position);//设置当前选中的item
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            //构造月份列表
            List<Integer> month = new ArrayList<>();//月份列表
            for (int i = 1; i <= 12; i++) {
                month.add(i);
            }
            //适配器2
            ArrayAdapter<Integer> monthadapter = new ArrayAdapter<>(this,
                    R.layout.date_item, month);//把月份的列表添加到适配器中，使用自定义的item样式
            monthadapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);//设置下拉选项内容
            runOnUiThread(() -> {
                monthSpinner.setAdapter(monthadapter);//设置适配器
            });
            //监听下拉框
            monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    dateData.setBirthMonth(monthadapter.getItem(position));
                    runOnUiThread(() -> {
                        monthSpinner.setSelection(position);//设置当前选中的item
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            //适配器3
            ArrayAdapter<Integer> startYearAdapter = new ArrayAdapter<>(this,
                    R.layout.date_item, year);//把年份的列表添加到适配器中，使用自定义的item样式
            startYearAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);//设置下拉选项内容
            startYearSpinner.setAdapter(startYearAdapter);//设置适配器
            //监听下拉框
            startYearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    dateData.setStartYear(startYearAdapter.getItem(position));//用DateData对象来处理选中的年份
                    runOnUiThread(() -> {
                        startYearSpinner.setSelection(position);//设置当前选中的item
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            //适配器4
            ArrayAdapter<Integer> startMonthAdapter = new ArrayAdapter<>(this,
                    R.layout.date_item, month);//把月份的列表添加到适配器中，使用自定义的item样式
            startMonthAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);//设置下拉选项内容
            runOnUiThread(() -> {
                startMonthSpinner.setAdapter(startMonthAdapter);//设置适配器
            });
            //监听下拉框
            startMonthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    dateData.setStartMonth(monthadapter.getItem(position));
                    runOnUiThread(() -> {
                        monthSpinner.setSelection(position);//设置当前选中的item
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            //适配器5
            ArrayAdapter<Integer> endYearAdapter = new ArrayAdapter<>(this,
                    R.layout.date_item, year);//把年份的列表添加到适配器中，使用自定义的item样式
            endYearAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);//设置下拉选项内容
            endYearSpinner.setAdapter(endYearAdapter);//设置适配器
            //监听下拉框
            endYearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    dateData.setEndYear(endYearAdapter.getItem(position));//用DateData对象来处理选中的年份
                    runOnUiThread(() -> {
                        endYearSpinner.setSelection(position);//设置当前选中的item
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            //适配器6
            ArrayAdapter<Integer> endMonthAdapter = new ArrayAdapter<>(this,
                    R.layout.date_item, month);//把月份的列表添加到适配器中，使用自定义的item样式
            endMonthAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);//设置下拉选项内容
            runOnUiThread(() -> {
                endMonthSpinner.setAdapter(endMonthAdapter);//设置适配器
            });
            //监听下拉框
            endMonthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    dateData.setEndMonth(endMonthAdapter.getItem(position));
                    runOnUiThread(() -> {
                        endMonthSpinner.setSelection(position);//设置当前选中的item
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            //各个edittext的输入


            //保存
            btn_save.setOnClickListener(v1 -> {
                String t1 = e1.getText().toString();
                String t2 = e2.getText().toString();
                String t3 = e3.getText().toString();
                String t5 = e5.getText().toString();
                String t6 = e6.getText().toString();
                String t7 = e7.getText().toString();
                int y1 = dateData.getBirthYear();
                int m1 = dateData.getBirthMonth();
                int y2 = dateData.getStartYear();
                int m2 = dateData.getStartMonth();
                int y3 = dateData.getEndYear();
                int m3 = dateData.getEndMonth();

                int newAge = 0;
                String start, end;
                Date birth, enter = new Date(), out = new Date();
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                    //出生日期
                    if (m1 >= 1 && m1 <= 9) {
                        birth = sdf.parse(y1 + "-0" + m1);
                    } else {
                        birth = sdf.parse(y1 + "-" + m1);
                    }
                    //当前
                    Date now = new Date();
                    Calendar c1 = Calendar.getInstance();
                    Calendar c2 = Calendar.getInstance();
                    c1.setTime(birth);
                    c2.setTime(now);
                    int tmp1 = c2.get(Calendar.DATE) - c1.get(Calendar.DATE);
                    int tmp2 = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
                    int tmp3 = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
                    if (tmp2 > 0) {//月份更大
                        tmp2 = 1;//最后要+1
                    } else if (tmp2 == 0) {//月份相同
                        tmp2 = tmp1 <= 0 ? 0 : 1;//判断日期，前面日期更小，最后月份要+1
                    } else {//月份更小，不用+
                        tmp2 = 0;
                    }
                    newAge = tmp3 + tmp2;
                    Log.i("age", newAge + "");

                    //入学时间，毕业时间
                    if (m2 >= 1 && m2 <= 9) {
                        start = y2 + "-0" + m2;
//                        enter = sdf.parse(y2 + "-0" + m2);
                    } else {
                        start = y2 + "-" + m2;
//                        enter = sdf.parse(y2 + "-" + m2);
                    }
                    if (m3 >= 1 && m3 <= 9) {
                        end = y3 + "-0" + m3;
//                        out = sdf.parse(y3 + "-0" + m3);
                    } else {
                        end = y3 + "-" + m3;
//                        out = sdf.parse(y3 + "-" + m3);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.i("age", newAge + "");
                Log.i("enter", enter.toString());
                Log.i("out", out.toString());

                //调api

                //切回原来的
                username.setVisibility(View.VISIBLE);
                gender.setVisibility(View.VISIBLE);
                emails.setVisibility(View.VISIBLE);
                age.setVisibility(View.VISIBLE);
                telephone.setVisibility(View.VISIBLE);
                school_name.setVisibility(View.VISIBLE);
                sno.setVisibility(View.VISIBLE);
                grade.setVisibility(View.VISIBLE);
                e1.setVisibility(View.INVISIBLE);
                e2.setVisibility(View.INVISIBLE);
                e3.setVisibility(View.INVISIBLE);
                yearSpinner.setVisibility(View.INVISIBLE);
                monthSpinner.setVisibility(View.INVISIBLE);
                startYearSpinner.setVisibility(View.INVISIBLE);
                startMonthSpinner.setVisibility(View.INVISIBLE);
                year_text.setVisibility(View.INVISIBLE);
                month_text.setVisibility(View.INVISIBLE);
                start_year_text.setVisibility(View.INVISIBLE);
                start_month_text.setVisibility(View.INVISIBLE);
                end_year_text.setVisibility(View.INVISIBLE);
                end_month_text.setVisibility(View.INVISIBLE);
                e5.setVisibility(View.INVISIBLE);
                e6.setVisibility(View.INVISIBLE);
                e7.setVisibility(View.INVISIBLE);
                endYearSpinner.setVisibility(View.INVISIBLE);
                endMonthSpinner.setVisibility(View.INVISIBLE);
                btn_save.setVisibility(View.INVISIBLE);
                modify.setVisibility(View.VISIBLE);
                modify_text.setVisibility(View.VISIBLE);
            });
        });

        //返回
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            Log.i("back", "返回到上一页");
            //跳转到首页
            Intent i = new Intent();
            i.setClass(UserInfo.this, HomeActivity.class);
            //一定要指定是第几个pager，这里填写3
            i.putExtra("id", 3);
            startActivity(i);
        });
    }
}
