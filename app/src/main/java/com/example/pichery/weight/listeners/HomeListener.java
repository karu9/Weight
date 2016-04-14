package com.example.pichery.weight.listeners;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pichery.weight.R;
import com.example.pichery.weight.model.ConsumedFood;
import com.example.pichery.weight.model.Food;
import com.example.pichery.weight.tab.TabHome;
import com.example.pichery.weight.util.DBUtils;

import java.util.List;

/**
 * Created by Doudouz on 14/04/2016.
 */
public class HomeListener implements View.OnClickListener {

    private ConsumedFood food;
    private FragmentActivity activity;

    public HomeListener(ConsumedFood food, FragmentActivity activity){
        this.food = food;
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(activity);
        helpBuilder.setTitle(food.getName() + " - " + food.getPoints() + activity.getResources().getString(R.string.pp));
        LayoutInflater inflater = activity.getLayoutInflater();
        View checkboxLayout = inflater.inflate(R.layout.popup_home, null);
        helpBuilder.setView(checkboxLayout);
        final AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
        Button delete = (Button) checkboxLayout.findViewById(R.id.deleteButtonHome);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DBUtils(activity).execute(ConsumedFood.getRemoveQuery(food));
                Toast toast = Toast.makeText(activity, activity.getResources().getString(R.string.toastConsumedAdded), Toast.LENGTH_SHORT);
                toast.show();
                helpDialog.cancel();
                refreshTabHome();
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
