package com.example.mike.e_ventory.SwipeTabAdapters;

import com.example.mike.e_ventory.Fragments.*;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SwipeTabAdapterStats extends FragmentPagerAdapter {

    public SwipeTabAdapterStats(FragmentManager fm){ super(fm); }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new All();
        }
        else if(position == 1){
            return new Amazon();
        }
        else if(position == 2){
            return new Ebay();
        }
        else{
            return new Etsy();
        }
    }

    @Override
    public int getCount(){
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "All";
            case 1:
                return "Amazon";
            case 2:
                return "Ebay";
            case 3:
                return "Etsy";
        }
        return null;
    }
}