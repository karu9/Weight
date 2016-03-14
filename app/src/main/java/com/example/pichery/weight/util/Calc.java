package com.example.pichery.weight.util;

import android.util.Log;

/**
 * Created by pichery on 28/11/15.
 */
public class Calc {
    public static int calculatePointWeight(float convertionRate, float weight, float caloricFactor, boolean female){
        float calories = 2200f + (weight-75f) * 18f * caloricFactor;
        if(female){
            calories = 1959f + (weight-75f) * 12f * caloricFactor;
        }
        return Math.round(calories / convertionRate);
    }

    public static int calculatePoints(float convertionRate, float calories){
        return(Math.round(calories / convertionRate));
    }

    public static float calculateCalories(float baseCalories, float baseWeight, float expectedWeight){
        return baseCalories * (expectedWeight / baseWeight);
    }

    public static int calculatePoint(float baseCalories, float baseWeight, float expectedWeight, float conversationRate){
        return calculatePoints(conversationRate, calculateCalories(baseCalories, baseWeight, expectedWeight));
    }
}
