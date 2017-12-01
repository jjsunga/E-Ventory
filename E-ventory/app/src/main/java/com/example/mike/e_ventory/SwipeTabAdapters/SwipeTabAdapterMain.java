package com.example.mike.e_ventory.SwipeTabAdapters;

import com.example.mike.e_ventory.Fragments.*;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SwipeTabAdapterMain extends FragmentPagerAdapter {

    private Intent intent;

    public SwipeTabAdapterMain(FragmentManager fm){ super(fm); }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            Inventory i = new Inventory();
            i.setSortIntent(intent);
            return i;
        }
        else{
            PopUpShop p = new PopUpShop();
            p.setSortIntent(intent);
            return p;
        }
    }

    @Override
    public int getCount(){
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Inventory";
            case 1:
                return "Pop Up Shop";
        }
        return null;
    }

    public void setSortIntent(Intent intent){
        this.intent = intent;
    }
}
