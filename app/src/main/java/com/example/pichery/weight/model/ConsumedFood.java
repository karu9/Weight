package com.example.pichery.weight.model;

import android.database.Cursor;

import com.example.pichery.weight.util.DateUtil;

import java.util.Date;

/**
 * Created by Doudouz on 24/03/2016.
 */
public class ConsumedFood {
    private String date;
    private String name;
    private String points;

    public ConsumedFood(Cursor cursor){
        date = cursor.getString(0);
        name = cursor.getString(1);
        points = cursor.getString(2);
    }

    public static String getQuery(){
        return "Select * from ConsumedFood";
    }

    public String createFood(String name, String points){
        return "Insert into ConsumedFood VALUES = (\"" + DateUtil.formatDate(new Date()) + "\",\")" + name + "\",\")" + points + "\",\")";
    }


}
