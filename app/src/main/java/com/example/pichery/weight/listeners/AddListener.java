package com.example.pichery.weight.listeners;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pichery.weight.R;
import com.example.pichery.weight.model.ConsumedFood;
import com.example.pichery.weight.model.Food;
import com.example.pichery.weight.tab.TabHome;
import com.example.pichery.weight.util.Calc;
import com.example.pichery.weight.util.DBUtils;

import java.util.List;

/**
 * Created by Doudouz on 14/04/2016.
 */
public class AddListener implements View.OnClickListener {
    private final DBUtils dbUtil;
    private Food food;
    private FragmentActivity activity;
    private AlertDialog parentDialog;

    public AddListener(Food food, FragmentActivity activity, AlertDialog dialog){
        this.food = food;
        this.activity = activity;
        this.parentDialog = dialog;
        dbUtil = new DBUtils(activity);
    }

    @Override
    public void onClick(View v) {
        parentDialog.cancel();
        final AlertDialog.Builder helpBuilder = new AlertDialog.Builder(activity);
        helpBuilder.setTitle(food.getName() + "(" + food.getUnit() + ")");

        LayoutInflater inflater = activity.getLayoutInflater();
        final View checkboxLayout = inflater.inflate(R.layout.popup_add_food, null);
        helpBuilder.setView(checkboxLayout);
        final AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
        EditText partText = (EditText) checkboxLayout.findViewById(R.id.PartTextAdd);
        partText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                EditText et = (EditText) checkboxLayout.findViewById(R.id.PartTextAdd);
                if (et.getText() != null && et.getText().length() != 0) {
                    try {
                        float part = Float.parseFloat(et.getText().toString());
                        int pp = Calc.calculatePoint(Float.parseFloat(food.getBaseCalories()), Float.parseFloat(food.getBaseWeight()), part);
                        TextView ppText = (TextView) checkboxLayout.findViewById(R.id.ppText);
                        ppText.setText(activity.getResources().getString(R.string.pp) + ": " + pp);
                    } catch (Exception e) {

                    }
                }
            }
        });

        Button addButton = (Button) checkboxLayout.findViewById(R.id.addToJButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = (EditText) checkboxLayout.findViewById(R.id.PartTextAdd);
                if (et.getText() != null && et.getText().length() != 0) {
                    try {
                        float part = Float.parseFloat(et.getText().toString());
                        int pp = Calc.calculatePoint(Float.parseFloat(food.getBaseCalories()), Float.parseFloat(food.getBaseWeight()), part);
                        dbUtil.execute(ConsumedFood.addConsumedFood(food.getName(), String.valueOf(pp)));
                        Toast toast = Toast.makeText(activity, activity.getResources().getString(R.string.toastConsumedAdded), Toast.LENGTH_SHORT);
                        toast.show();
                        refreshTabHome();
                        helpDialog.cancel();
                    } catch (Exception e) {

                    }
                }
            }
        });
    }

    private void refreshTabHome() {
        List<Fragment> listFragment = activity.getSupportFragmentManager().getFragments();
        if(listFragment != null && !listFragment.isEmpty()){
            for (Fragment frag : listFragment){
                if(frag instanceof TabHome){
                    ((TabHome) frag).refresh();
                    break;
                }
            }
        }
    }
}
