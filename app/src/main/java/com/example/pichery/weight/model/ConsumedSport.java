package com.example.pichery.weight.model;

import android.database.Cursor;

import com.example.pichery.weight.util.DateUtil;

import java.util.Date;

/**
 * Created by Doudouz on 24/03/2016.
 */
public class ConsumedSport implements Comparable<ConsumedSport>{
    private String date;
    private String name;
    private String points;

    public ConsumedSport(Cursor cursor){
        date = cursor.getString(0);
        name = cursor.getString(1);
        points = cursor.getString(2);
    }

    public static String getQuery(Date date){
        return "Select * from ConsumedSport where date like '" + DateUtil.formatDate(date) + "%'";
    }

    public static String addConsumedSport(String name, String points){
        return "Insert into ConsumedSport VALUES ('" + DateUtil.formatDateWithHour(new Date()) + "','" + name + "','" + points + "')";
    }


    @Override
    public int compareTo(ConsumedSport another) {
        return name.compareTo(another.name);
    }

    public String getName(){
        return name;
    }

    public String getPoints(){
        return points;
    }

    public static String getRemoveQuery(ConsumedSport food) {
        return "DELETE From ConsumedSport where name ='" + food.name + "' and points = '" + food.getPoints() + "' and date = '" + food.date + "'";
    }

    public static String modifyConsumedFood(ConsumedSport food, String nameText, String pointsText) {
        return "Update ConsumedSport Set name ='" + nameText +"', points = '" + pointsText + "' where name ='" + food.name + "' and points = '" + food.getPoints() + "' and date = '" + food.date + "'";
    }
}
