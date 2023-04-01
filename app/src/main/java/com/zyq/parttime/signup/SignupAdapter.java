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

                if (data != null && data.getS_id() != 0) {
                    //有记录
                    //                //隐藏评论的四个控件
//                headerViewHolder.comment_time_text.setVisibility(View.INVISIBLE);
//                headerViewHolder.comment_time.setVisibility(View.INVISIBLE);
//                headerViewHolder.comment_content.setVisibility(View.INVISIBLE);
//                headerViewHolder.btn_comment.setVisibility(View.INVISIBLE);

                    //动态改变整个item的高度
//                ConstraintLayout.LayoutParams p = (ConstraintLayout.LayoutParams)headerViewHolder.item.getLayoutParams();
//                p.height = Utils.px2dp(context,170);
//                headerViewHolder.item.setLayoutParams(p);

//                View rvView = LayoutInflater.from(context).inflate(R.layout.fragment_signup, null);
//                RecyclerView rv=(RecyclerView) rvView.findViewById(R.id.rv_signup);
//                rv.addView(headerViewHolder.item);
                    //1950
//                ConstraintLayout layout=new ConstraintLayout(context);
//                ConstraintLayout.LayoutParams p=new ConstraintLayout.LayoutParams
//                        (ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
//                layout.addView(headerViewHolder.item,p);
//                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) headerViewHolder.item.getLayoutParams();
//                params.width = ConstraintLayout.LayoutParams.MATCH_PARENT;
//                params.height = Utils.px2dp(context,170);
//                headerViewHolder.item.setLayoutParams(params);

//                LinearLayoutManager manager = new LinearLayoutManager(context){
//                    @Override
//                    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
//                        View view = recycler.getViewForPosition(0);
//                        if (view != null) {
//                            measureChild(view, widthSpec, heightSpec);
//                            //int measuredWidth = View.MeasureSpec.getSize(widthSpec);
//                            int measuredHeight = view.getMeasuredHeight();
//                            int showHeight = measuredHeight * state.getItemCount();
//                            if(state.getItemCount() >= 5){
//                                showHeight = measuredHeight * 5;
//                            }
//                            setMeasuredDimension(widthSpec, showHeight);
//                        }
//                    }
//                };
//                manager.setAutoMeasureEnabled(false);
//                exceptionListRecyclerview.setHasFixedSize(false);
//                exceptionListRecyclerview.setLayoutManager(manager);
//                exceptionListRecyclerview.setItemAnimator(new DefaultItemAnimator());

//                headerViewHolder.item.setLayoutParams(new RecyclerView.LayoutParams
//                        (RecyclerView.LayoutParams.MATCH_PARENT,
//                                Utils.px2dp(context,170)));

//                //动态改变背景view的约束布局
//                ConstraintSet s1 =new ConstraintSet();
//                s1.clone(headerViewHolder.item);
//                s1.constrainHeight(R.id.bg,Utils.px2dp(context,140));
//                s1.applyTo(headerViewHolder.item);//设置约束条件生效

//                //动态添加组件(3个按钮)
//                ConstraintSet set =new ConstraintSet();
//                set.clone(headerViewHolder.item);
//                //1
//                TextView btn1_2 = new TextView(context);
//                btn1_2.setText("评分");
//                btn1_2.setWidth(Utils.px2dp(context,57));
//                btn1_2.setHeight(Utils.px2dp(context,20));
//                btn1_2.setTextColor(context.getResources().getColor(R.color.text_black_color));
//                btn1_2.setTextSize(12);
//                btn1_2.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
//                btn1_2.setPadding(0,Utils.px2dp(context,2),Utils.px2dp(context,5),0);
//                btn1_2.setId(8881);
//                btn1_2.setBackgroundResource(R.drawable.purple_round_bg);
////                set.connect();
////                setParam(R.id.cancel,set);
//                headerViewHolder.line4.addView(btn1_2);//添加组件
//                ImageView btn1_1=new ImageView(context);
//                btn1_1.setImageResource(R.drawable.button_like);
//                //设置宽高
//                btn1_1.setLayoutParams(new ConstraintLayout.LayoutParams(Utils.px2dp(context,20), Utils.px2dp(context,20)));  //设置图片宽高
//                headerViewHolder.line4.addView(btn1_1);
//
//                //2
//                TextView btn2_2 = new TextView(context);
//                btn2_2.setText("评论");
//                btn2_2.setWidth(Utils.px2dp(context,57));
//                btn2_2.setHeight(Utils.px2dp(context,20));
//                btn2_2.setTextColor(context.getResources().getColor(R.color.text_black_color));
//                btn2_2.setTextSize(12);
//                btn2_2.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
//                btn2_2.setPadding(0,Utils.px2dp(context,2),Utils.px2dp(context,5),0);
//                btn2_2.setId(8882);
//                btn2_2.setBackgroundResource(R.drawable.purple_round_bg);
////                set.connect();
////                setParam(R.id.cancel,set);
//                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams
//                        (Utils.px2dp(context,57),Utils.px2dp(context,20));
//                params.topMargin=0;
//                params.leftMargin=Utils.px2dp(context,15);
//                params.rightMargin=0;
//                params.bottomMargin=0;
//                headerViewHolder.line4.addView(btn2_2,params);//添加组件
//
//                ImageView btn2_1=new ImageView(context);
//                btn2_1.setImageResource(R.drawable.button_comment);
//                //设置宽高
//                btn2_1.setLayoutParams(new ConstraintLayout.LayoutParams(Utils.px2dp(context,20), Utils.px2dp(context,20)));  //设置图片宽高
//                headerViewHolder.line4.addView(btn2_1);


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
//                                        Log.i("data_position实体", jsonObj.getString("data"));
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

                                            ((Activity) context).runOnUiThread(() -> {
                                                headerViewHolder.name.setText(position_name);
                                                headerViewHolder.area.setText(area);
                                                headerViewHolder.settlement.setText(settlement);
                                                headerViewHolder.salary.setText(salary);
                                                headerViewHolder.work_time.setText(work_time);
                                                headerViewHolder.status.setText(data.getSignup_status());
                                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                                                String update = sdf.format(data.getCreate_time());
                                                headerViewHolder.update_time.setText(update);

                                                //取消按钮的可见性
                                                if (positionInfo.getPosition_status().equals("已结束") || positionInfo.getPosition_status().equals("已录取")) {
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
//                                        Log.i("data_comment实体", jsonObj.getString("data"));
                                            JSONObject data_comment = JSON.parseObject(jsonObj.getString("data"));
                                            Log.i("data_comment实体", data_comment.toString());

                                            ((Activity) context).runOnUiThread(() -> {
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
//                                        Log.i("data_mark实体", jsonObj.getString("data"));
                                            JSONObject data_mark = JSON.parseObject(jsonObj.getString("data"));
                                            Log.i("data_mark实体", data_mark.toString());

                                            ((Activity) context).runOnUiThread(() -> {
                                                if (data_mark != null) {
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

                                                } else {//没记录就显示
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

//                //本地数据存储
//                pref = context.getSharedPreferences("signup", Context.MODE_MULTI_PROCESS);
//                //根据sharedpreference中数据，是否删去组件
////                boolean isMark=pref.getBoolean("isMark",false);
////                boolean isCancel=pref.getBoolean("isCancel",false);
////                int cur=pref.getInt("pos",-1);
//                String strM=pref.getString("marks","");
//                String strC=pref.getString("cancels","");
//
//                Gson gson = new Gson();
//                List<Marks> ps = gson.fromJson(strM, new TypeToken<List<Marks>>(){}.getType());
//                if(ps!=null){
//                    for(Marks item:ps){
//                        Log.i("item",item.toString());
//                    }
//                }


//                if(isMark==true && pos==cur){
//                    //移除mark
//                    headerViewHolder.line4.removeView(headerViewHolder.mark);
//                    headerViewHolder.line4.removeView(headerViewHolder.mark_icon);
//
//                    ConstraintSet set =new ConstraintSet();
//                    set.clone(headerViewHolder.line4);
//                    set.connect(
//                            R.id.cancel,
//                            ConstraintSet.LEFT,
//                            R.id.mark,
//                            ConstraintSet.LEFT
//                    );
//                    set.setMargin(R.id.cancel,ConstraintSet.START,Utils.px2dp(context,75));
//                    set.applyTo(headerViewHolder.line4);//设置约束条件生效
//                }
//
//                if(isCancel==true && pos==cur){
//                    headerViewHolder.line4.removeView(headerViewHolder.cancel);
//                    headerViewHolder.line4.removeView(headerViewHolder.cancel_icon);
//                }

                    //调api，判断是否已经评论 TODO，并设置数据

//                boolean isComment = false;
                    //UI更新
//                Message msg0 = new Message();
//                msg0.what = 1;
//                Bundle bundle0 = new Bundle();
//                bundle0.putBoolean("isComment", isComment);
//                msg0.setData(bundle0);
//                mHandler.sendMessage(msg0);//发送handler，更新ui

                    //0401
//                ((Activity) context).runOnUiThread(() -> {
//                    if (isComment == false) {
//                        //未评论
//                        //不显示评论后的textview
//                        headerViewHolder.comment_time_text.setVisibility(View.VISIBLE);
//                        headerViewHolder.comment_time.setVisibility(View.VISIBLE);
//                        headerViewHolder.comment_content.setVisibility(View.VISIBLE);
//                        headerViewHolder.btn_comment.setVisibility(View.VISIBLE);
//                        headerViewHolder.comment_content_after.setVisibility(View.INVISIBLE);
//                    } else {
//                        //已经评论
//                        //显示评论后的textview
//                        headerViewHolder.comment_time_text.setVisibility(View.INVISIBLE);
//                        headerViewHolder.comment_time.setVisibility(View.INVISIBLE);
//                        headerViewHolder.comment_content.setVisibility(View.INVISIBLE);
//                        headerViewHolder.btn_comment.setVisibility(View.INVISIBLE);
//                        headerViewHolder.comment_content_after.setVisibility(View.VISIBLE);
//                    }
//                });

                    //不显示404
                    ((Activity) context).runOnUiThread(() -> {
                        headerViewHolder.no_content.setVisibility(View.INVISIBLE);
                    });

                    //评分按钮
                    ((Activity) context).runOnUiThread(() -> {
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
//                                        Log.i("data_mark实体", jsonObj.getString("data"));
                                                    JSONObject data_cancel = JSON.parseObject(jsonObj.getString("data"));
                                                    Log.i("data_cancel实体", data_cancel.toString());

                                                    ((Activity) context).runOnUiThread(() -> {
                                                        if (!data_cancel.getString("memo").equals("不存在报名或报名不能取消")) {
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
//                                        Log.i("data_mark实体", jsonObj.getString("data"));
                                                    JSONObject data_cancel = JSON.parseObject(jsonObj.getString("data"));
                                                    Log.i("data_cancel实体", data_cancel.toString());

                                                    ((Activity) context).runOnUiThread(() -> {
                                                        if (!data_cancel.getString("memo").equals("不存在报名或报名不能取消")) {
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
//                                        Log.i("data_mark实体", jsonObj.getString("data"));
                                                    JSONObject data_comment_post = JSON.parseObject(jsonObj.getString("data"));
                                                    Log.i("data_comment_post实体", data_comment_post.toString());

                                                    ((Activity) context).runOnUiThread(() -> {
                                                        if (data_comment_post.getString("memo").equals("评论成功")) {
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
