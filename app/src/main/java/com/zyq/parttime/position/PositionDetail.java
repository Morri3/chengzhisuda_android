package com.zyq.parttime.position;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zyq.parttime.HomeActivity;
import com.zyq.parttime.R;
import com.zyq.parttime.form.Position;
import com.zyq.parttime.home.HomeAdapter;
import com.zyq.parttime.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PositionDetail extends AppCompatActivity {
    private Context context;//上下文
    private List<Position> list = new ArrayList();//存放recycleview的数据
    private RecyclerView rv; //RecyclerView布局
    private PositionDetailAdapter positionDetailAdapter;//适配器
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//取消默认顶部标题栏
        setContentView(R.layout.position_detail);
        context = this.getBaseContext();
        Log.i("context", context.toString());

        //获取传来的数据
        Intent editpro = getIntent();
        int pos = editpro.getIntExtra("pos", -1);
        list = (List<Position>) editpro.getSerializableExtra("position_data");//获得传来的列表

        Log.i("pos", pos + "");
        Log.i("list", list.toString());

        //适配器的定义与设置
        //配置布局管理器、分割线、适配器
        rv = (RecyclerView) findViewById(R.id.rv2);
        //第一步：设置布局管理器
        rv.setLayoutManager(new LinearLayoutManager(this));
        //第二步：设置适配器
        positionDetailAdapter = new PositionDetailAdapter(this, list.get(pos-1));//传入当前的item
        rv.setAdapter(positionDetailAdapter);

        //返回
        back=findViewById(R.id.back);
        back.setOnClickListener(v->{
            Log.i("back","返回到上一页");
            //跳转到首页
            Intent i = new Intent();
            i.setClass(PositionDetail.this, HomeActivity.class);
            //一定要指定是第几个pager，这里填写1
            i.putExtra("id", 1);
            startActivity(i);
        });
    }
}
