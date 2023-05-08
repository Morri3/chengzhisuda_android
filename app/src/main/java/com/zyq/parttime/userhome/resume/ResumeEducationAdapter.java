package com.zyq.parttime.userhome.resume;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.zyq.parttime.R;
import com.zyq.parttime.form.AddDetail;
import com.zyq.parttime.form.EditEducation;
import com.zyq.parttime.form.EditEducationDto;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ResumeEducationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<EditEducation> list;
    private int isSave;

    public ResumeEducationAdapter(Context context, List<EditEducation> list, int isSave) {
        this.context = context;
        this.list = list;
        this.isSave = isSave;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View view) {
            super(view);
            view.setOnClickListener(v -> {
            });
        }

        //显示时
        private TextView title;
        private TextView date;
        private TextView content;

        public TextView getTitle() {
            return title;
        }

        public void setTitle(TextView title) {
            this.title = title;
        }

        public TextView getDate() {
            return date;
        }

        public void setDate(TextView date) {
            this.date = date;
        }

        public TextView getContent() {
            return content;
        }

        public void setContent(TextView content) {
            this.content = content;
        }

        //编辑时
        private EditText title2;
        private EditText date2;
        private EditText content2;
        private ImageView delete2;
        private ImageView add2;
        private ImageView save2;

        public EditText getTitle2() {
            return title2;
        }

        public void setTitle2(EditText title2) {
            this.title2 = title2;
        }

        public EditText getDate2() {
            return date2;
        }

        public void setDate2(EditText date2) {
            this.date2 = date2;
        }

        public EditText getContent2() {
            return content2;
        }

        public void setContent2(EditText content2) {
            this.content2 = content2;
        }

        public ImageView getDelete2() {
            return delete2;
        }

        public void setDelete2(ImageView delete2) {
            this.delete2 = delete2;
        }

        public ImageView getAdd2() {
            return add2;
        }

        public void setAdd2(ImageView add2) {
            this.add2 = add2;
        }

        public ImageView getSave2() {
            return save2;
        }

        public void setSave2(ImageView save2) {
            this.save2 = save2;
        }
    }

    @Override
    public HeaderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View headerView = LayoutInflater.from(context).inflate(R.layout.resumes_show_item, parent, false);
        HeaderViewHolder headerHolder = new HeaderViewHolder(headerView);
        headerHolder.title = headerView.findViewById(R.id.title);
        headerHolder.date = headerView.findViewById(R.id.date);
        headerHolder.content = headerView.findViewById(R.id.content);

        headerHolder.title2 = headerView.findViewById(R.id.title_edit);
        headerHolder.date2 = headerView.findViewById(R.id.date_edit);
        headerHolder.content2 = headerView.findViewById(R.id.content_edit);
        headerHolder.delete2 = headerView.findViewById(R.id.delete_edit);
        headerHolder.add2 = headerView.findViewById(R.id.add_edit);
        headerHolder.save2 = headerView.findViewById(R.id.save_edit);
        return headerHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int pos) {
        try {
            if (holder instanceof HeaderViewHolder) {//属于头部
                HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
                EditEducation item = list.get(pos);
                Log.i("isSave", isSave + "");

                //设置焦点，实现走马灯
                headerViewHolder.content.requestFocus();
                headerViewHolder.date.requestFocus();
                headerViewHolder.title.requestFocus();

                if (isSave == 0) {
                    //隐藏编辑的控件
                    headerViewHolder.title2.setVisibility(View.INVISIBLE);
                    headerViewHolder.date2.setVisibility(View.INVISIBLE);
                    headerViewHolder.content2.setVisibility(View.INVISIBLE);
                    headerViewHolder.add2.setVisibility(View.INVISIBLE);
                    headerViewHolder.delete2.setVisibility(View.INVISIBLE);
                    headerViewHolder.save2.setVisibility(View.INVISIBLE);
                    headerViewHolder.title.setVisibility(View.VISIBLE);
                    headerViewHolder.date.setVisibility(View.VISIBLE);
                    headerViewHolder.content.setVisibility(View.VISIBLE);

                    //list的数据set到textview中
                    headerViewHolder.title.setText(item.getTitle());
                    headerViewHolder.date.setText(item.getDate());
                    headerViewHolder.content.setText("\t\t\t\t" + item.getContent());
                } else if (isSave == 1) {
                    //隐藏编辑的控件
                    headerViewHolder.title2.setVisibility(View.VISIBLE);
                    headerViewHolder.date2.setVisibility(View.VISIBLE);
                    headerViewHolder.content2.setVisibility(View.VISIBLE);
                    headerViewHolder.add2.setVisibility(View.INVISIBLE);//不显示添加图标
                    headerViewHolder.delete2.setVisibility(View.INVISIBLE);//不显示删除图标
                    headerViewHolder.save2.setVisibility(View.VISIBLE);
                    headerViewHolder.title.setVisibility(View.INVISIBLE);
                    headerViewHolder.date.setVisibility(View.INVISIBLE);
                    headerViewHolder.content.setVisibility(View.INVISIBLE);

                    //list的数据set到textview中
                    headerViewHolder.title2.setText(item.getTitle());
                    headerViewHolder.date2.setText(item.getDate());
                    headerViewHolder.content2.setText(item.getContent());

                    //保存detail
                    headerViewHolder.save2.setOnClickListener(v -> {
                        //把修改的内容更新到list中
                        item.setTitle(headerViewHolder.title2.getText().toString());
                        item.setDate(headerViewHolder.date2.getText().toString());
                        item.setContent(headerViewHolder.content2.getText().toString());

                        //隐藏增删改图标
                        headerViewHolder.add2.setVisibility(View.INVISIBLE);
                        headerViewHolder.save2.setVisibility(View.INVISIBLE);
                        headerViewHolder.delete2.setVisibility(View.INVISIBLE);

                        //UI界面添加该元素，并刷新适配器
                        saveData(pos, item);

                        //调api，根据id 修改数据库中的数据
                        new Thread(() -> {
                            try {
                                OkHttpClient client = new OkHttpClient();//创建Okhttp客户端

                                //根据initial变量的值，判断是 原有记录 还是 新增记录
                                int initial = item.getInitial();

                                if (initial == 1) {
                                    //原有记录

                                    //dto
                                    EditEducationDto dto = new EditEducationDto();
                                    dto.setRd_id(item.getRd_id());
                                    dto.setTelephone(item.getTelephone());
                                    dto.setContent(item.getContent());
                                    dto.setTitle(item.getTitle());
                                    //时间处理
                                    String time = item.getDate();
                                    String[] a = time.split("-");
                                    dto.setStart_time(a[0]);
                                    dto.setEnd_time(a[1]);

                                    String json = JSON.toJSONString(dto);//dto转json
                                    Request request = new Request.Builder()
                                            .url("http://114.55.239.213:8087/users/resumes/edit_education")
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
                                            Log.i("error", "数据更新失败");
                                            e.printStackTrace();
                                        }

                                        @Override
                                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                            if (response.isSuccessful()) {//调用成功
                                                try {
                                                    JSONObject jsonObj = JSON.parseObject(response.body().string());
                                                    Log.i("data", jsonObj.getString("data"));
                                                    JSONObject data = JSON.parseObject(jsonObj.getString("data"));

                                                    //获取obj中的数据
                                                    Log.i("rd_id", data.getString("rd_id"));
                                                    Log.i("修改", "修改成功！");
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                            } else {//调用失败
                                                Log.i("error", response.toString());
                                            }
                                        }
                                    });
                                } else if (initial == 0) {
                                    //新增记录

                                    //dto
                                    AddDetail dto = new AddDetail();
                                    dto.setTelephone(item.getTelephone());
                                    dto.setR_id(item.getR_id());
                                    dto.setDate(item.getDate());//字符串类型
                                    dto.setTitle(item.getTitle());
                                    dto.setContent(item.getContent());
                                    dto.setCategory("教育背景");
                                    String json = JSON.toJSONString(dto);//dto转json

                                    Request request = new Request.Builder()
                                            .url("http://114.55.239.213:8087/users/resumes/add_detail")
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
                                            Log.i("error", "数据更新失败");
                                            e.printStackTrace();
                                        }

                                        @Override
                                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                            if (response.isSuccessful()) {//调用成功
                                                try {
                                                    JSONObject jsonObj = JSON.parseObject(response.body().string());
                                                    Log.i("data", jsonObj.getString("data"));
                                                    JSONObject data = JSON.parseObject(jsonObj.getString("data"));

                                                    //获取obj中的数据
                                                    Log.i("rd_id", data.getString("rd_id"));
                                                    Log.i("新增", "新增成功！");
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                            } else {//调用失败
                                                Log.i("error", response.toString());
                                            }
                                        }
                                    });
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }).start();//要start才会启动
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setIsSave(int value) {
        this.isSave = value;
        notifyDataSetChanged();//刷新
    }

    public void saveData(int position, EditEducation editEducation) {
        list.set(position, editEducation);
        notifyDataSetChanged();//刷新
    }
}