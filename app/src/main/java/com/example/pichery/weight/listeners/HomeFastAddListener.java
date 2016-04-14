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
import com.example.pichery.weight.tab.TabHome;
import com.example.pichery.weight.util.DBUtils;

import java.util.List;

/**
 * Created by Doudouz on 14/04/2016.
 */
public class HomeFastAddListener implements View.OnClickListener {

    private FragmentActivity activity;

    public HomeFastAddListener(FragmentActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(activity);
        helpBuilder.setTitle(activity.getResources().getString(R.string.fastAdd));
        LayoutInflater inflater = activity.getLayoutInflater();
        final View checkboxLayout = inflater.inflate(R.layout.popup_fast_add, null);
        helpBuilder.setView(checkboxLayout);
        final AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
        Button add = (Button) checkboxLayout.findViewById(R.id.addToJournalFastButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = (EditText) checkboxLayout.findViewById(R.id.nameFast);
                EditText pp = (EditText) checkboxLayout.findViewById(R.id.ppFast);
                if(name.getText() != null && name.getText().length() > 0 && pp.getText() != null && pp.getText().length() > 0){
                    try{
                        String nameVal = name.getText().toString();
                        int ppInt = Integer.parseInt(pp.getText().toString());
                        new DBUtils(activity).execute(ConsumedFood.addConsumedFood(nameVal, "" + ppInt));

                        helpDialog.cancel();
                        refreshTabHome();
                    }
                    catch (Exception e){}
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
