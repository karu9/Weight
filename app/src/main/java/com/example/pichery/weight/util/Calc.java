package com.example.pichery.weight.util;

import android.util.Log;

/**
 * Created by pichery on 28/11/15.
 */
public class Calc {
    public static int calculatePointWeight(float weight, boolean female){
        float calories = (2200f + (weight-75f) * 18f) * 0.70f;
        if(female){
            calories = (1959f + (weight-75f) * 12f) * 0.70f;
        }
        return Math.round(calories / 38f);
    }

    public static int calculatePoints(float calories){
        return(Math.round(calories / 38f));
    }

    public static float calculateCalories(float baseCalories, float baseWeight, float expectedWeight){
        return baseCalories * (expectedWeight / baseWeight);
    }

    public static int calculatePoint(float baseCalories, float baseWeight, float expectedWeight){
        return calculatePoints(calculateCalories(baseCalories, baseWeight, expectedWeight));
    }
}
