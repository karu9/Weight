package com.example.pichery.weight.listeners;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.pichery.weight.R;
import com.example.pichery.weight.model.ConsumedFood;
import com.example.pichery.weight.model.Food;
import com.example.pichery.weight.tab.TabFood;
import com.example.pichery.weight.tab.TabHome;
import com.example.pichery.weight.util.DBUtils;

import java.util.List;

/**
 * Created by Doudouz on 14/04/2016.
 */
public class ModifyListenerConsumed implements View.OnClickListener {
    private ConsumedFood food;
    private FragmentActivity activity;
    private AlertDialog dialog;

    public ModifyListenerConsumed(ConsumedFood food, FragmentActivity activity, AlertDialog dialog){
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
        final View checkboxLayout = inflater.inflate(R.layout.popup_modify_consumed, null);
        helpBuilder.setView(checkboxLayout);
        final AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
        EditText name = (EditText) checkboxLayout.findViewById(R.id.nameTextModifyFoodConsumed);
        name.setText(food.getName());
        EditText points = (EditText) checkboxLayout.findViewById(R.id.ppTextModifyFoodConsumed);
        points.setText(food.getPoints());

        Button modify = (Button) checkboxLayout.findViewById(R.id.modifyFoodConsumed);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = (EditText) checkboxLayout.findViewById(R.id.nameTextModifyFoodConsumed);
                String nameText = name.getText().toString();
                EditText points = (EditText) checkboxLayout.findViewById(R.id.ppTextModifyFoodConsumed);
                String pointsText = points.getText().toString();
                if(!food.getName().equals(nameText) || !food.getPoints().equals(pointsText)){
                    DBUtils util = new DBUtils(activity);
                    util.execute(ConsumedFood.modifyConsumedFood(food, nameText, pointsText));
                    refreshTabHome();
                    helpDialog.cancel();
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
