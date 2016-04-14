package com.example.pichery.weight.model;

import android.database.Cursor;

import com.example.pichery.weight.util.DateUtil;

import java.util.Date;

/**
 * Created by Doudouz on 24/03/2016.
 */
public class ConsumedFood implements Comparable<ConsumedFood>{
    private String date;
    private String name;
    private String points;

    public ConsumedFood(Cursor cursor){
        date = cursor.getString(0);
        name = cursor.getString(1);
        points = cursor.getString(2);
    }

    public static String getQuery(Date date){
        return "Select * from ConsumedFood where date like '" + DateUtil.formatDate(date) + "%'";
    }

    public static String addConsumedFood(String name, String points){
        return "Insert into ConsumedFood VALUES ('" + DateUtil.formatDateWithHour(new Date()) + "','" + name + "','" + points + "')";
    }


    @Override
    public int compareTo(ConsumedFood another) {
        return name.compareTo(another.name);
    }

    public String getName(){
        return name;
    }

    public String getPoints(){
        return points;
    }

    public static String getRemoveQuery(ConsumedFood food) {
        return "DELETE From ConsumedFood where name ='" + food.name + "' and points = '" + food.getPoints() + "' and date = '" + food.date + "'";
    }
}
