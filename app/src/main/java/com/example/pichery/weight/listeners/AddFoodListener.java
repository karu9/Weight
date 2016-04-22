package com.example.pichery.weight.listeners;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pichery.weight.R;
import com.example.pichery.weight.model.ConsumedFood;
import com.example.pichery.weight.model.Food;
import com.example.pichery.weight.tab.TabFood;
import com.example.pichery.weight.tab.TabHome;
import com.example.pichery.weight.util.Calc;
import com.example.pichery.weight.util.DBUtils;

import java.util.List;

/**
 * Created by Doudouz on 14/04/2016.
 */
public class AddFoodListener implements View.OnClickListener {

    private FragmentActivity activity;
    private View parentView;
    private DBUtils dbUtil;

    public AddFoodListener(FragmentActivity activity, View parentView){
        this.activity = activity;
        this.parentView = parentView;
        dbUtil = new DBUtils(activity);
    }

    @Override
    public void onClick(View v) {

        EditText kcaltext = (EditText) parentView.findViewById(R.id.kCalText);
        EditText pourText = (EditText) parentView.findViewById(R.id.pourText);
        if (!kcaltext.getText().toString().isEmpty() && !pourText.getText().toString().isEmpty()) {
            try {
                final float kCal = Float.valueOf(kcaltext.getText().toString());
                final float pour = Float.valueOf(pourText.getText().toString());
                if (kCal != 0f && pour != 0f) {
                    AlertDialog.Builder helpBuilder = new AlertDialog.Builder(activity);
                    helpBuilder.setTitle(activity.getResources().getText(R.string.addFood).toString());
                    LayoutInflater inflater = activity.getLayoutInflater();
                    final View checkboxLayout = inflater.inflate(R.layout.popup_food_add, null);
                    helpBuilder.setView(checkboxLayout);
                    AlertDialog helpDialog = helpBuilder.create();
                    helpDialog.show();
                    EditText partText = (EditText) parentView.findViewById(R.id.partText);
                    try{
                        float part = Float.valueOf(partText.getText().toString());
                        if(part != 0f){
                            EditText popupPart = (EditText) checkboxLayout.findViewById(R.id.partText);
                            popupPart.setText("" + part);
                            int points = Calc.calculatePoints(kCal * part / pour);
                            TextView popupPP = (TextView) checkboxLayout.findViewById(R.id.ppText);
                            popupPP.setText(activity.getResources().getText(R.string.pp) + ": " + points);
                        }
                    } catch (Exception e){}

                    EditText popupPart = (EditText) checkboxLayout.findViewById(R.id.partText);
                    popupPart.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            EditText et = (EditText) checkboxLayout.findViewById(R.id.partText);
                            if (et.getText() != null && et.getText().length() != 0) {
                                try {
                                    float part = Float.parseFloat(et.getText().toString());
                                    if (part != 0f) {
                                        int pp = Calc.calculatePoint(kCal, pour, part);
                                        TextView ppText = (TextView) checkboxLayout.findViewById(R.id.ppText);
                                        ppText.setText(activity.getResources().getString(R.string.pp) + ": " + pp);
                                    }
                                } catch (Exception e) {

                                }
                            }
                        }
                    });
                    Button addToJournal = (Button) checkboxLayout.findViewById(R.id.addToJournal);
                    addToJournal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditText nameText = (EditText) checkboxLayout.findViewById(R.id.nameText);
                            EditText partText = (EditText) checkboxLayout.findViewById(R.id.partText);

                            if (!nameText.getText().toString().isEmpty()) {
                                try {
                                    String name = nameText.getText().toString();
                                    float part = Float.parseFloat(partText.getText().toString());

                                    if (kCal != 0f && pour != 0f && part != 0f) {
                                        dbUtil.execute(ConsumedFood.addConsumedFood(name, ""+Calc.calculatePoint(kCal, pour, part)));
                                        Toast toast = Toast.makeText(activity, activity.getResources().getString(R.string.toastConsumedAdded), Toast.LENGTH_SHORT);
                                        toast.show();
                                        refreshTabHome();
                                    }
                                } catch (Exception e) {

                                }
                            }
                        }
                    });

                    Button addToList = (Button) checkboxLayout.findViewById(R.id.addToList);
                    addToList.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditText nameText = (EditText) checkboxLayout.findViewById(R.id.nameText);
                            EditText unitText = (EditText) checkboxLayout.findViewById(R.id.unitText);

                            if (!nameText.getText().toString().isEmpty()
                                    && !unitText.getText().toString().isEmpty()) {
                                try {
                                    String name = nameText.getText().toString();
                                    String unit = unitText.getText().toString();
                                    if (kCal != 0f && pour != 0f) {
                                        List<Food> foods = dbUtil.loadFood(Food.searchFood(name));
                                        boolean addFood = true;
                                        if (foods.size() > 0) {
                                            for (Food food : foods) {
                                                if (food.getName().equalsIgnoreCase(name)) {
                                                    addFood = false;
                                                    break;
                                                }
                                            }
                                        }
                                        if (addFood) {
                                            dbUtil.execute(Food.addFoodQuery(name, String.valueOf(kCal), String.valueOf(pour), unit));
                                            Toast toast = Toast.makeText(activity, activity.getResources().getString(R.string.toastFoodAdded), Toast.LENGTH_SHORT);
                                            toast.show();
                                            refreshTabFood();
                                        } else {
                                            Toast toast = Toast.makeText(activity, activity.getResources().getString(R.string.toastFoodNotAdded), Toast.LENGTH_SHORT);
                                            toast.show();
                                        }

                                    }
                                } catch (Exception e) {

                                }
                            }
                        }
                    });
                }
            }catch (Exception e){}
        }
    }

    public void refreshTabHome(){
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
