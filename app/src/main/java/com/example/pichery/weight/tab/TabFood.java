package com.example.pichery.weight.tab;

/**
 * Created by pichery on 29/11/15.
 */
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pichery.weight.R;
import com.example.pichery.weight.listeners.FoodListener;
import com.example.pichery.weight.model.Food;
import com.example.pichery.weight.model.Weight;
import com.example.pichery.weight.util.Calc;
import com.example.pichery.weight.util.DBUtils;

import java.util.Collections;
import java.util.List;

public class TabFood extends Fragment {

    DBUtils dbUtil;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_food, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise();
    }

    private void initialise() {
        dbUtil =  new DBUtils(getActivity());
        setValuesFromDb(dbUtil.loadFood());
        setSearchListener();
    }

    private void setSearchListener() {
        Button button = (Button) getView().findViewById(R.id.findFood);
        if(button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText text = (EditText) getView().findViewById(R.id.findFoodText);
                    String foodString = text.getText().toString();
                    text.setText(null);
                    List<Food> foodList = dbUtil.loadFood(Food.searchFood(foodString));
                    LinearLayout layout = (LinearLayout) getView().findViewById(R.id.foodContainer);
                    layout.removeAllViews();
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    View view = inflater.inflate(R.layout.tab_food, null);
                    layout.addView(view);
                    setValuesFromDb(foodList);
                    TabFood.this.getView().invalidate();
                    setSearchListener();
                }
            });
        }
    }

    private void setValuesFromDb(List<Food> foodList) {
        LinearLayout layout = (LinearLayout) getView().findViewById(R.id.foodContainer);
        Collections.sort(foodList);
        if(foodList != null && foodList.size() > 0){
            for(int i = 0; i < foodList.size(); i++){
                Food food = foodList.get(i);
                layout.addView(createDbInput(food));
                layout.addView(createSeparator());
            }
        }
    }

    private LinearLayout createDbInput(Food food){
        String name = food.getName();
        String pp = Calc.calculatePoint(Float.parseFloat(food.getBaseCalories()),Float.parseFloat(food.getBaseWeight()), Float.parseFloat(food.getBaseWeight()))
                + " pp/ " + food.getBaseWeight()+ " " + food.getUnit();
        LinearLayout ll = new LinearLayout(getActivity());
        ll.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10, 0, 10, 0);
        ll.setLayoutParams(lp);
        ll.requestLayout();

        TextView dateView = new TextView(getActivity());
        dateView.setText(name);
        dateView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f));
        ll.addView(dateView);

        TextView weightView = new TextView(getActivity());
        weightView.setText(pp);
        weightView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f));
        ll.addView(weightView);

        ll.setOnClickListener(new FoodListener(food, getActivity()));
        return ll;
    }

    private ImageView createSeparator(){
        ImageView iv = new ImageView(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(25, 4, 25, 0);
        iv.setLayoutParams(lp);
        iv.setPadding(0, 10, 0, 0);
        return iv;
    }

    public void refresh() {
        LinearLayout layout = (LinearLayout) getView().findViewById(R.id.foodContainer);
        layout.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.tab_food, null);
        layout.addView(view);
        initialise();
        TabFood.this.getView().invalidate();
    }


}