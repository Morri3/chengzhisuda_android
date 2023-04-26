package com.zyq.parttime.userhome.resume;

import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.zyq.parttime.R;
import com.zyq.parttime.form.EditCampus;
import com.zyq.parttime.form.EditProject;
import com.zyq.parttime.form.EditProjectDto;
import com.zyq.parttime.form.EditSkills;
import com.zyq.parttime.sp.AddDetail;
import com.zyq.parttime.sp.DeleteDetail;

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

public class ResumeSkillsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<EditSkills> list;
    private int isSave;

    public ResumeSkillsAdapter(Context context, List<EditSkills> list, int isSave) {
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
        private TextView content;

        public TextView getContent() {
            return content;
        }

        public void setContent(TextView content) {
            this.content = content;
        }

        //编辑时
        private EditText content2;
        private ImageView delete2;
        private ImageView add2;
        private ImageView save2;

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
        View headerView = LayoutInflater.from(context).inflate(R.layout.campus_show_skills_item, parent, false);
        HeaderViewHolder headerHolder = new HeaderViewHolder(headerView);
        headerHolder.content = headerView.findViewById(R.id.content);

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
                EditSkills item = list.get(pos);
                Log.i("isSave", isSave + "");

                //设置焦点，实现走马灯
                headerViewHolder.content.requestFocus();

                if (isSave == 0) {
                    //隐藏编辑的控件
                    headerViewHolder.content2.setVisibility(View.INVISIBLE);
                    headerViewHolder.add2.setVisibility(View.INVISIBLE);
                    headerViewHolder.delete2.setVisibility(View.INVISIBLE);
                    headerViewHolder.save2.setVisibility(View.INVISIBLE);
                    headerViewHolder.content.setVisibility(View.VISIBLE);

                    //list的数据set到textview中
                    headerViewHolder.content.setText(item.getContent());
                } else if (isSave == 1) {
                    //隐藏编辑的控件
                    headerViewHolder.content2.setVisibility(View.VISIBLE);
                    headerViewHolder.add2.setVisibility(View.VISIBLE);
                    headerViewHolder.delete2.setVisibility(View.VISIBLE);
                    headerViewHolder.save2.setVisibility(View.VISIBLE);
                    headerViewHolder.content.setVisibility(View.INVISIBLE);

                    //list的数据set到textview中
                    headerViewHolder.content2.setText(item.getContent());

                    headerViewHolder.add2.setOnClickListener(v -> {
                        //添加一条空的
                        EditSkills i = new EditSkills();
                        i.setR_id(item.getR_id());
                        i.setTelephone(item.getTelephone());
                        i.setContent("请输入内容");
                        list.add(list.size(), i);

                        //添加动画
                        notifyItemInserted(list.size());
                    });

                    headerViewHolder.delete2.setOnClickListener(v -> {
                        int thePos = pos;
                        if (list.size() == 1) {//只有一条数据
                            Toast toast = Toast.makeText(context, "最少需要一条数据~", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 250);
                            toast.show();

                            Log.i("error", "最少需要一条数据~");
                        } else {
                            //删除自带默认动画
                            list.remove(thePos);

                            //调api TODO
                            new Thread(() -> {
                                try {
                                    OkHttpClient client = new OkHttpClient();//创建Okhttp客户端

                                    //dto
                                    DeleteDetail dto = new DeleteDetail();
                                    dto.setRd_id(item.getRd_id());
                                    dto.setTelephone(item.getTelephone());
                                    String json = JSON.toJSONString(dto);//dto转json
                                    Request request = new Request.Builder()
                                            .url("http://114.55.239.213:8087/users/resumes/delete_detail")
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
                                                    Log.i("删除", "删除成功！");
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
                        }
                    });

                    headerViewHolder.save2.setOnClickListener(v -> {
                        //把修改的内容更新到list中
                        item.setContent(headerViewHolder.content2.getText().toString());

                        //UI界面添加该元素，并刷新适配器
                        saveData(pos, item);
                        Log.i("b", list.toString());

                        //调api，根据id 修改数据库中的数据
                        new Thread(() -> {
                            try {
                                OkHttpClient client = new OkHttpClient();//创建Okhttp客户端

                                //根据initial变量的值，判断是 原有记录 还是 新增记录
                                int initial = item.getInitial();

                                if (initial == 1) {
                                    //原有记录

                                    //dto
                                    EditProjectDto dto = new EditProjectDto();
                                    dto.setRd_id(item.getRd_id());
                                    dto.setTelephone(item.getTelephone());
                                    dto.setContent(item.getContent());

                                    String json = JSON.toJSONString(dto);//dto转json
                                    Request request = new Request.Builder()
                                            .url("http://114.55.239.213:8087/users/resumes/edit_skills")
                                            .post(RequestBody.create(MediaType.parse("application/json"), json))
                                            .build();//创建Http请求
                                    client.newBuilder()
                                            .connectTimeout(20, TimeUnit.SECONDS)
                                            .readTimeout(25, TimeUnit.SECONDS)
                                            .writeTimeout(30, TimeUnit.SECONDS)
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
                                    dto.setContent(item.getContent());
                                    dto.setCategory("专业技能");
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

    public void saveData(int position, EditSkills editSkills) {
        list.set(position, editSkills);
        notifyDataSetChanged();//刷新
    }
}