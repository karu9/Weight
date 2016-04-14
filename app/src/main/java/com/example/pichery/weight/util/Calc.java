package com.example.pichery.weight.util;

import android.util.Log;

/**
 * Created by pichery on 28/11/15.
 */
public class Calc {
    public static int calculatePointWeight(float weight, boolean female){
        float sexFactor = 2225f;
        if(female) {
            sexFactor = 1952f;
        }
        float weightFactor = (float) Math.pow(weight, 0.48);
        float WWFactor = 0.7f;

        return Math.round(weightFactor * sexFactor * WWFactor / 380f);
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
