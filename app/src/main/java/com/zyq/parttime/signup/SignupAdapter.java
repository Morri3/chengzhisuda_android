package com.zyq.parttime.signup;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.util.Log;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zyq.parttime.R;
import com.zyq.parttime.form.Position;
import com.zyq.parttime.form.Signup;
import com.zyq.parttime.home.HomeAdapter;
import com.zyq.parttime.position.PositionDetail;
import com.zyq.parttime.sp.Cancels;
import com.zyq.parttime.sp.Marks;
import com.zyq.parttime.util.Utils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SignupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Serializable {
    private static final int HEADER_VIEW = 0; //头部

    private Context context;
    private List<Signup> dataList; //数据列表

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
                Signup data = dataList.get(pos);//获取当前item的数据
                Log.i("data", data.toString());//test

                //调api，分别获取每个item是否评分、评论、取消了


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


                boolean isComment=false;
                if(isComment==false){
                    //未评论
                    //不显示评论后的textview
                    headerViewHolder.comment_time_text.setVisibility(View.VISIBLE);
                    headerViewHolder.comment_time.setVisibility(View.VISIBLE);
                    headerViewHolder.comment_content.setVisibility(View.VISIBLE);
                    headerViewHolder.btn_comment.setVisibility(View.VISIBLE);
                    headerViewHolder.comment_content_after.setVisibility(View.INVISIBLE);
                }else{
                    //已经评论
                    //显示评论后的textview
                    headerViewHolder.comment_time_text.setVisibility(View.INVISIBLE);
                    headerViewHolder.comment_time.setVisibility(View.INVISIBLE);
                    headerViewHolder.comment_content.setVisibility(View.INVISIBLE);
                    headerViewHolder.btn_comment.setVisibility(View.INVISIBLE);
                    headerViewHolder.comment_content_after.setVisibility(View.VISIBLE);
                }

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


                //报名自身属性
                headerViewHolder.status.setText(data.getSignup_status());
                headerViewHolder.update_time.setText(data.getUpdate_time() + "");

                //评分按钮
                headerViewHolder.mark.setOnClickListener(view -> {
                    Toast.makeText(this.context, "点击了评分按钮", Toast.LENGTH_SHORT).show();

                    for (Signup i : dataList) {
                        Log.i("z3", i.toString());
                    }

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
//                    Gson gson2 = new Gson();
//                    String res = gson2.toJson(marks);
//                    Log.i("mark",res);
//                    editor.putString("marks",res);
////                    editor.putBoolean("isMark",true);//标记为已经点了评分
////                    editor.putInt("pos",pos);//当前选择的item
//                    editor.commit();//提交

                    //改变取消按钮的位置
                    ConstraintSet set =new ConstraintSet();
                    set.clone(headerViewHolder.line4);
                    set.connect(
                            R.id.cancel,
                            ConstraintSet.LEFT,
                            R.id.mark,
                            ConstraintSet.LEFT
                    );
                    set.setMargin(R.id.cancel,ConstraintSet.START,Utils.px2dp(context,75));
                    set.applyTo(headerViewHolder.line4);//设置约束条件生效

                    //跳转到评分界面
                    Intent editpro = new Intent(context, MarkActivity.class);
                    editpro.putExtra("signup_data", (Serializable) dataList);//传递兼职数据
                    editpro.putExtra("pos", data.getS_id());//传递当前选中的报名的id
                    context.startActivity(editpro);
                });
                headerViewHolder.mark_icon.setOnClickListener(view -> {
                    Toast.makeText(this.context, "点击了评分按钮", Toast.LENGTH_SHORT).show();

                    for (Signup i : dataList) {
                        Log.i("z3", i.toString());
                    }

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
                    ConstraintSet set =new ConstraintSet();
                    set.clone(headerViewHolder.line4);
                    set.connect(
                                R.id.cancel,
                                ConstraintSet.LEFT,
                                R.id.mark,
                                ConstraintSet.LEFT
                        );
                    set.setMargin(R.id.cancel,ConstraintSet.START,Utils.px2dp(context,75));
                    set.applyTo(headerViewHolder.line4);//设置约束条件生效

                    //跳转到评分界面
                    Intent editpro = new Intent(context, MarkActivity.class);
                    editpro.putExtra("signup_data", (Serializable) dataList);//传递兼职数据
                    editpro.putExtra("pos", data.getS_id());//传递当前选中的报名的id
                    context.startActivity(editpro);
                });

                //取消按钮
                headerViewHolder.cancel.setOnClickListener(view -> {
                    //调api，改报名状态


                    //移除组件
                    headerViewHolder.line4.removeView(headerViewHolder.cancel);
                    headerViewHolder.line4.removeView(headerViewHolder.cancel_icon);

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


                    //移除组件
                    headerViewHolder.line4.removeView(headerViewHolder.cancel);
                    headerViewHolder.line4.removeView(headerViewHolder.cancel_icon);

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


                    //确定按钮+输入框，显示TextView，隐藏按钮,移除组件
                    headerViewHolder.comment_content_after.setText(content);
                    //移除2个控件
                    headerViewHolder.comment_content.setVisibility(View.INVISIBLE);
                    headerViewHolder.btn_comment.setVisibility(View.INVISIBLE);
                    //显示Textview
                    headerViewHolder.comment_content_after.setVisibility(View.VISIBLE);
                });
            }
        } catch (Exception e) {
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
