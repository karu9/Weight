package com.example.pichery.weight.model;

import android.database.Cursor;

/**
 * Created by Doudouz on 24/03/2016.
 */
public class Sport implements Comparable<Sport>{
    private String name;
    private String baseTime;
    private String baseCalories;

    public Sport(Cursor cursor){
        name = cursor.getString(0);
        baseTime = cursor.getString(1);
        baseCalories = cursor.getString(2);
    }

    public static String getQuery(){
        return "Select * from Sport";
    }

    public static String searchSport(String search){
        if(search == null){
            search = "";
        }
        return "Select * from Sport where name like'%" + search + "%'";
    }

    @Override
    public int compareTo(Sport another) {
        return this.name.toLowerCase().compareTo(another.name.toLowerCase());
    }

    public String getName(){
        return name;
    }

    public String getBaseCalories(){
        return baseCalories;
    }

    public String getBaseTime(){
        return baseTime;
    }


    public static String addSportQuery(String name, String time, String calories){
        return "Insert into Sport VALUES ('" + name + "','" + time + "','" + calories +"')";
    }

    public static String getRemoveQuery(Sport sport) {
        return "DELETE From Sport where name ='" + sport.name + "' and BaseCalories = '" + sport.getBaseCalories() + "' and baseTime = '" + sport.getBaseTime() + "'";
    }

    public static String modifySport(Sport food, String nameText, String caloriesText, String timeText) {
        return "UPDATE Food Set name='" + nameText + "', BaseCalories = '" + caloriesText + "', baseTime = '" + timeText + "' where name ='" + food.name + "' and BaseCalories = '" + food.getBaseCalories() + "' and baseTime = '" + food.getBaseTime() + "'";
    }
}
