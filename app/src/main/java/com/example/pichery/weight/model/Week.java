package com.example.pichery.weight.model;

import android.database.Cursor;

import com.example.pichery.weight.util.DBUtils;
import com.example.pichery.weight.util.DateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Doudouz on 24/03/2016.
 */
public class Week {
    private Date dateEnd;
    private Date dateStart;

    public Week(Cursor cursor){
        dateStart = DateUtil.getDate(cursor.getString(0));
        dateEnd = DateUtil.getDate(cursor.getString(1));
    }

    private Week(Date dateStart, Date dateEnd){
        this.dateEnd = dateEnd;
        this.dateStart = dateStart;
    }

    public static Week getWeek(Date date, List<Week> weeks, DBUtils dbUtil){
        for(Week week : weeks){
            if(date.after(week.dateStart) && date.before(week.dateEnd)) {
                return week;
            }
        }
        Week week = Week.createWeek(dbUtil);
        return week;
    }

    public static Week createWeek(DBUtils dbUtils){
        // Get calendar set to current date and time
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 6);
        dbUtils.execute(createWeek(new Date(), c.getTime()));
        return new Week(new Date(), c.getTime());
    }

    public static String getQuery(){
        return "Select * from Week";
    }

    public static String createWeek(Date dateStart, Date dateEnd){
        return "Insert into Week VALUES ('" + DateUtil.formatDate(dateStart) + "','" + DateUtil.formatDate(dateEnd) + "')" ;
    }

    public Date getDateStart(){
        return dateStart;
    }

    public Date getDateEnd(){
        return dateEnd;
    }
}
