package com.zyq.parttime.position;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.zyq.parttime.HomeActivity;
import com.zyq.parttime.R;
import com.zyq.parttime.form.Position;
import com.zyq.parttime.sp.EmpInfo;
import com.zyq.parttime.sp.MarkInfo;
import com.zyq.parttime.sp.SignupDto;
import com.zyq.parttime.util.Constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PositionDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int HEADER_VIEW = 0; //头部

    private Context context;
    private Position data; //数据列表
    private EmpInfo empInfo;
    private MarkInfo markInfo;
    private int flag = 0;//判断是否可以报名

    //    private static Activity findActivity(Context cont) {
//        if (cont == null)
//            return null;
//        else if (cont instanceof Activity)
//            return (Activity) cont;
//        else if (cont instanceof ContextWrapper)
//            return findActivity(((ContextWrapper) cont).getBaseContext());
//        return null;
//    }
    public static Activity getActivityFromView(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    public PositionDetailAdapter(Context context, Position data, EmpInfo empInfo, MarkInfo markInfo) {
        this.context = context;
        this.data = data;
        this.markInfo = markInfo;
        this.empInfo = empInfo;
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
        private TextView num1, num2, num3, num4, num5, num6, num7, num8;
        private AppCompatButton btn_signup;

        public TextView getNum1() {
            return num1;
        }

        public void setNum1(TextView num1) {
            this.num1 = num1;
        }

        public TextView getNum2() {
            return num2;
        }

        public void setNum2(TextView num2) {
            this.num2 = num2;
        }

        public TextView getNum3() {
            return num3;
        }

        public void setNum3(TextView num3) {
            this.num3 = num3;
        }

        public TextView getNum4() {
            return num4;
        }

        public void setNum4(TextView num4) {
            this.num4 = num4;
        }

        public TextView getNum5() {
            return num5;
        }

        public void setNum5(TextView num5) {
            this.num5 = num5;
        }

        public TextView getNum6() {
            return num6;
        }

        public void setNum6(TextView num6) {
            this.num6 = num6;
        }

        public TextView getNum7() {
            return num7;
        }

        public void setNum7(TextView num7) {
            this.num7 = num7;
        }

        public TextView getNum8() {
            return num8;
        }

        public void setNum8(TextView num8) {
            this.num8 = num8;
        }

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
            View headerView = LayoutInflater.from(context).inflate(R.layout.position_detail_item, null);//recycleview会被压缩
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

            //点评数据
            positionDetailHolder.num1 = headerView.findViewById(R.id.num1);
            positionDetailHolder.num2 = headerView.findViewById(R.id.num2);
            positionDetailHolder.num3 = headerView.findViewById(R.id.num3);
            positionDetailHolder.num4 = headerView.findViewById(R.id.num4);
            positionDetailHolder.num5 = headerView.findViewById(R.id.num5);
            positionDetailHolder.num6 = headerView.findViewById(R.id.num6);
            positionDetailHolder.num7 = headerView.findViewById(R.id.num7);
            positionDetailHolder.num8 = headerView.findViewById(R.id.num8);

            //评论数据


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
                Log.i("data_position实体", data.toString());

                headerViewHolder.name.setText(data.getPosition_name());
                headerViewHolder.settlement.setText(data.getSettlement());
                headerViewHolder.salary.setText(data.getSalary());
                headerViewHolder.area.setText(data.getArea());
                headerViewHolder.exp.setText(data.getExp());
                headerViewHolder.content.setText(data.getContent());
                headerViewHolder.requirement.setText(data.getRequirement());
                headerViewHolder.slogan.setText(data.getSlogan());

                headerViewHolder.employer_name.setText(empInfo.getEmp_name());
                headerViewHolder.unit_name.setText(empInfo.getUnit_name());
                headerViewHolder.unit_area.setText(empInfo.getUnit_loc());
                headerViewHolder.descriptions.setText(empInfo.getUnit_descriptions());
                headerViewHolder.job_nums.setText("在招职位" + empInfo.getJob_nums() + "个");
                //数量要变颜色


                headerViewHolder.num1.setText(markInfo.getTotal_score() + "");
                headerViewHolder.num2.setText(markInfo.getPf() + "");
                headerViewHolder.num3.setText(markInfo.getPl() + "");
                headerViewHolder.num4.setText(markInfo.getWe() + "");
                headerViewHolder.num5.setText(markInfo.getLt() + "");
                headerViewHolder.num6.setText(markInfo.getPt() + "");
                headerViewHolder.num7.setText(markInfo.getOds() + "");
                headerViewHolder.num8.setText(markInfo.getDsps() + "");

                //报名按钮
                headerViewHolder.btn_signup.setOnClickListener(v -> {
                    Log.i("报名", "报名");
                    //调api
                    new Thread(() -> {
                        try {
                            OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                            SignupDto signupDto = new SignupDto();
                            signupDto.setTelephone(Constants.telephone);//TODO 后面改为当前用户
                            signupDto.setP_id(data.getP_id());

                            String json = JSON.toJSONString(signupDto);//dto转json
                            Request request = new Request.Builder()
                                    .url("http://114.55.239.213:8082/parttime/stu/signup")
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
                                    Log.i("error", "数据获取失败");
                                    e.printStackTrace();
                                }

                                @Override
                                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                    if (response.isSuccessful()) {//调用成功
                                        try {
                                            com.alibaba.fastjson.JSONObject jsonObj = JSON.parseObject(response.body().string());
                                            JSONObject data_signup = JSON.parseObject(jsonObj.getString("data"));
                                            Log.i("data_signup实体", data_signup.toString());

                                            if (data_signup.getString("memo").equals("该用户已报名")) {
                                                flag = 1;
                                                Log.i("flag", flag + "");
                                            }

                                            try {
                                                Thread.sleep(2000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
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

                    //延时5s，以显示ui的更新
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Log.i("flag2", flag + "");
                    if (flag == 1) {
                        //不能报名
                        Toast toast = Toast.makeText(context, "该用户已报名", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                        toast.show();
                    } else {
                        //能报名
                        Toast toast = Toast.makeText(context, "报名成功", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                        toast.show();

                        //跳转到首页
                        Log.i("btn", "点击了报名按钮");
                        Intent i = new Intent();
                        i.setClass(context, HomeActivity.class);
                        //一定要指定是第几个pager，这里填写1
                        i.putExtra("id", 1);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//
                        context.startActivity(i);
                    }
                });


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    class async extends AsyncTask<String, String, String> {
//        private  HeaderViewHolder headerViewHolder ;
//        private MarkInfo markInfo;
//
//        public async(MarkInfo markInfo,RecyclerView.ViewHolder holder) {
//            this.markInfo=markInfo;
//            this.headerViewHolder =(HeaderViewHolder) holder;
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            try {
//                Thread.sleep(2000);
//
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }

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
