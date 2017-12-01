package com.example.mike.e_ventory;

import com.example.mike.e_ventory.Activities.*;

import android.content.Context;
import android.database.Cursor;
import android.view.MenuItem;
import java.util.ArrayList;

import java.util.List;

/**
 * Created by Mike on 12/3/2016.
 */

public class EVentory{

    public static Class optionsMenu(MenuItem item){

        switch (item.getItemId()) {
            case R.id.searchProduct:
                return Search.class;
            case R.id.notifications:
                return Notifications.class;
            case R.id.addProduct:
                return AddProduct.class;
            case R.id.stats:
                return Statistics.class;
            case R.id.preferences:
                return Preferences.class;
        }
        return null;
    }

    public static ArrayList<ListItemProd> getSortedList(int sortBy, Context context){

        DBAdapter db = new DBAdapter(context);
        Cursor c;
        ArrayList<ListItemProd> prodList = new ArrayList<ListItemProd>();

        if(sortBy == 1){
            db.open();
            c = db.getAllOrderedBy("price");
        }
        else if(sortBy == 2){
            db.open();
            c = db.getAllOrderedBy("quantity");
        }
        else {
            db.open();
            c = db.getAllOrderedBy("title");
        }

        if(c.moveToFirst()){
            do{
                ListItemProd item = new ListItemProd(c.getString(1),
                        Double.parseDouble(c.getString(2)), Integer.parseInt(c.getString(4)));
                prodList.add(item);
            }while(c.moveToNext());
        }
        db.close();

        return prodList;

    }
}
