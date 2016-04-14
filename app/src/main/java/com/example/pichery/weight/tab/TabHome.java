package com.example.pichery.weight.tab;

/**
 * Created by pichery on 29/11/15.
 */

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pichery.weight.R;
import com.example.pichery.weight.listeners.HomeFastAddListener;
import com.example.pichery.weight.listeners.HomeListener;
import com.example.pichery.weight.model.ConsumedFood;
import com.example.pichery.weight.model.Food;
import com.example.pichery.weight.model.Profile;
import com.example.pichery.weight.model.Week;
import com.example.pichery.weight.model.Weight;
import com.example.pichery.weight.util.Calc;
import com.example.pichery.weight.util.DBUtils;
import com.example.pichery.weight.util.DateUtil;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TabHome extends Fragment {


    private DBUtils dbUtil;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise();
    }

    private void initialise() {
        dbUtil =  new DBUtils(getActivity());
        setValuesFromDb();
        setLeftPoints();
        setFastAddListener();
    }

    private void setFastAddListener() {
        FloatingActionButton fastAdd = (FloatingActionButton) getView().findViewById(R.id.fastAdd);
        fastAdd.setOnClickListener(new HomeFastAddListener(getActivity()));
    }

    private int getSportPoints(Date date) {
        return 0;
    }

    private int getDailyPoints(){
        Profile profile = dbUtil.loadProfile();
        boolean female = "femme".equalsIgnoreCase(profile.getSex());
        List<Weight> weights = dbUtil.loadWeights();
        float weight = 100f;
        if(weights != null && !weights.isEmpty()){
            Collections.sort(weights);
            weight = Float.parseFloat(weights.get(0).getValue());
        }
        return Calc.calculatePointWeight(weight, female);
    }

    private int getHebdoPoints(Date date){
        List<Week> weeks = dbUtil.loadWeeks();
        Week week = Week.getWeek(date, weeks, dbUtil);
        Calendar dayStart = Calendar.getInstance();
        dayStart.setTime(week.getDateStart());
        Calendar dayEnd = Calendar.getInstance();
        dayEnd.setTime(week.getDateEnd());

        int HebdoPoints = 49;
        while(DateUtil.formatDate(dayStart.getTime()).compareTo(DateUtil.formatDate(dayEnd.getTime())) <= 0){
            HebdoPoints -= getDeltaHebdoPoints(dayStart.getTime());
            dayStart.add(Calendar.DATE, 1);
        }
        if(HebdoPoints > 0){
            return HebdoPoints;
        }
        return 0;
    }

    private int getDeltaHebdoPoints(Date date){
        int points = getConsumedPoints(date);
        int dailyPoints = getDailyPoints();
        if(points > dailyPoints){
            return  points - dailyPoints;
        }
        return 0;
    }

    private int getConsumedPoints(Date date){
        List<ConsumedFood> consumedFood = dbUtil.loadConsumedFood(date);
        String leftPointsText = null;
        if(consumedFood != null && consumedFood.size() > 0) {
            int consumedPP = 0;
            for (ConsumedFood food : consumedFood) {
                consumedPP += Integer.parseInt(food.getPoints());
            }
            return consumedPP;
        }
        return 0;
    }

    private int getLeftPoints(Date date){
        int points = getDailyPoints();
        int consumed = getConsumedPoints(date);
        if(consumed < points){
            return points - consumed;
        }
        return 0;
    }

    private void setLeftPoints() {
        Button leftPoints = (Button) getView().findViewById(R.id.leftPoints);
        leftPoints.setText(getResources().getText(R.string.leftPoints) + String.valueOf(getLeftPoints(new Date())));
        Button hebdoPoints = (Button) getView().findViewById(R.id.hebdoPoints);
        hebdoPoints.setText(getResources().getText(R.string.hebdoPoints) + String.valueOf(getHebdoPoints(new Date())));
        Button sportPoints = (Button) getView().findViewById(R.id.sportPoints);
        sportPoints.setText(getResources().getText(R.string.sportPoints) + String.valueOf(getSportPoints(new Date())));
    }

    private void setValuesFromDb() {
        LinearLayout layout = (LinearLayout) getView().findViewById(R.id.contentContainer);
        List<ConsumedFood> consumedFood = dbUtil.loadConsumedFood(new Date());
        Collections.sort(consumedFood);
        if(consumedFood != null && consumedFood.size() > 0){
            for(int i = 0; i < consumedFood.size(); i++){
                layout.addView(createDbInput(consumedFood.get(i)));
                layout.addView(createSeparator());
            }
        }
    }

    private LinearLayout createDbInput(ConsumedFood consumedFood){
        String name = consumedFood.getName();
        String point = consumedFood.getPoints();
        LinearLayout ll = new LinearLayout(getActivity());
        ll.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(25, 0, 25, 0);
        ll.setLayoutParams(lp);
        ll.requestLayout();

        TextView dateView = new TextView(getActivity());
        dateView.setText(name);
        dateView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f));
        ll.addView(dateView);

        TextView weightView = new TextView(getActivity());
        weightView.setText(point);
        weightView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f));
        ll.addView(weightView);

        ll.setOnClickListener(new HomeListener(consumedFood, getActivity()));
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
        LinearLayout layout = (LinearLayout) getView().findViewById(R.id.homeContainer);
        layout.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.tab_home, null);
        layout.addView(view);
        initialise();
        this.getView().invalidate();
    }

}