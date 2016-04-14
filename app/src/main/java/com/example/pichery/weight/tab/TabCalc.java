package com.example.pichery.weight.tab;

/**
 * Created by pichery on 29/11/15.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pichery.weight.R;
import com.example.pichery.weight.model.ConsumedFood;
import com.example.pichery.weight.model.Food;
import com.example.pichery.weight.util.Calc;
import com.example.pichery.weight.util.DBUtils;

import java.util.List;

public class TabCalc extends Fragment {

    private DBUtils dbUtil;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_calc, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise();
    }

    private void initialise() {
        dbUtil =  new DBUtils(getActivity());
        setListener();
    }


    private void setListener() {
        Button button = (Button) getView().findViewById(R.id.calculatePointButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText kcaltext = (EditText) getView().findViewById(R.id.kCalText);
                EditText pourText = (EditText) getView().findViewById(R.id.pourText);
                EditText partText = (EditText) getView().findViewById(R.id.partText);

                if(!kcaltext.getText().toString().isEmpty() && !pourText.getText().toString().isEmpty() && !partText.getText().toString().isEmpty()) {
                    try {
                        float kCal = Float.valueOf(kcaltext.getText().toString());
                        float pour = Float.valueOf(pourText.getText().toString());
                        float part = Float.valueOf(partText.getText().toString());
                        if(kCal != 0f && pour != 0f && part != 0f){
                            int points = Calc.calculatePoints(kCal * part / pour);
                            TextView resultText = (TextView) getView().findViewById(R.id.pointResultText);
                            resultText.setText(""+points);
                        }
                    } catch (Exception e) {

                    }
                }
            }
        });

        button = (Button) getView().findViewById(R.id.addButtonJournal);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText kcaltext = (EditText) getView().findViewById(R.id.kCalText);
                EditText pourText = (EditText) getView().findViewById(R.id.pourText);
                EditText partText = (EditText) getView().findViewById(R.id.partText);
                EditText nameText = (EditText) getView().findViewById(R.id.nameText);

                if (!kcaltext.getText().toString().isEmpty()
                        && !pourText.getText().toString().isEmpty()
                        && !nameText.getText().toString().isEmpty()
                        && !partText.getText().toString().isEmpty()) {
                    try {
                        float kCal = Float.valueOf(kcaltext.getText().toString());
                        float pour = Float.valueOf(pourText.getText().toString());
                        float part = Float.valueOf(partText.getText().toString());
                        String name = nameText.getText().toString();

                        if (part != 0f && pour != 0f && kCal != 0f) {
                            int points = Calc.calculatePoints(kCal * part / pour);
                            dbUtil.execute(ConsumedFood.addConsumedFood(name, String.valueOf(points)));
                            Toast toast = Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.toastConsumedAdded), Toast.LENGTH_SHORT);
                            toast.show();
                            refreshTabHome();
                        }

                        eraseText();


                    } catch (Exception e) {

                    }
                }
            }
        });

        button = (Button) getView().findViewById(R.id.addButtonList);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText kcaltext = (EditText) getView().findViewById(R.id.kCalText);
                EditText pourText = (EditText) getView().findViewById(R.id.pourText);
                EditText nameText = (EditText) getView().findViewById(R.id.nameText);
                EditText unitText = (EditText) getView().findViewById(R.id.unitText);

                if (!kcaltext.getText().toString().isEmpty()
                        && !pourText.getText().toString().isEmpty()
                        && !nameText.getText().toString().isEmpty()
                        && !unitText.getText().toString().isEmpty()) {
                    try {
                        float kCal = Float.valueOf(kcaltext.getText().toString());
                        float pour = Float.valueOf(pourText.getText().toString());
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
                                Toast toast = Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.toastFoodAdded), Toast.LENGTH_SHORT);
                                toast.show();
                                refreshTabFood();
                            } else {
                                Toast toast = Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.toastFoodNotAdded), Toast.LENGTH_SHORT);
                                toast.show();
                            }


                            eraseText();
                        }
                    } catch (Exception e) {

                    }
                }
            }
        });
    }

    public void eraseText(){
        EditText kcaltext = (EditText) getView().findViewById(R.id.kCalText);
        kcaltext.setText("");
        EditText pourText = (EditText) getView().findViewById(R.id.pourText);
        pourText.setText("");
        EditText nameText = (EditText) getView().findViewById(R.id.nameText);
        nameText.setText("");
        EditText unitText = (EditText) getView().findViewById(R.id.unitText);
        unitText.setText("");
        EditText partText = (EditText) getView().findViewById(R.id.partText);
        partText.setText("");
    }

    public void refreshTabHome(){
        List<Fragment> listFragment = this.getFragmentManager().getFragments();
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
        List<Fragment> listFragment = this.getFragmentManager().getFragments();
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