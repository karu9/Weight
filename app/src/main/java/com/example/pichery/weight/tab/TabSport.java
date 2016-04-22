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
import com.example.pichery.weight.listeners.SportAddListener;
import com.example.pichery.weight.listeners.SportListener;
import com.example.pichery.weight.model.Food;
import com.example.pichery.weight.model.Sport;
import com.example.pichery.weight.model.Weight;
import com.example.pichery.weight.util.Calc;
import com.example.pichery.weight.util.DBUtils;

import java.util.Collections;
import java.util.List;

public class TabSport extends Fragment {

    DBUtils dbUtil;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_sport, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise();
    }

    private void initialise() {
        dbUtil =  new DBUtils(getActivity());
        setValuesFromDb(dbUtil.loadSport(Sport.getQuery()));
        setSearchListener();
        getView().findViewById(R.id.fastAddSport).setOnClickListener(new SportAddListener(getActivity()));
    }

    private void setSearchListener() {
        Button button = (Button) getView().findViewById(R.id.findSport);
        if(button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText text = (EditText) getView().findViewById(R.id.findSportText);
                    String sportString = text.getText().toString();
                    text.setText(null);
                    List<Sport> SportList = dbUtil.loadSport(Sport.searchSport(sportString));
                    LinearLayout layout = (LinearLayout) getView().findViewById(R.id.sportContainer);
                    layout.removeAllViews();
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    View view = inflater.inflate(R.layout.tab_sport, null);
                    layout.addView(view);
                    setValuesFromDb(SportList);
                    TabSport.this.getView().invalidate();
                    setSearchListener();
                }
            });
        }

    }

    private void setValuesFromDb(List<Sport> sportList) {
        LinearLayout layout = (LinearLayout) getView().findViewById(R.id.sportContainer);
        Collections.sort(sportList);
        if(sportList != null && sportList.size() > 0){
            for(int i = 0; i < sportList.size(); i++){
                Sport sport = sportList.get(i);
                layout.addView(createDbInput(sport));
                layout.addView(createSeparator());
            }
        }
    }

    private LinearLayout createDbInput(Sport sport){
        String name = sport.getName();
        String pp = Calc.calculatePoint(Float.parseFloat(sport.getBaseCalories()),Float.parseFloat(sport.getBaseTime()), 30f) + " pp/30min";
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

        ll.setOnClickListener(new SportListener(sport, getActivity()));
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
        LinearLayout layout = (LinearLayout) getView().findViewById(R.id.sportContainer);
        layout.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.tab_sport, null);
        layout.addView(view);
        initialise();
        TabSport.this.getView().invalidate();
    }


}