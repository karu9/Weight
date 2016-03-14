package com.example.pichery.weight;

import android.test.AndroidTestCase;

import junit.framework.TestCase;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class TestCalc extends AndroidTestCase{
    @Test
    public void testCalculatePointWeight(){
        int points = Calc.calculatePointWeight(38f, 120f, 0.70f, true);
        assertEquals(46, points);
    }
}