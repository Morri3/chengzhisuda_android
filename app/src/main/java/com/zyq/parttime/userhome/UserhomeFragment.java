package com.zyq.parttime.userhome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zyq.parttime.R;
import com.zyq.parttime.userhome.intented.IntentedManage;
import com.zyq.parttime.userhome.resume.ResumesManage;

public class UserhomeFragment extends Fragment {
    private Context context;//上下文

    public UserhomeFragment() {}

    public UserhomeFragment(String context) {
//        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userhome, container, false);
        context = this.getActivity();

        TextView name=view.findViewById(R.id.username);
        ImageView head=view.findViewById(R.id.head);
        ImageView right1=view.findViewById(R.id.right1);
        ImageView right2=view.findViewById(R.id.right2);
        ImageView right3=view.findViewById(R.id.right3);
        ImageView right4=view.findViewById(R.id.right4);

        right1.setOnClickListener(v->{
            Intent i = new Intent();
            i.setClass(context, UserInfo.class);
            startActivity(i);
        });
        right2.setOnClickListener(v->{
            Intent i = new Intent();
            i.setClass(context, ResumesManage.class);
            startActivity(i);
        });
        right3.setOnClickListener(v->{
            Intent i = new Intent();
            i.setClass(context, IntentedManage.class);
            startActivity(i);
        });
        right4.setOnClickListener(v->{
//            Intent i = new Intent();
//            i.setClass(context, IntentedManage.class);
//            startActivity(i);
        });


        return view;
    }
}
