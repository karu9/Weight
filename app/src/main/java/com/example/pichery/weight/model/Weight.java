package com.example.pichery.weight.model;

import android.database.Cursor;

import com.example.pichery.weight.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pichery on 06/12/15.
 */
public class Weight implements Comparable<Weight>{
    String date;
    String value;

    public Weight(Cursor cursor){
        String stringDate = cursor.getString(0);
        date = stringDate;
        value = cursor.getString(1);
    }

    public Weight(String date, String value){
        date = date;
        value = value;
    }

    public static String getQuery() {
        return "Select * from Weight order by date DESC";
    }
    
    public static String getDeltaWeight(Weight ref, Weight comparedTo){
        return String.valueOf(Float.valueOf(ref.getValue()) - Float.valueOf(comparedTo.getValue()));
    }

    public String getValue() {
        return value;
    }

    public String getDate() {
        return date;
    }

    public static String setWeightQuery(String value){
        return "INSERT INTO Weight VALUES ('"+DateUtil.formatDate(new Date())+"','"+value+"')";
    }

    @Override
    public int compareTo(Weight weight){
        return DateUtil.getDate(weight.date).compareTo(DateUtil.getDate(this.date));
    }
}
