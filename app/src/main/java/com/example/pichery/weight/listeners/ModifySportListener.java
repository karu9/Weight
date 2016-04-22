package com.example.pichery.weight.listeners;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pichery.weight.R;
import com.example.pichery.weight.model.Food;
import com.example.pichery.weight.model.Sport;
import com.example.pichery.weight.tab.TabFood;
import com.example.pichery.weight.tab.TabSport;
import com.example.pichery.weight.util.DBUtils;

import java.util.List;

/**
 * Created by Doudouz on 14/04/2016.
 */
public class ModifySportListener implements View.OnClickListener {
    private Sport sport;
    private FragmentActivity activity;
    private AlertDialog dialog;

    public ModifySportListener(Sport sport, FragmentActivity activity, AlertDialog dialog){
        this.dialog = dialog;
        this.sport = sport;
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        dialog.cancel();

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(activity);
        helpBuilder.setTitle(activity.getResources().getString(R.string.modify));

        LayoutInflater inflater = activity.getLayoutInflater();
        final View checkboxLayout = inflater.inflate(R.layout.popup_modify_sport, null);
        helpBuilder.setView(checkboxLayout);
        final AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
        EditText name = (EditText) checkboxLayout.findViewById(R.id.nameTextModifySport);
        name.setText(sport.getName());
        EditText calories = (EditText) checkboxLayout.findViewById(R.id.caloriesTextModifySport);
        calories.setText(sport.getBaseCalories());
        EditText time = (EditText) checkboxLayout.findViewById(R.id.timeTextModifySport);
        time.setText(sport.getBaseTime());

        Button modify = (Button) checkboxLayout.findViewById(R.id.modifySport);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = (EditText) checkboxLayout.findViewById(R.id.nameTextModifySport);
                String nameText = name.getText().toString();
                EditText calories = (EditText) checkboxLayout.findViewById(R.id.caloriesTextModifySport);
                String caloriesText = calories.getText().toString();
                EditText time = (EditText) checkboxLayout.findViewById(R.id.timeTextModifySport);
                String timeText = time.getText().toString();
                if(!sport.getName().equals(nameText) || !sport.getBaseTime().equals(timeText) || !sport.getBaseCalories().equals(caloriesText)){
                    DBUtils util = new DBUtils(activity);
                    util.execute(Sport.modifySport(sport, nameText, caloriesText, timeText));
                    refreshTabSport();
                    helpDialog.cancel();
                }
            }
        });
    }

    public void refreshTabSport(){
        List<Fragment> listFragment = activity.getSupportFragmentManager().getFragments();
        if(listFragment != null && !listFragment.isEmpty()){
            for (Fragment frag : listFragment){
                if(frag instanceof TabSport){
                    ((TabSport) frag).refresh();
                    break;
                }
            }
        }
    }
}
