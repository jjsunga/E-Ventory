package com.example.mike.e_ventory.Fragments;

import com.example.mike.e_ventory.*;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Ebay extends Fragment {

    public Ebay(){}

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_ebay, container, false);
    }
}