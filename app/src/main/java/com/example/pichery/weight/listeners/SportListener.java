package com.example.pichery.weight.listeners;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pichery.weight.R;
import com.example.pichery.weight.model.Food;
import com.example.pichery.weight.model.Sport;
import com.example.pichery.weight.tab.TabFood;
import com.example.pichery.weight.util.DBUtils;

import java.util.List;

/**
 * Created by Doudouz on 14/04/2016.
 */
public class SportListener implements View.OnClickListener {

    private Sport sport;
    private FragmentActivity activity;

    public SportListener(Sport sport, FragmentActivity activity){
        this.sport = sport;
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(activity);
        helpBuilder.setTitle(sport.getName());

        LayoutInflater inflater = activity.getLayoutInflater();
        View checkboxLayout = inflater.inflate(R.layout.popup_sport, null);
        helpBuilder.setView(checkboxLayout);
        final AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
        Button add = (Button) checkboxLayout.findViewById(R.id.addToJournalButtonSport);
        add.setOnClickListener(new AddSportListener(sport, activity, helpDialog));

        Button delete = (Button) checkboxLayout.findViewById(R.id.deleteButtonSport);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DBUtils(activity).execute(Sport.getRemoveQuery(sport));
                Toast toast = Toast.makeText(activity, activity.getResources().getString(R.string.sportRemoved), Toast.LENGTH_SHORT);
                toast.show();
                helpDialog.cancel();
                refreshTabFood();
            }
        });

        Button modify = (Button) checkboxLayout.findViewById(R.id.modifyButtonSport);
        modify.setOnClickListener(new ModifySportListener(sport, activity, helpDialog));
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
