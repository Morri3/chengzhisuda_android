package com.zyq.parttime.signup;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zyq.parttime.R;
import com.zyq.parttime.form.Position;
import com.zyq.parttime.form.Signup;
import com.zyq.parttime.home.HomeAdapter;
import com.zyq.parttime.position.PositionDetail;
import com.zyq.parttime.sp.Cancel;
import com.zyq.parttime.sp.Cancels;
import com.zyq.parttime.sp.CommentPost;
import com.zyq.parttime.sp.HistoryDto;
import com.zyq.parttime.sp.Marks;
import com.zyq.parttime.sp.PositionInfo;
import com.zyq.parttime.util.Utils;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public class SignupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Serializable {
    private static final int HEADER_VIEW = 0; //头部

    private Context context;
    private List<Signup> dataList = new ArrayList<>(); //数据列表
    private HeaderViewHolder viewHolder;

//    private List<Marks> marks=new ArrayList<>();
//    private List<Cancels> cancels=new ArrayList<>();
//    //sharedpreference
//    private SharedPreferences pref;
//    private SharedPreferences.Editor editor;

    public SignupAdapter(Context context, List<Signup> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View view) {
            super(view);
            view.setOnClickListener(v -> {
            });
        }

        //封装数据
        private TextView name;
        private TextView status;
        private TextView area;
        private TextView settlement;
        private TextView salary;
        private TextView update_time;
        private TextView work_time;
        private TextView mark;
        private ImageView mark_icon;
        private TextView cancel;
        private ImageView cancel_icon;
        private TextView comment_time_text;
        private TextView comment_time;
        private EditText comment_content;
        private TextView comment_content_after;
        private AppCompatButton btn_comment;
        private View bg;
        private ConstraintLayout line4;
        private ConstraintLayout item;
        private View divider;

        public View getDivider() {
            return divider;
        }

        public void setDivider(View divider) {
            this.divider = divider;
        }

        private TextView no_content;

        public TextView getNo_content() {
            return no_content;
        }

        public void setNo_content(TextView no_content) {
            this.no_content = no_content;
        }

        public TextView getName() {
            return name;
        }

        public void setName(TextView name) {
            this.name = name;
        }

        public TextView getStatus() {
            return status;
        }

        public void setStatus(TextView status) {
            this.status = status;
        }

        public TextView getArea() {
            return area;
        }

        public void setArea(TextView area) {
            this.area = area;
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

        public TextView getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(TextView update_time) {
            this.update_time = update_time;
        }

        public TextView getWork_time() {
            return work_time;
        }

        public void setWork_time(TextView work_time) {
            this.work_time = work_time;
        }

        public TextView getMark() {
            return mark;
        }

        public void setMark(TextView mark) {
            this.mark = mark;
        }

        public ImageView getMark_icon() {
            return mark_icon;
        }

        public void setMark_icon(ImageView mark_icon) {
            this.mark_icon = mark_icon;
        }

        public TextView getCancel() {
            return cancel;
        }

        public void setCancel(TextView cancel) {
            this.cancel = cancel;
        }

        public ImageView getCancel_icon() {
            return cancel_icon;
        }

        public void setCancel_icon(ImageView cancel_icon) {
            this.cancel_icon = cancel_icon;
        }

        public TextView getComment_time_text() {
            return comment_time_text;
        }

        public void setComment_time_text(TextView comment_time_text) {
            this.comment_time_text = comment_time_text;
        }

        public TextView getComment_time() {
            return comment_time;
        }

        public void setComment_time(TextView comment_time) {
            this.comment_time = comment_time;
        }

        public EditText getComment_content() {
            return comment_content;
        }

        public void setComment_content(EditText comment_content) {
            this.comment_content = comment_content;
        }

        public AppCompatButton getBtn_comment() {
            return btn_comment;
        }

        public void setBtn_comment(AppCompatButton btn_comment) {
            this.btn_comment = btn_comment;
        }

        public TextView getComment_content_after() {
            return comment_content_after;
        }

        public void setComment_content_after(TextView comment_content_after) {
            this.comment_content_after = comment_content_after;
        }

        public View getBg() {
            return bg;
        }

        public void setBg(View bg) {
            this.bg = bg;
        }

        public ConstraintLayout getLine4() {
            return line4;
        }

        public void setLine4(ConstraintLayout line4) {
            this.line4 = line4;
        }

        public ConstraintLayout getItem() {
            return item;
        }

        public void setItem(ConstraintLayout item) {
            this.item = item;
        }

    }

    //创建头部viewhodler
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER_VIEW) {
            View headerView = LayoutInflater.from(context).inflate(R.layout.signup_item, null);
            HeaderViewHolder headerHolder = new HeaderViewHolder(headerView);
            //接下来是控件的引用声明
            headerHolder.name = headerView.findViewById(R.id.name);
            headerHolder.status = headerView.findViewById(R.id.status);
            headerHolder.area = headerView.findViewById(R.id.area);
            headerHolder.settlement = headerView.findViewById(R.id.settlement);
            headerHolder.salary = headerView.findViewById(R.id.salary);
            headerHolder.update_time = headerView.findViewById(R.id.update_time);
            headerHolder.work_time = headerView.findViewById(R.id.work_time);
            headerHolder.mark = headerView.findViewById(R.id.mark);
            headerHolder.mark_icon = headerView.findViewById(R.id.mark_icon);
            headerHolder.cancel = headerView.findViewById(R.id.cancel);
            headerHolder.cancel_icon = headerView.findViewById(R.id.cancel_icon);
            headerHolder.comment_time_text = headerView.findViewById(R.id.comment_time_text);
            headerHolder.comment_time = headerView.findViewById(R.id.comment_time);
            headerHolder.comment_content = headerView.findViewById(R.id.comment_content);
            headerHolder.btn_comment = headerView.findViewById(R.id.btn_comment);
            headerHolder.comment_content_after = headerView.findViewById(R.id.comment_content_after);
            headerHolder.bg = headerView.findViewById(R.id.bg);
            headerHolder.line4 = headerView.findViewById(R.id.line4);
            headerHolder.item = headerView.findViewById(R.id.item);
            headerHolder.no_content = headerView.findViewById(R.id.no_content);
            headerHolder.divider = headerView.findViewById(R.id.divider);
            return headerHolder;
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int pos) {
        try {
            if (holder instanceof HeaderViewHolder) {//属于头部
                HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
                viewHolder = headerViewHolder;
                Signup data = dataList.get(pos);//获取当前item的数据
                Log.i("报名item的信息", data.toString());

                if (data != null && data.getS_id() != 0) {//有记录
                    //根据p_id找到position实体，调api  TODO
                    PositionInfo positionInfo = new PositionInfo();
                    new Thread(() -> {
                        try {
                            OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                            Request request = new Request.Builder()
                                    .url("http://114.55.239.213:8082/parttime/stu/get_one?p_id=" + data.getP_id())
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
                                            com.alibaba.fastjson.JSONObject jsonObj = JSON.parseObject(response.body().string());
                                            JSONObject data_position = JSON.parseObject(jsonObj.getString("data"));
                                            Log.i("data_position实体", data_position.toString());

                                            String position_name = data_position.getString("position_name");
                                            String area = data_position.getString("area");
                                            String settlement = data_position.getString("settlement");
                                            String salary = data_position.getString("salary");
                                            String work_time = data_position.getString("work_time");
                                            positionInfo.setPosition_name(position_name);
                                            positionInfo.setSettlement(settlement);
                                            positionInfo.setSalary(salary);
                                            positionInfo.setWork_time(work_time);
                                            positionInfo.setArea(area);
                                            String position_status = data.getSignup_status();
                                            positionInfo.setPosition_status(position_status);

                                            //延时3s
                                            try {
                                                Thread.sleep(1500);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            ((Activity) context).runOnUiThread(() -> {
                                                headerViewHolder.name.setText(position_name);
                                                headerViewHolder.area.setText(area);
                                                headerViewHolder.settlement.setText(settlement);
                                                headerViewHolder.salary.setText(salary);
                                                headerViewHolder.work_time.setText(work_time);
                                                headerViewHolder.status.setText(data.getSignup_status());

                                                //设置状态字段的颜色
                                                if (data.getSignup_status().equals("已报名")) {
                                                    headerViewHolder.status.setTextColor(context.getResources().getColor(R.color.status_1));
                                                } else if (data.getSignup_status().equals("已录取")) {
                                                    headerViewHolder.status.setTextColor(context.getResources().getColor(R.color.status_2));
                                                } else if (data.getSignup_status().equals("已结束")) {
                                                    headerViewHolder.status.setTextColor(context.getResources().getColor(R.color.status_3));
                                                } else if (data.getSignup_status().equals("已取消")) {
                                                    headerViewHolder.status.setTextColor(context.getResources().getColor(R.color.status_4));
                                                }

                                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                                                String update = sdf.format(data.getCreate_time());
                                                headerViewHolder.update_time.setText(update);

                                                //取消按钮的可见性
                                                if (positionInfo.getPosition_status().equals("已结束")
                                                        || positionInfo.getPosition_status().equals("已取消")
                                                        || positionInfo.getPosition_status().equals("已录取")) {
                                                    headerViewHolder.cancel.setVisibility(View.INVISIBLE);//不可见
                                                    headerViewHolder.cancel_icon.setVisibility(View.INVISIBLE);//不可见

                                                    //移除组件
                                                    headerViewHolder.line4.removeView(headerViewHolder.cancel);
                                                    headerViewHolder.line4.removeView(headerViewHolder.cancel_icon);
                                                } else if (positionInfo.getPosition_status().equals("已报名")) {
                                                    headerViewHolder.cancel.setVisibility(View.VISIBLE);//可见
                                                    headerViewHolder.cancel_icon.setVisibility(View.VISIBLE);//可见
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

                    //评论按钮的可见性
                    new Thread(() -> {
                        try {
                            OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                            Request request = new Request.Builder()
                                    .url("http://114.55.239.213:8082/comments/get?s_id=" + data.getS_id())
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
                                            com.alibaba.fastjson.JSONObject jsonObj = JSON.parseObject(response.body().string());
                                            JSONObject data_comment = JSON.parseObject(jsonObj.getString("data"));
                                            Log.i("data_comment实体", data_comment.toString());

                                            //延时3s
                                            try {
                                                Thread.sleep(1500);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            ((Activity) context).runOnUiThread(() -> {
                                                if (data.getSignup_status().equals("已结束")) {
                                                    //显示评论的相关组件
                                                    if (data_comment.getString("memo").equals("获取失败")) {
                                                        //说明没有评论过
                                                        //不显示评论后的textview
                                                        headerViewHolder.comment_time.setVisibility(View.INVISIBLE);
                                                        headerViewHolder.comment_content.setVisibility(View.VISIBLE);
                                                        headerViewHolder.btn_comment.setVisibility(View.VISIBLE);
                                                        headerViewHolder.comment_content_after.setVisibility(View.INVISIBLE);
                                                    } else {
                                                        //评论过
                                                        //显示评论后的textview
                                                        headerViewHolder.comment_time.setText(data_comment.getString("create_time"));
                                                        headerViewHolder.comment_content_after.setVisibility(View.VISIBLE);
                                                        headerViewHolder.comment_content_after.setText(data_comment.getString("content"));
                                                        headerViewHolder.comment_time.setVisibility(View.VISIBLE);
                                                        headerViewHolder.comment_content.setVisibility(View.INVISIBLE);
                                                        headerViewHolder.btn_comment.setVisibility(View.INVISIBLE);
                                                    }
                                                } else {
                                                    //不显示评论的相关组件
                                                    headerViewHolder.comment_time.setVisibility(View.INVISIBLE);
                                                    headerViewHolder.comment_content.setVisibility(View.INVISIBLE);
                                                    headerViewHolder.btn_comment.setVisibility(View.INVISIBLE);
                                                    headerViewHolder.comment_content_after.setVisibility(View.VISIBLE);
                                                    headerViewHolder.comment_content_after.setText("目前无法评论");
                                                    headerViewHolder.comment_content_after.setTextColor(context.getResources().getColor(R.color.text_grey_color));
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

                    //评分按钮的可见性
                    new Thread(() -> {
                        try {
                            OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                            Request request = new Request.Builder()
                                    .url("http://114.55.239.213:8082/mark/get?s_id=" + data.getS_id())
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
                                            com.alibaba.fastjson.JSONObject jsonObj = JSON.parseObject(response.body().string());
                                            JSONObject data_mark = JSON.parseObject(jsonObj.getString("data"));
                                            Log.i("data_mark实体", data_mark.toString());

                                            //4s延时
                                            try {
                                                Thread.sleep(1500);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }

                                            ((Activity) context).runOnUiThread(() -> {
                                                if (data_mark != null) {
                                                    if (data.getSignup_status().equals("已结束")) {
                                                        if (data_mark.getString("memo").equals("获取失败")) {
                                                            //说明没有评分过
                                                            headerViewHolder.mark.setVisibility(View.VISIBLE);
                                                            headerViewHolder.mark_icon.setVisibility(View.VISIBLE);
                                                        } else {
                                                            //评分过
                                                            headerViewHolder.mark.setVisibility(View.INVISIBLE);
                                                            headerViewHolder.mark_icon.setVisibility(View.INVISIBLE);

                                                            //移除组件
                                                            headerViewHolder.line4.removeView(headerViewHolder.mark);
                                                            headerViewHolder.line4.removeView(headerViewHolder.mark_icon);

                                                            //改变取消按钮的位置
                                                            ConstraintSet set = new ConstraintSet();
                                                            set.clone(headerViewHolder.line4);
                                                            set.connect(
                                                                    R.id.cancel,
                                                                    ConstraintSet.LEFT,
                                                                    R.id.mark,
                                                                    ConstraintSet.LEFT
                                                            );
                                                            set.setMargin(R.id.cancel, ConstraintSet.START, Utils.px2dp(context, 75));
                                                            set.applyTo(headerViewHolder.line4);//设置约束条件生效
                                                        }
                                                    } else {//已结束+其他状态不能评分
                                                        headerViewHolder.mark.setVisibility(View.INVISIBLE);
                                                        headerViewHolder.mark_icon.setVisibility(View.INVISIBLE);

                                                        //移除组件
                                                        headerViewHolder.line4.removeView(headerViewHolder.mark);
                                                        headerViewHolder.line4.removeView(headerViewHolder.mark_icon);

                                                        //改变取消按钮的位置
                                                        ConstraintSet set = new ConstraintSet();
                                                        set.clone(headerViewHolder.line4);
                                                        set.connect(
                                                                R.id.cancel,
                                                                ConstraintSet.LEFT,
                                                                R.id.mark,
                                                                ConstraintSet.LEFT
                                                        );
                                                        set.setMargin(R.id.cancel, ConstraintSet.START, Utils.px2dp(context, 75));
                                                        set.applyTo(headerViewHolder.line4);//设置约束条件生效
                                                    }
                                                } else {
                                                    //没记录就显示
                                                    ((Activity) context).runOnUiThread(() -> {
                                                        headerViewHolder.mark.setVisibility(View.VISIBLE);
                                                        headerViewHolder.mark_icon.setVisibility(View.VISIBLE);
                                                    });
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

                    //延时3s
                    try {
                        Thread.sleep(1500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    ((Activity) context).runOnUiThread(() -> {
                        //不显示404
                        headerViewHolder.no_content.setVisibility(View.INVISIBLE);

                        //评分按钮
                        headerViewHolder.mark.setOnClickListener(view -> {
                            Toast toast = Toast.makeText(context, "点击了评分按钮", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                            toast.show();

                            //移除组件
                            headerViewHolder.line4.removeView(headerViewHolder.mark);
                            headerViewHolder.line4.removeView(headerViewHolder.mark_icon);

                            //改变取消按钮的位置
                            ConstraintSet set = new ConstraintSet();
                            set.clone(headerViewHolder.line4);
                            set.connect(
                                    R.id.cancel,
                                    ConstraintSet.LEFT,
                                    R.id.mark,
                                    ConstraintSet.LEFT
                            );
                            set.setMargin(R.id.cancel, ConstraintSet.START, Utils.px2dp(context, 75));
                            set.applyTo(headerViewHolder.line4);//设置约束条件生效

                            //跳转到评分界面
                            Intent editpro = new Intent(context, MarkActivity.class);
                            editpro.putExtra("signup_data", (Serializable) dataList);//传递兼职数据
                            editpro.putExtra("pos", data.getS_id());//传递当前选中的报名的id
                            context.startActivity(editpro);
                        });
                        headerViewHolder.mark_icon.setOnClickListener(view -> {
                            Toast toast = Toast.makeText(context, "点击了评分按钮", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                            toast.show();

                            //移除组件
                            headerViewHolder.line4.removeView(headerViewHolder.mark);
                            headerViewHolder.line4.removeView(headerViewHolder.mark_icon);

//                    //记录sharedpreference
//                    editor = pref.edit();
//                    //构造对象
//                    Marks mark=new Marks();
//                    mark.setPos(pos);
//                    mark.setFlag(true);
//                    marks.add(mark);
//                    Gson gson3 = new Gson();
//                    String res = gson3.toJson(marks);
//                    Log.i("mark",res);
//                    editor.putString("marks",res);
////                    editor.putBoolean("isMark",true);//标记为已经点了评分
////                    editor.putInt("pos",pos);//当前选择的item
//                    editor.commit();//提交

                            //改变取消按钮的位置
                            ConstraintSet set = new ConstraintSet();
                            set.clone(headerViewHolder.line4);
                            set.connect(
                                    R.id.cancel,
                                    ConstraintSet.LEFT,
                                    R.id.mark,
                                    ConstraintSet.LEFT
                            );
                            set.setMargin(R.id.cancel, ConstraintSet.START, Utils.px2dp(context, 75));
                            set.applyTo(headerViewHolder.line4);//设置约束条件生效

                            //跳转到评分界面
                            Intent editpro = new Intent(context, MarkActivity.class);
                            editpro.putExtra("signup_data", (Serializable) dataList);//传递兼职数据
                            editpro.putExtra("pos", data.getS_id());//传递当前选中的报名的id
                            context.startActivity(editpro);
                        });

                        //取消按钮
                        headerViewHolder.cancel.setOnClickListener(view -> {
                            //调api，改报名状态  TODO
                            new Thread(() -> {
                                try {
                                    OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                                    Cancel dto = new Cancel();
                                    dto.setS_id(data.getS_id());
                                    dto.setTelephone(data.getStu_id());
                                    String json = JSON.toJSONString(dto);//dto转json
                                    Request request = new Request.Builder()
                                            .url("http://114.55.239.213:8082/parttime/stu/cancel")
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
                                                    JSONObject data_cancel = JSON.parseObject(jsonObj.getString("data"));
                                                    Log.i("data_cancel实体", data_cancel.toString());

                                                    //延时3s
                                                    try {
                                                        Thread.sleep(1500);
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }

                                                    ((Activity) context).runOnUiThread(() -> {
                                                        if (!data_cancel.getString("memo").equals("不存在报名或报名不能取消")) {
                                                            Toast toast = Toast.makeText(context, "已成功取消报名！", Toast.LENGTH_SHORT);
                                                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                                            toast.show();

                                                            //移除组件
                                                            headerViewHolder.line4.removeView(headerViewHolder.cancel);
                                                            headerViewHolder.line4.removeView(headerViewHolder.cancel_icon);

                                                            headerViewHolder.status.setText("已取消");
                                                        } else {
                                                            //第一个参数：设置toast在屏幕中显示的位置。这里设置是居中靠顶
                                                            //第二个参数：相对于第一个参数设置toast位置的横向X轴的偏移量，正数向右偏移，负数向左偏移
                                                            //第三个参数：相对于第一个参数设置toast位置的纵向y轴的偏移量，正数向下偏移，负数向上偏移
                                                            //如果设置的偏移量超过了屏幕的范围，toast将在屏幕内靠近超出的那个边界显示
                                                            //屏幕居中显示，X轴和Y轴偏移量都是0
                                                            Toast toast = Toast.makeText(context, "不能取消该报名", Toast.LENGTH_SHORT);
                                                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                                            toast.show();
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

                            //延时3s
                            try {
                                Thread.sleep(1500);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            //移除组件
                            ((Activity) context).runOnUiThread(() -> {
                                headerViewHolder.line4.removeView(headerViewHolder.cancel);
                                headerViewHolder.line4.removeView(headerViewHolder.cancel_icon);
                            });
//                    //记录sharedpreference
//                    editor = pref.edit();
//                    //构造对象
//                    Cancels cancel=new Cancels();
//                    cancel.setPos(pos);
//                    cancel.setFlag(true);
//                    cancels.add(cancel);
//                    Gson gson4 = new Gson();
//                    String res = gson4.toJson(cancels);
//                    Log.i("cancel",res);
//                    editor.putString("cancels",res);
//                    editor.commit();//提交
                        });
                        headerViewHolder.cancel_icon.setOnClickListener(view -> {
                            //调api，改报名状态
                            new Thread(() -> {
                                try {
                                    OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                                    Cancel dto = new Cancel();
                                    dto.setS_id(data.getS_id());
                                    dto.setTelephone(data.getStu_id());
                                    String json = JSON.toJSONString(dto);//dto转json
                                    Request request = new Request.Builder()
                                            .url("http://114.55.239.213:8082/parttime/stu/cancel")
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
                                                    JSONObject data_cancel = JSON.parseObject(jsonObj.getString("data"));
                                                    Log.i("data_cancel实体", data_cancel.toString());

                                                    //延时3s
                                                    try {
                                                        Thread.sleep(1500);
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }

                                                    ((Activity) context).runOnUiThread(() -> {
                                                        if (!data_cancel.getString("memo").equals("不存在报名或报名不能取消")) {
                                                            Toast toast = Toast.makeText(context, "已成功取消报名！", Toast.LENGTH_SHORT);
                                                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                                            toast.show();

                                                            //移除组件
                                                            headerViewHolder.line4.removeView(headerViewHolder.cancel);
                                                            headerViewHolder.line4.removeView(headerViewHolder.cancel_icon);
                                                        } else {
                                                            //第一个参数：设置toast在屏幕中显示的位置。这里设置是居中靠顶
                                                            //第二个参数：相对于第一个参数设置toast位置的横向X轴的偏移量，正数向右偏移，负数向左偏移
                                                            //第三个参数：相对于第一个参数设置toast位置的纵向y轴的偏移量，正数向下偏移，负数向上偏移
                                                            //如果设置的偏移量超过了屏幕的范围，toast将在屏幕内靠近超出的那个边界显示
                                                            //屏幕居中显示，X轴和Y轴偏移量都是0
                                                            Toast toast = Toast.makeText(context, "不能取消该报名", Toast.LENGTH_SHORT);
                                                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                                            toast.show();
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

                            //移除组件
                            ((Activity) context).runOnUiThread(() -> {
                                headerViewHolder.line4.removeView(headerViewHolder.cancel);
                                headerViewHolder.line4.removeView(headerViewHolder.cancel_icon);
                            });
//                    //记录sharedpreference
//                    editor = pref.edit();
//                    //构造对象
//                    Cancels cancel=new Cancels();
//                    cancel.setPos(pos);
//                    cancel.setFlag(true);
//                    cancels.add(cancel);
//                    Gson gson5 = new Gson();
//                    String res = gson5.toJson(cancels);
//                    Log.i("cancel",res);
//                    editor.putString("cancels",res);
////                    editor.putBoolean("isCancel",true);//标记为已经点了取消
////                    editor.putInt("pos",pos);//当前选择的item
//                    editor.commit();//提交
                        });

                        //评论确定按钮
                        headerViewHolder.btn_comment.setOnClickListener(view -> {
                            //获取输入框的内容
                            String content = headerViewHolder.comment_content.getText().toString();
                            Log.i("评论内容", content);

                            //调api，content存到db TODO
                            new Thread(() -> {
                                try {
                                    OkHttpClient client = new OkHttpClient();//创建Okhttp客户端
                                    CommentPost dto = new CommentPost();
                                    dto.setS_id(data.getS_id());
                                    dto.setContent(content);
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Date now = sdf.parse(sdf.format(new Date()));
                                    dto.setCreate_time(now);
                                    String json = JSON.toJSONString(dto);//dto转json
                                    Request request = new Request.Builder()
                                            .url("http://114.55.239.213:8082/comments/stu/post")
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
                                                    JSONObject data_comment_post = JSON.parseObject(jsonObj.getString("data"));
                                                    Log.i("data_comment_post实体", data_comment_post.toString());

                                                    //延时3s
                                                    try {
                                                        Thread.sleep(1500);
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }

                                                    ((Activity) context).runOnUiThread(() -> {
                                                        if (data_comment_post.getString("memo").equals("评论成功")) {
                                                            Toast toast = Toast.makeText(context, "评论成功！", Toast.LENGTH_SHORT);
                                                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                                            toast.show();

                                                            //确定按钮+输入框，显示TextView，隐藏按钮,移除组件
                                                            headerViewHolder.comment_content_after.setText(content);
                                                            headerViewHolder.comment_time.setVisibility(View.VISIBLE);
                                                            headerViewHolder.comment_time.setText(sdf.format(now));//设置时间
                                                            //移除2个控件
                                                            headerViewHolder.comment_content.setVisibility(View.INVISIBLE);
                                                            headerViewHolder.btn_comment.setVisibility(View.INVISIBLE);
                                                            //显示Textview
                                                            headerViewHolder.comment_content_after.setVisibility(View.VISIBLE);
                                                        } else {
                                                            Toast toast = Toast.makeText(context, "评论失败", Toast.LENGTH_SHORT);
                                                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                                                            toast.show();
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
                        });
                    });
                } else {
                    //延时3s
                    try {
                        Thread.sleep(1500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //为空，显示404
                    ((Activity) context).runOnUiThread(() -> {
                        headerViewHolder.no_content.setVisibility(View.VISIBLE);
                        headerViewHolder.area.setVisibility(View.INVISIBLE);
                        headerViewHolder.salary.setVisibility(View.INVISIBLE);
                        headerViewHolder.settlement.setVisibility(View.INVISIBLE);
                        headerViewHolder.work_time.setVisibility(View.INVISIBLE);
                        headerViewHolder.comment_content_after.setVisibility(View.INVISIBLE);
                        headerViewHolder.comment_content.setVisibility(View.INVISIBLE);
                        headerViewHolder.comment_time.setVisibility(View.INVISIBLE);
                        headerViewHolder.cancel_icon.setVisibility(View.INVISIBLE);
                        headerViewHolder.name.setVisibility(View.INVISIBLE);
                        headerViewHolder.btn_comment.setVisibility(View.INVISIBLE);
                        headerViewHolder.mark_icon.setVisibility(View.INVISIBLE);
                        headerViewHolder.comment_time_text.setVisibility(View.INVISIBLE);
                        headerViewHolder.status.setVisibility(View.INVISIBLE);
                        headerViewHolder.update_time.setVisibility(View.INVISIBLE);
                        headerViewHolder.mark.setVisibility(View.INVISIBLE);
                        headerViewHolder.cancel.setVisibility(View.INVISIBLE);
                        headerViewHolder.line4.setVisibility(View.INVISIBLE);
                        headerViewHolder.divider.setVisibility(View.INVISIBLE);
                    });
                }
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }

    }

    //重写getItemCount方法
    @Override
    public int getItemCount() {
        return dataList.size();
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
