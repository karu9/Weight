package com.example.pichery.weight.tab;

/**
 * Created by pichery on 29/11/15.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.pichery.weight.R;
import com.example.pichery.weight.util.Calc;
import com.example.pichery.weight.util.DBUtils;

public class TabCalc extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_calc, container, false);
    }

    private void setListener() {
        Button button = (Button) getView().findViewById(R.id.calculatePointButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText kcaltext = (EditText) getView().findViewById(R.id.kCalText);
                EditText pourText = (EditText) getView().findViewById(R.id.pourText);
                EditText partText = (EditText) getView().findViewById(R.id.partText);

                if(!kcaltext.getText().toString().isEmpty() && !pourText.getText().toString().isEmpty() && !partText.getText().toString().isEmpty()) {
                    try {
                        float kCal = Float.valueOf(kcaltext.getText().toString());
                        float pour = Float.valueOf(pourText.getText().toString());
                        float part = Float.valueOf(partText.getText().toString());
                        if(kCal != 0f && pour != 0f && part != 0f){
                            int points = Calc.calculatePoints(38f, kCal * part / pour);
                             partText = (EditText) getView().findViewById(R.id.partText);
                        }
                    } catch (Exception e) {

                    }
                }
            }
        });
    }
}