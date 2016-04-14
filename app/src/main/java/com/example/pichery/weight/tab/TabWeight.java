package com.example.pichery.weight.tab;

/**
 * Created by pichery on 29/11/15.
 */

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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

import java.util.Collections;
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
        initialise();
    }

    private void initialise() {
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
                if (!text.getText().toString().isEmpty()) {
                    try {
                        String weightString = text.getText().toString();
                        weightString = weightString.replace(',', '.');
                        float weightval = Float.parseFloat(weightString);
                        if (weightval > 50) {
                            dbUtil.execute(Weight.setWeightQuery(weightString));
                            text.setText("", TextView.BufferType.EDITABLE);
                            refresh();
                        }
                    } catch (NumberFormatException e) {

                    }
                }
            }
        });
    }

    private void setValuesFromDb() {
        LinearLayout layout = (LinearLayout) getView().findViewById(R.id.weightContainer);
        List<Weight> weights = dbUtil.loadWeights();
        Collections.sort(weights);
        if(weights != null && weights.size() > 0){
            Weight firstWeight = weights.get(weights.size()-1);
            for(int i = 0; i < weights.size(); i++){
                Weight previousWeight;
                if (i < weights.size() - 1){
                    previousWeight = weights.get(i+1);
                }
                else{
                    previousWeight = weights.get(i);
                }
                layout.addView(createDbInput(weights.get(i).getDate(), weights.get(i).getValue(), Weight.getDeltaWeight(weights.get(i), previousWeight), Weight.getDeltaWeight(weights.get(i), firstWeight)));
                layout.addView(createSeparator());
            }
        }
    }

    private LinearLayout createDbInput(String date, String weight, String previousDelta, String firstDelta){
        int color = getResources().getColor(R.color.textColor);
        if(Float.parseFloat(previousDelta) > 0){
            color = getResources().getColor(R.color.red);
        }
        LinearLayout ll = new LinearLayout(getActivity());
        ll.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(25, 0, 25, 0);
        ll.setLayoutParams(lp);
        ll.requestLayout();

        TextView dateView = new TextView(getActivity());
        dateView.setText(date);
        dateView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.25f));
        dateView.setTextColor(color);
        ll.addView(dateView);

        TextView weightView = new TextView(getActivity());
        weightView.setText(weight);
        weightView.setTextColor(color);
        weightView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.25f));
        ll.addView(weightView);

        TextView previousDeltaView = new TextView(getActivity());
        previousDeltaView.setText(previousDelta);
        previousDeltaView.setTextColor(color);
        previousDeltaView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.25f));
        ll.addView(previousDeltaView);

        TextView firstDeltaView = new TextView(getActivity());
        firstDeltaView.setText(firstDelta);
        firstDeltaView.setTextColor(color);
        firstDeltaView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.25f));
        ll.addView(firstDeltaView);

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

    public void refresh() {
        LinearLayout layout = (LinearLayout) getView().findViewById(R.id.weightContainer);
        layout.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.tab_weight, null);
        layout.addView(view);
        initialise();
        TabWeight.this.getView().invalidate();
    }
}