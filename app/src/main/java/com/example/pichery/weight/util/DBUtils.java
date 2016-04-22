package com.example.pichery.weight.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pichery.weight.model.ConsumedFood;
import com.example.pichery.weight.model.Food;
import com.example.pichery.weight.model.Profile;
import com.example.pichery.weight.model.Sport;
import com.example.pichery.weight.model.Week;
import com.example.pichery.weight.model.Weight;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pichery on 29/11/15.
 */
public class DBUtils extends SQLiteAssetHelper{
    public SQLiteDatabase myDataBase;

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
            Cursor cursor = query(Profile.getQuery());
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

    private Cursor query(String query) {
        return myDataBase.rawQuery(query, null);
    }

    public List<Weight> loadWeights(){
        List<Weight> ret = new ArrayList<Weight>();
        try{
            openReadableDatabase();
            Cursor cursor = query(Weight.getQuery());
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

    public List<ConsumedFood> loadConsumedFood(Date date){
        List<ConsumedFood> ret = new ArrayList<ConsumedFood>();
        try{
            openReadableDatabase();
            Cursor cursor = query(ConsumedFood.getQuery(date));
            while(cursor.moveToNext()){
                ret.add(new ConsumedFood(cursor));
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

    public List<Week> loadWeeks(){
        List<Week> ret = new ArrayList<Week>();
        try{
            openReadableDatabase();
            Cursor cursor = query(Week.getQuery());
            while(cursor.moveToNext()){
                ret.add(new Week(cursor));
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

    public List<Food> loadFood(){
        return this.loadFood(Food.getQuery());
    }

    public List<Food> loadFood(String query){
        List<Food> ret = new ArrayList<Food>();
        try{
            openReadableDatabase();
            Cursor cursor = query(query);
            while(cursor.moveToNext()){
                ret.add(new Food(cursor));
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

    public List<Sport> loadSport(String query) {
        List<Sport> ret = new ArrayList<Sport>();
        try{
            openReadableDatabase();
            Cursor cursor = query(query);
            while(cursor.moveToNext()){
                ret.add(new Sport(cursor));
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
}
