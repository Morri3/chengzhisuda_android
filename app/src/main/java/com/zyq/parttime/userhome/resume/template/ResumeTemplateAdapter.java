package com.zyq.parttime.userhome.resume.template;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zyq.parttime.R;
import com.zyq.parttime.form.Position;
import com.zyq.parttime.position.PositionDetail;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResumeTemplateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Serializable {
    private static final int HEADER_VIEW = 0; //头部
    private Context context;
    private List<TextView> other;//存放当前tip外的其他tip

    public ResumeTemplateAdapter(Context context) {
        this.context = context;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View view) {
            super(view);
            view.setOnClickListener(v -> {
            });
        }

        //封装数据
        private ImageView head;
        private TextView username, username2;
        private TextView birth;
        private TextView telephone;
        private TextView exp;
        private TextView emails;
        private TextView current_area;

        private TextView intention_title, intention;
        private TextView education_title, education_time, education_subtitle, education_content_1, education_content_2;
        private TextView project_title, project_time, project_subtitle, project_content_1, project_content_2, project_time_2, project_subtitle_2, project_content_3, project_content_4, project_content_5;
        private TextView profession_title, profession_content, profession_content_2, profession_content_3;
        private TextView campus_title, campus_time, campus_subtitle, campus_content;

        private ImageView tip_icon_1, tip_icon_2, tip_icon_3, tip_icon_4, tip_icon_5, tip_icon_6, tip_icon_7, tip_icon_8, tip_icon_9;
        private TextView tip_1, tip_2, tip_3, tip_4, tip_5, tip_6, tip_7, tip_8, tip_9;

        //getter、setter方法
        public ImageView getTip_icon_9() {
            return tip_icon_9;
        }

        public void setTip_icon_9(ImageView tip_icon_9) {
            this.tip_icon_9 = tip_icon_9;
        }

        public TextView getTip_9() {
            return tip_9;
        }

        public void setTip_9(TextView tip_9) {
            this.tip_9 = tip_9;
        }

        public ImageView getHead() {
            return head;
        }

        public void setHead(ImageView head) {
            this.head = head;
        }

        public TextView getUsername() {
            return username;
        }

        public void setUsername(TextView username) {
            this.username = username;
        }

        public TextView getUsername2() {
            return username2;
        }

        public void setUsername2(TextView username2) {
            this.username2 = username2;
        }

        public TextView getBirth() {
            return birth;
        }

        public void setBirth(TextView birth) {
            this.birth = birth;
        }

        public TextView getTelephone() {
            return telephone;
        }

        public void setTelephone(TextView telephone) {
            this.telephone = telephone;
        }

        public TextView getExp() {
            return exp;
        }

        public void setExp(TextView exp) {
            this.exp = exp;
        }

        public TextView getEmails() {
            return emails;
        }

        public void setEmails(TextView emails) {
            this.emails = emails;
        }

        public TextView getCurrent_area() {
            return current_area;
        }

        public void setCurrent_area(TextView current_area) {
            this.current_area = current_area;
        }

        public TextView getIntention_title() {
            return intention_title;
        }

        public void setIntention_title(TextView intention_title) {
            this.intention_title = intention_title;
        }

        public TextView getIntention() {
            return intention;
        }

        public void setIntention(TextView intention) {
            this.intention = intention;
        }

        public TextView getEducation_title() {
            return education_title;
        }

        public void setEducation_title(TextView education_title) {
            this.education_title = education_title;
        }

        public TextView getEducation_time() {
            return education_time;
        }

        public void setEducation_time(TextView education_time) {
            this.education_time = education_time;
        }

        public TextView getEducation_subtitle() {
            return education_subtitle;
        }

        public void setEducation_subtitle(TextView education_subtitle) {
            this.education_subtitle = education_subtitle;
        }

        public TextView getEducation_content_1() {
            return education_content_1;
        }

        public void setEducation_content_1(TextView education_content_1) {
            this.education_content_1 = education_content_1;
        }

        public TextView getEducation_content_2() {
            return education_content_2;
        }

        public void setEducation_content_2(TextView education_content_2) {
            this.education_content_2 = education_content_2;
        }

        public TextView getProject_title() {
            return project_title;
        }

        public void setProject_title(TextView project_title) {
            this.project_title = project_title;
        }

        public TextView getProject_time() {
            return project_time;
        }

        public void setProject_time(TextView project_time) {
            this.project_time = project_time;
        }

        public TextView getProject_subtitle() {
            return project_subtitle;
        }

        public void setProject_subtitle(TextView project_subtitle) {
            this.project_subtitle = project_subtitle;
        }

        public TextView getProject_content_1() {
            return project_content_1;
        }

        public void setProject_content_1(TextView project_content_1) {
            this.project_content_1 = project_content_1;
        }

        public TextView getProject_content_2() {
            return project_content_2;
        }

        public void setProject_content_2(TextView project_content_2) {
            this.project_content_2 = project_content_2;
        }

        public TextView getProject_time_2() {
            return project_time_2;
        }

        public void setProject_time_2(TextView project_time_2) {
            this.project_time_2 = project_time_2;
        }

        public TextView getProject_subtitle_2() {
            return project_subtitle_2;
        }

        public void setProject_subtitle_2(TextView project_subtitle_2) {
            this.project_subtitle_2 = project_subtitle_2;
        }

        public TextView getProject_content_3() {
            return project_content_3;
        }

        public void setProject_content_3(TextView project_content_3) {
            this.project_content_3 = project_content_3;
        }

        public TextView getProject_content_4() {
            return project_content_4;
        }

        public void setProject_content_4(TextView project_content_4) {
            this.project_content_4 = project_content_4;
        }

        public TextView getProject_content_5() {
            return project_content_5;
        }

        public void setProject_content_5(TextView project_content_5) {
            this.project_content_5 = project_content_5;
        }

        public TextView getProfession_title() {
            return profession_title;
        }

        public void setProfession_title(TextView profession_title) {
            this.profession_title = profession_title;
        }

        public TextView getProfession_content() {
            return profession_content;
        }

        public void setProfession_content(TextView profession_content) {
            this.profession_content = profession_content;
        }

        public TextView getProfession_content_2() {
            return profession_content_2;
        }

        public void setProfession_content_2(TextView profession_content_2) {
            this.profession_content_2 = profession_content_2;
        }

        public TextView getProfession_content_3() {
            return profession_content_3;
        }

        public void setProfession_content_3(TextView profession_content_3) {
            this.profession_content_3 = profession_content_3;
        }

        public TextView getCampus_title() {
            return campus_title;
        }

        public void setCampus_title(TextView campus_title) {
            this.campus_title = campus_title;
        }

        public TextView getCampus_time() {
            return campus_time;
        }

        public void setCampus_time(TextView campus_time) {
            this.campus_time = campus_time;
        }

        public TextView getCampus_subtitle() {
            return campus_subtitle;
        }

        public void setCampus_subtitle(TextView campus_subtitle) {
            this.campus_subtitle = campus_subtitle;
        }

        public TextView getCampus_content() {
            return campus_content;
        }

        public void setCampus_content(TextView campus_content) {
            this.campus_content = campus_content;
        }

        public ImageView getTip_icon_1() {
            return tip_icon_1;
        }

        public void setTip_icon_1(ImageView tip_icon_1) {
            this.tip_icon_1 = tip_icon_1;
        }

        public ImageView getTip_icon_2() {
            return tip_icon_2;
        }

        public void setTip_icon_2(ImageView tip_icon_2) {
            this.tip_icon_2 = tip_icon_2;
        }

        public ImageView getTip_icon_3() {
            return tip_icon_3;
        }

        public void setTip_icon_3(ImageView tip_icon_3) {
            this.tip_icon_3 = tip_icon_3;
        }

        public ImageView getTip_icon_4() {
            return tip_icon_4;
        }

        public void setTip_icon_4(ImageView tip_icon_4) {
            this.tip_icon_4 = tip_icon_4;
        }

        public ImageView getTip_icon_5() {
            return tip_icon_5;
        }

        public void setTip_icon_5(ImageView tip_icon_5) {
            this.tip_icon_5 = tip_icon_5;
        }

        public ImageView getTip_icon_6() {
            return tip_icon_6;
        }

        public void setTip_icon_6(ImageView tip_icon_6) {
            this.tip_icon_6 = tip_icon_6;
        }

        public ImageView getTip_icon_7() {
            return tip_icon_7;
        }

        public void setTip_icon_7(ImageView tip_icon_7) {
            this.tip_icon_7 = tip_icon_7;
        }

        public ImageView getTip_icon_8() {
            return tip_icon_8;
        }

        public void setTip_icon_8(ImageView tip_icon_8) {
            this.tip_icon_8 = tip_icon_8;
        }

        public TextView getTip_1() {
            return tip_1;
        }

        public void setTip_1(TextView tip_1) {
            this.tip_1 = tip_1;
        }

        public TextView getTip_2() {
            return tip_2;
        }

        public void setTip_2(TextView tip_2) {
            this.tip_2 = tip_2;
        }

        public TextView getTip_3() {
            return tip_3;
        }

        public void setTip_3(TextView tip_3) {
            this.tip_3 = tip_3;
        }

        public TextView getTip_4() {
            return tip_4;
        }

        public void setTip_4(TextView tip_4) {
            this.tip_4 = tip_4;
        }

        public TextView getTip_5() {
            return tip_5;
        }

        public void setTip_5(TextView tip_5) {
            this.tip_5 = tip_5;
        }

        public TextView getTip_6() {
            return tip_6;
        }

        public void setTip_6(TextView tip_6) {
            this.tip_6 = tip_6;
        }

        public TextView getTip_7() {
            return tip_7;
        }

        public void setTip_7(TextView tip_7) {
            this.tip_7 = tip_7;
        }

        public TextView getTip_8() {
            return tip_8;
        }

        public void setTip_8(TextView tip_8) {
            this.tip_8 = tip_8;
        }
    }

    //创建头部viewhodler
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER_VIEW) {
            View headerView = LayoutInflater.from(context).inflate(R.layout.template_item, null);
            HeaderViewHolder headerHolder = new HeaderViewHolder(headerView);
            //接下来是控件的引用声明
            headerHolder.head = headerView.findViewById(R.id.head);
            headerHolder.username = headerView.findViewById(R.id.username);
            headerHolder.username2 = headerView.findViewById(R.id.username2);
            headerHolder.birth = headerView.findViewById(R.id.birth);
            headerHolder.telephone = headerView.findViewById(R.id.telephone);
            headerHolder.exp = headerView.findViewById(R.id.exp);
            headerHolder.emails = headerView.findViewById(R.id.emails);
            headerHolder.current_area = headerView.findViewById(R.id.current_area);

            headerHolder.intention_title = headerView.findViewById(R.id.intention_title);
            headerHolder.intention = headerView.findViewById(R.id.intention);

            headerHolder.education_title = headerView.findViewById(R.id.education_title);
            headerHolder.education_time = headerView.findViewById(R.id.education_time);
            headerHolder.education_subtitle = headerView.findViewById(R.id.education_subtitle);
            headerHolder.education_content_1 = headerView.findViewById(R.id.education_content_1);
            headerHolder.education_content_2 = headerView.findViewById(R.id.education_content_2);

            headerHolder.project_title = headerView.findViewById(R.id.project_title);
            headerHolder.project_time = headerView.findViewById(R.id.project_time);
            headerHolder.project_subtitle = headerView.findViewById(R.id.project_subtitle);
            headerHolder.project_content_1 = headerView.findViewById(R.id.project_content_1);
            headerHolder.project_content_2 = headerView.findViewById(R.id.project_content_2);
            headerHolder.project_time_2 = headerView.findViewById(R.id.project_time_2);
            headerHolder.project_subtitle_2 = headerView.findViewById(R.id.project_subtitle_2);
            headerHolder.project_content_3 = headerView.findViewById(R.id.project_content_3);
            headerHolder.project_content_4 = headerView.findViewById(R.id.project_content_4);
            headerHolder.project_content_5 = headerView.findViewById(R.id.project_content_5);

            headerHolder.profession_title = headerView.findViewById(R.id.profession_title);
            headerHolder.profession_content = headerView.findViewById(R.id.profession_content);
            headerHolder.profession_content_2 = headerView.findViewById(R.id.profession_content_2);
            headerHolder.profession_content_3 = headerView.findViewById(R.id.profession_content_3);

            headerHolder.campus_title = headerView.findViewById(R.id.campus_title);
            headerHolder.campus_time = headerView.findViewById(R.id.campus_time);
            headerHolder.campus_subtitle = headerView.findViewById(R.id.campus_subtitle);
            headerHolder.campus_content = headerView.findViewById(R.id.campus_content);

            headerHolder.tip_icon_1 = headerView.findViewById(R.id.tip_icon_1);
            headerHolder.tip_icon_2 = headerView.findViewById(R.id.tip_icon_2);
            headerHolder.tip_icon_3 = headerView.findViewById(R.id.tip_icon_3);
            headerHolder.tip_icon_4 = headerView.findViewById(R.id.tip_icon_4);
            headerHolder.tip_icon_5 = headerView.findViewById(R.id.tip_icon_5);
            headerHolder.tip_icon_6 = headerView.findViewById(R.id.tip_icon_6);
            headerHolder.tip_icon_7 = headerView.findViewById(R.id.tip_icon_7);
            headerHolder.tip_icon_8 = headerView.findViewById(R.id.tip_icon_8);
            headerHolder.tip_icon_9 = headerView.findViewById(R.id.tip_icon_9);
            headerHolder.tip_1 = headerView.findViewById(R.id.tip_1);
            headerHolder.tip_2 = headerView.findViewById(R.id.tip_2);
            headerHolder.tip_3 = headerView.findViewById(R.id.tip_3);
            headerHolder.tip_4 = headerView.findViewById(R.id.tip_4);
            headerHolder.tip_5 = headerView.findViewById(R.id.tip_5);
            headerHolder.tip_6 = headerView.findViewById(R.id.tip_6);
            headerHolder.tip_7 = headerView.findViewById(R.id.tip_7);
            headerHolder.tip_8 = headerView.findViewById(R.id.tip_8);
            headerHolder.tip_9 = headerView.findViewById(R.id.tip_9);

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

                //初始时默认第一条高亮
                headerViewHolder.tip_1.setTextColor((context.getResources()).getColor(R.color.text_black_color));

                //点击指定提示进行高亮
                headerViewHolder.tip_1.setOnClickListener(v -> {
                    List<TextView> list = new ArrayList<>();
                    list.add(headerViewHolder.tip_1);
                    list.add(headerViewHolder.tip_2);
                    list.add(headerViewHolder.tip_3);
                    list.add(headerViewHolder.tip_4);
                    list.add(headerViewHolder.tip_5);
                    list.add(headerViewHolder.tip_6);
                    list.add(headerViewHolder.tip_7);
                    list.add(headerViewHolder.tip_8);
                    list.add(headerViewHolder.tip_9);

                    //1.当前点击的控件高亮
                    headerViewHolder.tip_1.setTextColor((context.getResources()).getColor(R.color.text_black_color));

                    //2.other列表存放除当前点击的控件外的其他tip控件
                    other = list;
                    other.remove(0);

                    //3.其他控件取消高亮
                    for (TextView item : other) {
                        item.setTextColor((context.getResources()).getColor(R.color.text_deep_grey_color));
                    }
                });

                headerViewHolder.tip_2.setOnClickListener(v -> {
                    List<TextView> list = new ArrayList<>();
                    list.add(headerViewHolder.tip_1);
                    list.add(headerViewHolder.tip_2);
                    list.add(headerViewHolder.tip_3);
                    list.add(headerViewHolder.tip_4);
                    list.add(headerViewHolder.tip_5);
                    list.add(headerViewHolder.tip_6);
                    list.add(headerViewHolder.tip_7);
                    list.add(headerViewHolder.tip_8);
                    list.add(headerViewHolder.tip_9);

                    //1.当前点击的控件高亮
                    headerViewHolder.tip_2.setTextColor((context.getResources()).getColor(R.color.text_black_color));

                    //2.other列表存放除当前点击的控件外的其他tip控件
                    other = list;
                    other.remove(1);

                    //3.其他控件取消高亮
                    for (TextView item : other) {
                        item.setTextColor((context.getResources()).getColor(R.color.text_deep_grey_color));
                    }
                });

                headerViewHolder.tip_3.setOnClickListener(v -> {
                    List<TextView> list = new ArrayList<>();
                    list.add(headerViewHolder.tip_1);
                    list.add(headerViewHolder.tip_2);
                    list.add(headerViewHolder.tip_3);
                    list.add(headerViewHolder.tip_4);
                    list.add(headerViewHolder.tip_5);
                    list.add(headerViewHolder.tip_6);
                    list.add(headerViewHolder.tip_7);
                    list.add(headerViewHolder.tip_8);
                    list.add(headerViewHolder.tip_9);

                    //1.当前点击的控件高亮
                    headerViewHolder.tip_3.setTextColor((context.getResources()).getColor(R.color.text_black_color));

                    //2.other列表存放除当前点击的控件外的其他tip控件
                    other = list;
                    other.remove(2);

                    //3.其他控件取消高亮
                    for (TextView item : other) {
                        item.setTextColor((context.getResources()).getColor(R.color.text_deep_grey_color));
                    }
                });

                headerViewHolder.tip_4.setOnClickListener(v -> {
                    List<TextView> list = new ArrayList<>();
                    list.add(headerViewHolder.tip_1);
                    list.add(headerViewHolder.tip_2);
                    list.add(headerViewHolder.tip_3);
                    list.add(headerViewHolder.tip_4);
                    list.add(headerViewHolder.tip_5);
                    list.add(headerViewHolder.tip_6);
                    list.add(headerViewHolder.tip_7);
                    list.add(headerViewHolder.tip_8);
                    list.add(headerViewHolder.tip_9);

                    //1.当前点击的控件高亮
                    headerViewHolder.tip_4.setTextColor((context.getResources()).getColor(R.color.text_black_color));

                    //2.other列表存放除当前点击的控件外的其他tip控件
                    other = list;
                    other.remove(3);

                    //3.其他控件取消高亮
                    for (TextView item : other) {
                        item.setTextColor((context.getResources()).getColor(R.color.text_deep_grey_color));
                    }
                });

                headerViewHolder.tip_5.setOnClickListener(v -> {
                    List<TextView> list = new ArrayList<>();
                    list.add(headerViewHolder.tip_1);
                    list.add(headerViewHolder.tip_2);
                    list.add(headerViewHolder.tip_3);
                    list.add(headerViewHolder.tip_4);
                    list.add(headerViewHolder.tip_5);
                    list.add(headerViewHolder.tip_6);
                    list.add(headerViewHolder.tip_7);
                    list.add(headerViewHolder.tip_8);
                    list.add(headerViewHolder.tip_9);

                    //1.当前点击的控件高亮
                    headerViewHolder.tip_5.setTextColor((context.getResources()).getColor(R.color.text_black_color));

                    //2.other列表存放除当前点击的控件外的其他tip控件
                    other = list;
                    other.remove(4);

                    //3.其他控件取消高亮
                    for (TextView item : other) {
                        item.setTextColor((context.getResources()).getColor(R.color.text_deep_grey_color));
                    }
                });

                headerViewHolder.tip_6.setOnClickListener(v -> {
                    List<TextView> list = new ArrayList<>();
                    list.add(headerViewHolder.tip_1);
                    list.add(headerViewHolder.tip_2);
                    list.add(headerViewHolder.tip_3);
                    list.add(headerViewHolder.tip_4);
                    list.add(headerViewHolder.tip_5);
                    list.add(headerViewHolder.tip_6);
                    list.add(headerViewHolder.tip_7);
                    list.add(headerViewHolder.tip_8);
                    list.add(headerViewHolder.tip_9);

                    //1.当前点击的控件高亮
                    headerViewHolder.tip_6.setTextColor((context.getResources()).getColor(R.color.text_black_color));

                    //2.other列表存放除当前点击的控件外的其他tip控件
                    other = list;
                    other.remove(5);

                    //3.其他控件取消高亮
                    for (TextView item : other) {
                        item.setTextColor((context.getResources()).getColor(R.color.text_deep_grey_color));
                    }
                });

                headerViewHolder.tip_7.setOnClickListener(v -> {
                    List<TextView> list = new ArrayList<>();
                    list.add(headerViewHolder.tip_1);
                    list.add(headerViewHolder.tip_2);
                    list.add(headerViewHolder.tip_3);
                    list.add(headerViewHolder.tip_4);
                    list.add(headerViewHolder.tip_5);
                    list.add(headerViewHolder.tip_6);
                    list.add(headerViewHolder.tip_7);
                    list.add(headerViewHolder.tip_8);
                    list.add(headerViewHolder.tip_9);

                    //1.当前点击的控件高亮
                    headerViewHolder.tip_7.setTextColor((context.getResources()).getColor(R.color.text_black_color));

                    //2.other列表存放除当前点击的控件外的其他tip控件
                    other = list;
                    other.remove(6);

                    //3.其他控件取消高亮
                    for (TextView item : other) {
                        item.setTextColor((context.getResources()).getColor(R.color.text_deep_grey_color));
                    }
                });

                headerViewHolder.tip_8.setOnClickListener(v -> {
                    List<TextView> list = new ArrayList<>();
                    list.add(headerViewHolder.tip_1);
                    list.add(headerViewHolder.tip_2);
                    list.add(headerViewHolder.tip_3);
                    list.add(headerViewHolder.tip_4);
                    list.add(headerViewHolder.tip_5);
                    list.add(headerViewHolder.tip_6);
                    list.add(headerViewHolder.tip_7);
                    list.add(headerViewHolder.tip_8);
                    list.add(headerViewHolder.tip_9);

                    //1.当前点击的控件高亮
                    headerViewHolder.tip_8.setTextColor((context.getResources()).getColor(R.color.text_black_color));

                    //2.other列表存放除当前点击的控件外的其他tip控件
                    other = list;
                    other.remove(7);

                    //3.其他控件取消高亮
                    for (TextView item : other) {
                        item.setTextColor((context.getResources()).getColor(R.color.text_deep_grey_color));
                    }
                });

                headerViewHolder.tip_9.setOnClickListener(v -> {
                    List<TextView> list = new ArrayList<>();
                    list.add(headerViewHolder.tip_1);
                    list.add(headerViewHolder.tip_2);
                    list.add(headerViewHolder.tip_3);
                    list.add(headerViewHolder.tip_4);
                    list.add(headerViewHolder.tip_5);
                    list.add(headerViewHolder.tip_6);
                    list.add(headerViewHolder.tip_7);
                    list.add(headerViewHolder.tip_8);
                    list.add(headerViewHolder.tip_9);

                    //1.当前点击的控件高亮
                    headerViewHolder.tip_9.setTextColor((context.getResources()).getColor(R.color.text_black_color));

                    //2.other列表存放除当前点击的控件外的其他tip控件
                    other = list;
                    other.remove(8);

                    //3.其他控件取消高亮
                    for (TextView item : other) {
                        item.setTextColor((context.getResources()).getColor(R.color.text_deep_grey_color));
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //重写getItemCount方法，这里永远是1个元素
    @Override
    public int getItemCount() {
        return 1;
    }

    //重写getItemViewType方法
    @Override
    public int getItemViewType(int position) {
        if (position >= 0) {
            return HEADER_VIEW;
        } else {
            return -1;
        }
    }
}
