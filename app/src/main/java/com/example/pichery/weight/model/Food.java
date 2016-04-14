package com.example.pichery.weight.model;

import android.database.Cursor;

import com.example.pichery.weight.util.DateUtil;

import java.util.Date;

/**
 * Created by Doudouz on 24/03/2016.
 */
public class Food implements Comparable<Food>{
    private String name;
    private String baseCalories;
    private String baseWeight;
    private String type;
    private String unit;
    private String barCode;

    public Food(Cursor cursor){
        name = cursor.getString(0);
        baseCalories = cursor.getString(1);
        baseWeight = cursor.getString(2);
        type = cursor.getString(3);
        unit = cursor.getString(4);
        barCode = cursor.getString(5);
    }

    public static String getQuery(){
        return "Select * from Food";
    }

    public static String searchFood(String search){
        if(search == null){
            search = "";
        }
        return "Select * from Food where name like'%" + search + "%'";
    }

    @Override
    public int compareTo(Food another) {
        return this.name.toLowerCase().compareTo(another.name.toLowerCase());
    }

    public String getName(){
        return name;
    }

    public String getBaseCalories(){
        return baseCalories;
    }

    public String getBaseWeight(){
        return baseWeight;
    }

    public String getUnit(){return unit;}

    public static String addFoodQuery(String name, String kCal, String Pour, String unit){
        return "Insert into Food VALUES ('" + name + "','" + kCal + "','" + Pour + "','" + "NULL" + "','" + unit + "','" + "NULL" + "')";
    }

}
