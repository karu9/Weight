package com.example.pichery.weight.model;

import android.database.Cursor;

/**
 * Created by pichery on 06/12/15.
 */
public class Profile {
    private String name;
    private String sex;

    public Profile(Cursor cursor){
        name = cursor.getString(0);
        sex = cursor.getString(1);
    }

    public static String getQuery(){
        return "Select * from Profile";
    }

    public String getName(){
        return name;
    }

    public String setName(String name){
        return "Update profile set name = \"" + name + "\"";
    }

    public String getSex(){
        return sex;
    }

    public String setSex(String gender){
        return "Update profile set Sex = \"" + gender + "\"";
    }

}
