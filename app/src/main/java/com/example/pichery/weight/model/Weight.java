package com.example.pichery.weight.model;

import android.database.Cursor;

import com.example.pichery.weight.util.DateUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by pichery on 06/12/15.
 */
public class Weight {
    String date;
    Float value;

    public Weight(Cursor cursor){
        String stringDate = cursor.getString(0);
        date = stringDate;
        value = cursor.getFloat(1);
    }

    public static String getQuery() {
        return "Select * from Weight order by date DESC";
    }
    
    public static Weight getFirstWeight(List<Weight> listWeight){
        if (listWeight == null || listWeight.isEmpty()){
            return null;
        }
        else{
            Weight min = listWeight.get(0);
            for(int i = 1; i < listWeight.size(); i++){
                if(listWeight.get(i).getDate().compareTo(min.getDate())<0){
                    min = listWeight.get(i);
                }
            }
            return min;
        }

    }
    
    public static float getDeltaWeight(Weight ref, Weight comparedTo){
        return comparedTo.getValue() - ref.getValue();
    }

    public Float getValue() {
        return value;
    }

    public String getDate() {
        return date;
    }

    public static String setWeightQuery(Float value){
        return "INSERT INTO Weight VALUES ("+DateUtil.formatDate(new Date())+","+value+")";
    }
}
