package com.example.mike.e_ventory.Fragments;

import com.example.mike.e_ventory.*;
import com.example.mike.e_ventory.ListAdapters.ListAdapterPopUpShop;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class PopUpShop extends Fragment{

    private Intent sortOrder;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState){
        ArrayList<ListItemProd> prodList;
        ListView listView;

        prodList = EVentory.getSortedList(sortOrder.getExtras().getInt("sortBy"), getContext());

        View v = inflater.inflate(R.layout.fragment_pop_up_shop, container, false);
        listView = (ListView) v.findViewById(R.id.itemlist);

        ListAdapterPopUpShop adapter = new ListAdapterPopUpShop(getContext(), R.layout.item_product,
                prodList);

        listView.setAdapter(adapter);

        return v;
    }
    public void setSortIntent(Intent intent){
        sortOrder = intent;
    }

}
