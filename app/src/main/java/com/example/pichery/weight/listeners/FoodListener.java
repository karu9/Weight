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
import com.example.pichery.weight.tab.TabFood;
import com.example.pichery.weight.util.DBUtils;

import java.util.List;

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
        final AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
        Button add = (Button) checkboxLayout.findViewById(R.id.addToJournalButton);
        add.setOnClickListener(new AddListener(food, activity, helpDialog));

        Button delete = (Button) checkboxLayout.findViewById(R.id.deleteButton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DBUtils(activity).execute(Food.getRemoveQuery(food));
                Toast toast = Toast.makeText(activity, activity.getResources().getString(R.string.toastRemoved), Toast.LENGTH_SHORT);
                toast.show();
                helpDialog.cancel();
                refreshTabFood();
            }
        });

        Button modify = (Button) checkboxLayout.findViewById(R.id.modifyButton);
        modify.setOnClickListener(new ModifyListener(food, activity, helpDialog));
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
