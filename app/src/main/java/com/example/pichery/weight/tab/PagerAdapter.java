package com.example.pichery.weight.tab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabHome tab1 = new TabHome();
                return tab1;
            case 1:
                TabCalc tab2 = new TabCalc();
                return tab2;
            case 2:
                TabFood tab3 = new TabFood();
                return tab3;
            case 3:
                TabSport tab4 = new TabSport();
                return tab4;
            case 4:
                TabWeight tab5 = new TabWeight();
                return tab5;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
