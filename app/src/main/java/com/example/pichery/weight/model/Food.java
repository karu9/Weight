package com.example.pichery.weight.model;

import android.database.Cursor;

import com.example.pichery.weight.util.DateUtil;

import java.util.Date;

/**
 * Created by Doudouz on 24/03/2016.
 */
public class Food {
    private String name;
    private String baseCalories;
    private String baseWeight;
    private String type;
    private String barCode;

    public Food(Cursor cursor){
        name = cursor.getString(0);
        baseCalories = cursor.getString(1);
        baseWeight = cursor.getString(2);
        type = cursor.getString(3);
        barCode = cursor.getString(4);
    }

    public static String getQuery(){
        return "Select * from Food";
    }

    public String createFood(String name, String baseCalories, String baseWeight){
        return "Insert into Food VALUES = (\"" + name + "\",\")" + baseCalories + "\",\")" + baseWeight + "\",\")";
    }

}
