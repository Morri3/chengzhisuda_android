//package com.zyq.parttime.home.recommend;
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.zyq.parttime.R;
//import com.zyq.parttime.form.Position;
//import com.zyq.parttime.home.HomeAdapter;
//import com.zyq.parttime.position.PositionDetail;
//
//import java.io.Serializable;
//import java.util.List;
//
//public class RecommendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Serializable {
//    private static final int HEADER_VIEW = 0; //头部
//
//    private Context context;
//    private List<Position> dataList; //数据列表
//
//    public RecommendAdapter(Context context, List<Position> dataList) {
//        this.context = context;
//        this.dataList = dataList;
//    }
//
//    public class HeaderViewHolder extends RecyclerView.ViewHolder {
//        public HeaderViewHolder(View view) {
//            super(view);
//            view.setOnClickListener(v -> {
//            });
//        }
//
//        //封装数据
//        private TextView name;
//        private TextView area;
//        private TextView settlement;
//        private TextView salary;
//        private TextView work_time;
//        private View divider;
//        private TextView detail_text;
//        private ImageView detail_icon;
//
//        //getter、setter方法
//        public TextView getName() {
//            return name;
//        }
//
//        public void setName(TextView name) {
//            this.name = name;
//        }
//
//        public TextView getArea() {
//            return area;
//        }
//
//        public void setArea(TextView area) {
//            this.area = area;
//        }
//
//        public TextView getSettlement() {
//            return settlement;
//        }
//
//        public void setSettlement(TextView settlement) {
//            this.settlement = settlement;
//        }
//
//        public TextView getSalary() {
//            return salary;
//        }
//
//        public void setSalary(TextView salary) {
//            this.salary = salary;
//        }
//
//        public TextView getWork_time() {
//            return work_time;
//        }
//
//        public void setWork_time(TextView work_time) {
//            this.work_time = work_time;
//        }
//
//        public View getDivider() {
//            return divider;
//        }
//
//        public void setDivider(View divider) {
//            this.divider = divider;
//        }
//
//        public TextView getDetail_text() {
//            return detail_text;
//        }
//
//        public void setDetail_text(TextView detail_text) {
//            this.detail_text = detail_text;
//        }
//
//        public ImageView getDetail_icon() {
//            return detail_icon;
//        }
//
//        public void setDetail_icon(ImageView detail_icon) {
//            this.detail_icon = detail_icon;
//        }
//    }
//
//    //创建头部viewhodler
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        if (viewType == HEADER_VIEW) {
//            View headerView = LayoutInflater.from(context).inflate(R.layout.home_item, null);
//            HeaderViewHolder headerHolder = new HeaderViewHolder(headerView);
//            //接下来是控件的引用声明
//            headerHolder.name = headerView.findViewById(R.id.name);
//            headerHolder.area = headerView.findViewById(R.id.area);
//            headerHolder.settlement = headerView.findViewById(R.id.settlement);
//            headerHolder.salary = headerView.findViewById(R.id.salary);
//            headerHolder.work_time = headerView.findViewById(R.id.work_time);
//            headerHolder.divider = headerView.findViewById(R.id.divider);
//            headerHolder.detail_text = headerView.findViewById(R.id.detail_text);
//            headerHolder.detail_icon = headerView.findViewById(R.id.detail_icon);
//            return headerHolder;
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int pos) {
//        try {
//            if (holder instanceof HomeAdapter.HeaderViewHolder) {//属于头部
//                HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
//
//                headerViewHolder.name.setVisibility(View.VISIBLE);
//                headerViewHolder.area.setVisibility(View.VISIBLE);
//                headerViewHolder.settlement.setVisibility(View.VISIBLE);
//                headerViewHolder.work_time.setVisibility(View.VISIBLE);
//                headerViewHolder.salary.setVisibility(View.VISIBLE);
//                headerViewHolder.detail_text.setVisibility(View.VISIBLE);
//                headerViewHolder.detail_icon.setVisibility(View.VISIBLE);
//                headerViewHolder.divider.setVisibility(View.VISIBLE);
//
//                //获取第一个推荐的兼职
//                Position position = dataList.get(0);
//                if (!position.getContent().equals("暂无兼职")) {
//                    //有兼职数据
//                    Position data = dataList.get(pos);//获取当前item的数据
//                    Log.i("data_adapter", data.toString());//test
//
//                    headerViewHolder.name.setText(data.getPosition_name());
//                    headerViewHolder.area.setText(data.getArea());
//                    headerViewHolder.settlement.setText(data.getSettlement());
//                    headerViewHolder.work_time.setText(data.getWork_time());
//                    headerViewHolder.salary.setText(data.getSalary());
//
//                    //最后一个不显示分割线
//                    if (pos == dataList.size() - 1) {
//                        headerViewHolder.divider.setVisibility(View.INVISIBLE);
//                    }
//
//                    //查看详情按钮
//                    headerViewHolder.detail_text.setOnClickListener(view -> {
//                        Toast toast = Toast.makeText(context, "欢迎查看兼职详情", Toast.LENGTH_SHORT);
//                        toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
//                        toast.show();
//
//                        //跳转到兼职详细界面
//                        Intent editpro = new Intent(context, PositionDetail.class);
//                        editpro.putExtra("position_data", (Serializable) dataList);//传递兼职数据
//                        editpro.putExtra("p_id", data.getP_id());//传递当前选中的兼职的id
//                        //获取该position在list中的位置（下标），传过去
//                        editpro.putExtra("pos", pos);//传递当前选中的报名的下标
//                        context.startActivity(editpro);
//                    });
//                    headerViewHolder.detail_icon.setOnClickListener(view -> {
//                        Toast toast = Toast.makeText(context, "欢迎查看兼职详情", Toast.LENGTH_SHORT);
//                        toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
//                        toast.show();
//
//                        //跳转到兼职详细界面
//                        Intent editpro = new Intent(context, PositionDetail.class);
//                        editpro.putExtra("position_data", (Serializable) dataList);//传递兼职数据
//                        editpro.putExtra("p_id", data.getP_id());//传递当前选中的兼职的id
//                        //获取该position在list中的位置（下标），传过去
//                        editpro.putExtra("pos", pos);//传递当前选中的报名的下标
//                        context.startActivity(editpro);
//                    });
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    //重写getItemCount方法
//    @Override
//    public int getItemCount() {
//        return dataList.size();
//    }
//
//    //重写getItemViewType方法
//    @Override
//    public int getItemViewType(int position) {
//        if (position >= 0) { //第一个item就是背景
//            return HEADER_VIEW;
//        } else { //其余是详细信息的各个字段
//            return -1;
//        }
//    }
//}