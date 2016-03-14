package com.example.pichery.weight.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pichery.weight.model.Profile;
import com.example.pichery.weight.model.Weight;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pichery on 29/11/15.
 */
public class DBUtils extends SQLiteAssetHelper{
    public SQLiteDatabase myDataBase;

    public static enum FoodElement{
        Name, baseCalories, BaseWeight, Unit
    }

    public static enum DayElement{
        DayId, Date, NumberOfPoints, WeekId
    }

    public static enum ConsumedFoodElement{
        DayId, Name, Points
    }

    public static enum WeightElement{
        NumberOfSportPoints, NumberOfHebdoPoints, DateEnd, DateStart, WeekId
    }


    public DBUtils (Context context){
        this(context, "weight.db", null, 1);
    }
    public DBUtils(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //Open database
    public void openReadableDatabase() throws SQLException{
        myDataBase = getReadableDatabase();
    }

    //Open database
    public void openWritableDatabase() throws SQLException{
        myDataBase = getWritableDatabase();
    }

    public void closeDatabase(){
        if(myDataBase != null){
            myDataBase.close();
        }
        myDataBase = null;
    }

    public Profile loadProfile(){
        try{
            openReadableDatabase();
            Cursor cursor = myDataBase.rawQuery(Profile.getQuery(), null);
            cursor.moveToFirst();
            Profile profile = new Profile(cursor);
            cursor.close();
            return profile;
        }
        catch (SQLException e) {
            return null;
        }
        finally{
            closeDatabase();
        }
    }

    public List<Weight> loadWeights(){
        List<Weight> ret = new ArrayList<Weight>();
        try{
            openReadableDatabase();
            Cursor cursor = myDataBase.rawQuery(Weight.getQuery(), null);
            while(cursor.moveToNext()){
                ret.add(new Weight(cursor));
            }
            cursor.close();
            return ret;
        }
        catch (SQLException e) {
            return null;
        }
        finally{
            closeDatabase();
        }
    }

    public void execute(String query){
        try{
            openWritableDatabase();
            myDataBase.execSQL(query);
        }
        catch (SQLException e) {
        }
        finally{
            closeDatabase();
        }
    }
}
