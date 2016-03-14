package com.example.pichery.weight.tab;

/**
 * Created by pichery on 29/11/15.
 */

import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pichery.weight.R;
import com.example.pichery.weight.model.Weight;
import com.example.pichery.weight.util.DBUtils;
import com.example.pichery.weight.util.DateUtil;

import java.util.List;

public class TabWeight extends Fragment{

    DBUtils dbUtil;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_weight, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbUtil =  new DBUtils(getActivity());
        setValuesFromDb();
        setListener();
    }

    private void setListener() {
        Button button = (Button) getView().findViewById(R.id.commitWeight);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = (EditText) getView().findViewById(R.id.weightText);
                if(!text.getText().toString().isEmpty()){
                    try{
                        String weightString = text.getText().toString();
                        weightString.replace(',', '.');
                        float weightval = Float.parseFloat(weightString);
                        if(weightval > 50){
                            dbUtil.execute(Weight.setWeightQuery(weightval));
                        }
                    }
                    catch (NumberFormatException e){

                    }
                }
                text.setText("", TextView.BufferType.EDITABLE);
                TabWeight.this.onStart();
            }
        });
    }

    private void setValuesFromDb() {
        LinearLayout layout = (LinearLayout) getView().findViewById(R.id.weightContainer);
        List<Weight> weights = dbUtil.loadWeights();
        if(weights != null && weights.size() > 0){
            Weight firstWeight = Weight.getFirstWeight(weights);
            for(Weight weight : weights){
                layout.addView(createDbInput(weight.getDate(), weight.getValue(), weight.getValue() - firstWeight.getValue()));
                layout.addView(createSeparator());
            }
        }
    }

    private LinearLayout createDbInput(String Date, float weight, float delta){
        LinearLayout ll = new LinearLayout(getActivity());
        ll.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(25, 0, 25, 0);
        ll.setLayoutParams(lp);
        ll.requestLayout();
        return ll;
    }

    private ImageView createSeparator(){
        ImageView iv = new ImageView(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(25, 4, 25, 0);
        iv.setLayoutParams(lp);
        iv.setPadding(0,10,0,0);
        return iv;
    }
}