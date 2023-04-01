package com.zyq.parttime.home;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.zyq.parttime.R;
import com.zyq.parttime.form.Position;
import com.zyq.parttime.position.PositionDetail;

import java.io.Serializable;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Serializable {
    private static final int HEADER_VIEW = 0; //头部

    private Context context;
    private List<Position> dataList; //数据列表

    public HomeAdapter(Context context, List<Position> dataList) {
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
        private TextView area;
        private TextView settlement;
        private TextView salary;
        private TextView work_time;
        private View divider;
        private TextView detail_text;
        private ImageView detail_icon;

        //getter、setter方法
        public TextView getName() {
            return name;
        }

        public void setName(TextView name) {
            this.name = name;
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

        public TextView getWork_time() {
            return work_time;
        }

        public void setWork_time(TextView work_time) {
            this.work_time = work_time;
        }

        public View getDivider() {
            return divider;
        }

        public void setDivider(View divider) {
            this.divider = divider;
        }

        public TextView getDetail_text() {
            return detail_text;
        }

        public void setDetail_text(TextView detail_text) {
            this.detail_text = detail_text;
        }

        public ImageView getDetail_icon() {
            return detail_icon;
        }

        public void setDetail_icon(ImageView detail_icon) {
            this.detail_icon = detail_icon;
        }
    }

    //创建头部viewhodler
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER_VIEW) {
            View headerView = LayoutInflater.from(context).inflate(R.layout.home_item, null);
            HeaderViewHolder headerHolder = new HeaderViewHolder(headerView);
            //接下来是控件的引用声明
            headerHolder.name = headerView.findViewById(R.id.name);
            headerHolder.area = headerView.findViewById(R.id.area);
            headerHolder.settlement = headerView.findViewById(R.id.settlement);
            headerHolder.salary = headerView.findViewById(R.id.salary);
            headerHolder.work_time = headerView.findViewById(R.id.work_time);
            headerHolder.divider = headerView.findViewById(R.id.divider);
            headerHolder.detail_text = headerView.findViewById(R.id.detail_text);
            headerHolder.detail_icon = headerView.findViewById(R.id.detail_icon);
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
                Position data = dataList.get(pos);//获取当前item的数据
                Log.i("data_adapter", data.toString());//test

                headerViewHolder.name.setText(data.getPosition_name());
                headerViewHolder.area.setText(data.getArea());
                headerViewHolder.settlement.setText(data.getSettlement());
                headerViewHolder.work_time.setText(data.getWork_time());
                headerViewHolder.salary.setText(data.getSalary());

                //最后一个不显示分割线
                if (pos == dataList.size() - 1) {
                    headerViewHolder.divider.setVisibility(View.INVISIBLE);
                }

                //查看详情按钮
                headerViewHolder.detail_text.setOnClickListener(view -> {
                    Toast toast = Toast.makeText(context, "点击了查看详情按钮", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                    toast.show();

                    //跳转到兼职详细界面
                    Intent editpro = new Intent(context, PositionDetail.class);
                    editpro.putExtra("position_data", (Serializable) dataList);//传递兼职数据
                    editpro.putExtra("pos", data.getP_id());//传递当前选中的兼职的id
                    //获取该position在list中的位置（下标），传过去  TODO

                    context.startActivity(editpro);
                });
                headerViewHolder.detail_icon.setOnClickListener(view -> {
                    Toast toast = Toast.makeText(context, "点击了查看详情按钮", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                    toast.show();

                    //跳转到兼职详细界面
                    Intent editpro = new Intent(context, PositionDetail.class);
                    editpro.putExtra("position_data", (Serializable) dataList);//传递兼职数据
                    editpro.putExtra("pos", data.getP_id());//传递当前选中的兼职的id
                    //获取该position在list中的位置（下标），传过去  TODO


                    context.startActivity(editpro);
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
