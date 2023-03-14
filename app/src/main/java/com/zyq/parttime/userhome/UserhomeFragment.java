package com.zyq.parttime.userhome;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zyq.parttime.R;

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

        return view;
    }
}
