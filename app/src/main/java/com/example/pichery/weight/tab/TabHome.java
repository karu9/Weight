package com.example.pichery.weight.tab;

/**
 * Created by pichery on 29/11/15.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pichery.weight.R;
import com.example.pichery.weight.util.DBUtils;

public class TabHome extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.tab_home, container, false);
        TextView tv = (TextView) view.getChildAt(0);
        tv.setText(new DBUtils(getContext()).loadProfile().getName());
        return view;
    }
}