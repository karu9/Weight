package com.example.pichery.weight.listeners;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.pichery.weight.R;
import com.example.pichery.weight.model.Food;
import com.example.pichery.weight.tab.TabFood;
import com.example.pichery.weight.util.DBUtils;

import java.util.List;

/**
 * Created by Doudouz on 14/04/2016.
 */
public class ModifyListener implements View.OnClickListener {
    private Food food;
    private FragmentActivity activity;
    private AlertDialog dialog;

    public ModifyListener(Food food, FragmentActivity activity, AlertDialog dialog){
        this.dialog = dialog;
        this.food = food;
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        dialog.cancel();

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(activity);
        helpBuilder.setTitle(activity.getResources().getString(R.string.modify));

        LayoutInflater inflater = activity.getLayoutInflater();
        final View checkboxLayout = inflater.inflate(R.layout.popup_modify, null);
        helpBuilder.setView(checkboxLayout);
        final AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
        EditText name = (EditText) checkboxLayout.findViewById(R.id.nameTextModifyFood);
        name.setText(food.getName());
        EditText calories = (EditText) checkboxLayout.findViewById(R.id.caloriesTextModifyFood);
        calories.setText(food.getBaseCalories());
        EditText pour = (EditText) checkboxLayout.findViewById(R.id.pourTextModifyFood);
        pour.setText(food.getBaseWeight());
        EditText unit = (EditText) checkboxLayout.findViewById(R.id.unitTextModifyFood);
        unit.setText(food.getUnit());

        Button modify = (Button) checkboxLayout.findViewById(R.id.modifyFood);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = (EditText) checkboxLayout.findViewById(R.id.nameTextModifyFood);
                String nameText = name.getText().toString();
                EditText calories = (EditText) checkboxLayout.findViewById(R.id.caloriesTextModifyFood);
                String caloriesText = calories.getText().toString();
                EditText pour = (EditText) checkboxLayout.findViewById(R.id.pourTextModifyFood);
                String pourText = pour.getText().toString();
                EditText unit = (EditText) checkboxLayout.findViewById(R.id.unitTextModifyFood);
                String unitText = unit.getText().toString();
                if(!food.getName().equals(nameText) || !food.getUnit().equals(unitText) || !food.getBaseCalories().equals(caloriesText) || !food.getBaseWeight().equals(pourText)){
                    DBUtils util = new DBUtils(activity);
                    util.execute(Food.modifyFood(food, nameText, caloriesText, unitText, pourText));
                    refreshTabFood();
                    helpDialog.cancel();
                }
            }
        });
    }

    public void refreshTabFood(){
        List<Fragment> listFragment = activity.getSupportFragmentManager().getFragments();
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
