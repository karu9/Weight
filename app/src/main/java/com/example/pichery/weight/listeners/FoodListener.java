package com.example.pichery.weight.listeners;

import android.app.AlertDialog;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.pichery.weight.R;
import com.example.pichery.weight.model.Food;

/**
 * Created by Doudouz on 14/04/2016.
 */
public class FoodListener implements View.OnClickListener {

    private Food food;
    private FragmentActivity activity;

    public FoodListener(Food food, FragmentActivity activity){
        this.food = food;
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(activity);
        helpBuilder.setTitle(food.getName());

        LayoutInflater inflater = activity.getLayoutInflater();
        View checkboxLayout = inflater.inflate(R.layout.popup_food, null);
        helpBuilder.setView(checkboxLayout);
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
        Button add = (Button) checkboxLayout.findViewById(R.id.addToJournalButton);
        add.setOnClickListener(new AddListener(food, activity, helpDialog));

        Button delete = (Button) checkboxLayout.findViewById(R.id.deleteButton);
        Button modify = (Button) checkboxLayout.findViewById(R.id.modifyButton);
    }
}
