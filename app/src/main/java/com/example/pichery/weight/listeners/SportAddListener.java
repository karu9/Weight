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
import com.example.pichery.weight.model.ConsumedSport;
import com.example.pichery.weight.model.Sport;
import com.example.pichery.weight.tab.TabHome;
import com.example.pichery.weight.tab.TabSport;
import com.example.pichery.weight.util.DBUtils;

import java.util.List;

/**
 * Created by Doudouz on 14/04/2016.
 */
public class SportAddListener implements View.OnClickListener {

    private FragmentActivity activity;

    public SportAddListener(FragmentActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(activity);
        helpBuilder.setTitle(activity.getResources().getString(R.string.sportAdd));
        LayoutInflater inflater = activity.getLayoutInflater();
        final View checkboxLayout = inflater.inflate(R.layout.popup_fast_add_sport, null);
        helpBuilder.setView(checkboxLayout);
        final AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
        Button add = (Button) checkboxLayout.findViewById(R.id.addSportFast);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = (EditText) checkboxLayout.findViewById(R.id.nameTextSportFast);
                EditText calories = (EditText) checkboxLayout.findViewById(R.id.caloriesTextSportFast);
                EditText time = (EditText) checkboxLayout.findViewById(R.id.timeTextSportFast);
                if(name.getText() != null && name.getText().length() > 0
                        && calories.getText() != null && calories.getText().length() > 0
                        && time.getText() != null && time.getText().length() > 0){
                    try{
                        String nameVal = name.getText().toString();
                        String timeVal = time.getText().toString();
                        String caloriesVal = calories.getText().toString();
                        new DBUtils(activity).execute(Sport.addSportQuery(nameVal, timeVal, caloriesVal));

                        helpDialog.cancel();
                        refreshTabSport();
                    }
                    catch (Exception e){}
                }
            }
        });
    }

    private void refreshTabSport() {
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
