package com.zyq.parttime.position;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.zyq.parttime.HomeActivity;
import com.zyq.parttime.R;
import com.zyq.parttime.form.Position;
import com.zyq.parttime.home.HomeAdapter;
import com.zyq.parttime.home.HomeFragment;

import java.util.List;

public class PositionDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int HEADER_VIEW = 0; //头部

    private Context context;
    private Position data; //数据列表

    public PositionDetailAdapter(Context context, Position data) {
        this.context = context;
        this.data = data;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View view) {
            super(view);
            view.setOnClickListener(v -> {
            });
        }

        //封装数据
        private TextView name, settlement, salary, area, exp, content, requirement, employer_name,
                slogan, unit_name, descriptions, job_nums, unit_area;
        private AppCompatButton btn_signup;

        public TextView getName() {
            return name;
        }

        public void setName(TextView name) {
            this.name = name;
        }

        public TextView getSettlement() {
            return settlement;
        }

        public void setSettlement(TextView settlement) {
            this.settlement = settlement;
        }

        public TextView getSalary() {
            return salary;
        }

        public void setSalary(TextView salary) {
            this.salary = salary;
        }

        public TextView getArea() {
            return area;
        }

        public void setArea(TextView area) {
            this.area = area;
        }

        public TextView getExp() {
            return exp;
        }

        public void setExp(TextView exp) {
            this.exp = exp;
        }

        public TextView getContent() {
            return content;
        }

        public void setContent(TextView content) {
            this.content = content;
        }

        public TextView getRequirement() {
            return requirement;
        }

        public void setRequirement(TextView requirement) {
            this.requirement = requirement;
        }

        public TextView getEmployer_name() {
            return employer_name;
        }

        public void setEmployer_name(TextView employer_name) {
            this.employer_name = employer_name;
        }

        public TextView getSlogan() {
            return slogan;
        }

        public void setSlogan(TextView slogan) {
            this.slogan = slogan;
        }

        public TextView getUnit_name() {
            return unit_name;
        }

        public void setUnit_name(TextView unit_name) {
            this.unit_name = unit_name;
        }

        public TextView getDescriptions() {
            return descriptions;
        }

        public void setDescriptions(TextView descriptions) {
            this.descriptions = descriptions;
        }

        public TextView getJob_nums() {
            return job_nums;
        }

        public void setJob_nums(TextView job_nums) {
            this.job_nums = job_nums;
        }

        public TextView getUnit_area() {
            return unit_area;
        }

        public void setUnit_area(TextView unit_area) {
            this.unit_area = unit_area;
        }

        public AppCompatButton getBtn_signup() {
            return btn_signup;
        }

        public void setBtn_signup(AppCompatButton btn_signup) {
            this.btn_signup = btn_signup;
        }
    }

    //创建头部viewhodler
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER_VIEW) {
//            View headerView = LayoutInflater.from(context).inflate(R.layout.position_detail_item,parent,false);//不能滚动
            View headerView = LayoutInflater.from(context).inflate(R.layout.position_detail_item,null);//recycleview会被压缩
            HeaderViewHolder positionDetailHolder = new HeaderViewHolder(headerView);

            //接下来是控件的引用声明
            positionDetailHolder.name = headerView.findViewById(R.id.name);
            positionDetailHolder.settlement = headerView.findViewById(R.id.settlement);
            positionDetailHolder.salary = headerView.findViewById(R.id.salary);
            positionDetailHolder.area = headerView.findViewById(R.id.area);
            positionDetailHolder.exp = headerView.findViewById(R.id.exp);
            positionDetailHolder.content = headerView.findViewById(R.id.content);
            positionDetailHolder.requirement = headerView.findViewById(R.id.requirement);
            positionDetailHolder.employer_name = headerView.findViewById(R.id.employer_name);
            positionDetailHolder.slogan = headerView.findViewById(R.id.slogan);
            positionDetailHolder.unit_name = headerView.findViewById(R.id.unit_name);
            positionDetailHolder.descriptions = headerView.findViewById(R.id.descriptions);
            positionDetailHolder.job_nums = headerView.findViewById(R.id.job_nums);
            positionDetailHolder.unit_area = headerView.findViewById(R.id.unit_area);
            //...点评数据

            positionDetailHolder.btn_signup = headerView.findViewById(R.id.btn_signup);
            return positionDetailHolder;
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int pos) {
        try {
            if (holder instanceof HeaderViewHolder) {//属于头部
                HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
                Log.i("data", data.toString());//test

                headerViewHolder.name.setText(data.getPosition_name());
                headerViewHolder.settlement.setText(data.getSettlement());
                headerViewHolder.salary.setText(data.getSalary());
                headerViewHolder.area.setText(data.getArea());
                headerViewHolder.exp.setText(data.getExp());
                headerViewHolder.content.setText(data.getContent());
                headerViewHolder.requirement.setText(data.getRequirement());
//            headerViewHolder.employer_name.setText(data.get);
                headerViewHolder.slogan.setText(data.getSlogan());
//            headerViewHolder.unit_name.setText(data.get);
//            headerViewHolder.descriptions.setText(data.get);
                //数量要变颜色

//            headerViewHolder.job_nums.setText("在招职位"+data.get+"个");
//            headerViewHolder.unit_area.setText(data.get);
                //点评数据

                //报名按钮
                headerViewHolder.btn_signup.setOnClickListener(v -> {
                    //跳转到首页
                    Log.i("btn","点击了报名按钮");
                    Intent i=new Intent();
                    i.setClass(this.context, HomeActivity.class);
                    //一定要指定是第几个pager，这里填写1
                    i.putExtra("id",1);
                    context.startActivity(i);
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //重写getItemCount方法，传进来就1个item，数量=1
    @Override
    public int getItemCount() {
        return 1;
    }

    //重写getItemViewType方法
    @Override
    public int getItemViewType(int position) {
        if (position >= 0) { //第一个item就是背景
            return HEADER_VIEW;
        } else { //其余是详细信息的各个字段
            return -1;
        }
    }
}
