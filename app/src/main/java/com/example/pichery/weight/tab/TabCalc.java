package com.example.pichery.weight.tab;

/**
 * Created by pichery on 29/11/15.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pichery.weight.R;
import com.example.pichery.weight.listeners.AddFoodListener;
import com.example.pichery.weight.model.Food;
import com.example.pichery.weight.util.Calc;
import com.example.pichery.weight.util.DBUtils;

import java.util.List;

public class TabCalc extends Fragment {

    private DBUtils dbUtil;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_calc2, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise();
    }

    private void initialise() {
        dbUtil =  new DBUtils(getActivity());
        setListener();
    }


    private void setListener() {

        EditText text = (EditText) getView().findViewById(R.id.partText);
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                EditText kcaltext = (EditText) getView().findViewById(R.id.kCalText);
                EditText pourText = (EditText) getView().findViewById(R.id.pourText);
                EditText partText = (EditText) getView().findViewById(R.id.partText);

                if (!kcaltext.getText().toString().isEmpty() && !pourText.getText().toString().isEmpty() && !partText.getText().toString().isEmpty()) {
                    try {
                        float kCal = Float.valueOf(kcaltext.getText().toString());
                        float pour = Float.valueOf(pourText.getText().toString());
                        float part = Float.valueOf(partText.getText().toString());
                        if (kCal != 0f && pour != 0f && part != 0f) {
                            int points = Calc.calculatePoints(kCal * part / pour);
                            TextView resultText = (TextView) getView().findViewById(R.id.pointTextView);
                            resultText.setText(getResources().getText(R.string.points).toString() + points);
                        }
                    } catch (Exception e) {

                    }
                }
            }
        });

        Button button = (Button) getView().findViewById(R.id.addButton);
        button.setOnClickListener(new AddFoodListener(getActivity(), getView()));

    }

    public void refreshTabHome(){
        List<Fragment> listFragment = this.getFragmentManager().getFragments();
        if(listFragment != null && !listFragment.isEmpty()){
            for (Fragment frag : listFragment){
                if(frag instanceof TabHome){
                    ((TabHome) frag).refresh();
                    break;
                }
            }
        }
    }

    public void refreshTabFood(){
        List<Fragment> listFragment = this.getFragmentManager().getFragments();
        if(listFragment != null && !listFragment.isEmpty()){
            for (Fragment frag : listFragment){
                if(frag instanceof TabFood){
                    ((TabFood) frag).refresh();
                    break;
                }
            }
        }
    }
}