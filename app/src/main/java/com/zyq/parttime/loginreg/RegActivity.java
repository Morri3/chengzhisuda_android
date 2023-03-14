package com.zyq.parttime.loginreg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.zyq.parttime.R;
import com.zyq.parttime.util.DateData;

import java.util.ArrayList;
import java.util.List;

public class RegActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//取消默认标题栏
        setContentView(R.layout.activity_reg);

        ImageView back = findViewById(R.id.back);
        AppCompatButton reg = findViewById(R.id.btn_reg);
        Spinner yearSpinner = findViewById(R.id.year);
        Spinner monthSpinner = findViewById(R.id.month);
        Spinner startYearSpinner = findViewById(R.id.start_year);
        Spinner startMonthSpinner = findViewById(R.id.start_month);
        Spinner endYearSpinner = findViewById(R.id.end_year);
        Spinner endMonthSpinner = findViewById(R.id.end_month);

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

        //返回按钮
        back.setOnClickListener(v -> {
            //跳转到登录页
            Intent intent = new Intent(RegActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        //注册按钮
        reg.setOnClickListener(v -> {
            //获取表单数据


            //调用接口存到DB中


            //跳转到登录页
            Intent intent = new Intent(RegActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
