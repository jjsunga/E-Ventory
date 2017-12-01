package com.example.mike.e_ventory.Activities;

import com.example.mike.e_ventory.*;
import com.example.mike.e_ventory.SwipeTabAdapters.SwipeTabAdapterStats;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class Statistics extends AppCompatActivity {

    private String[] sortOptions;

    private SwipeTabAdapterStats statsTabAdapter;
    private ViewPager vPager;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        sortOptions();
        tabs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() != R.id.stats)
            startActivity(new Intent(this, EVentory.optionsMenu(item)));
        return true;
    }

    //Sort Options Dropdown.
    public void sortOptions(){
        sortOptions = getResources().getStringArray(R.array.sortOptions);

        Spinner s1 = (Spinner) findViewById(R.id.sortOption);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_single_choice, sortOptions);

        s1.setAdapter(adapter);

        s1.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected( AdapterView<?> arg0, View arg1, int arg2, long arg3 )
            {
                int index = arg0.getSelectedItemPosition();

                Toast.makeText( getBaseContext(), "Selected item : "
                        + sortOptions[index], Toast.LENGTH_LONG ).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) { }
        });

    }

    //Swipe Tabs.
    public void tabs(){
        statsTabAdapter = new SwipeTabAdapterStats(getSupportFragmentManager());

        vPager = (ViewPager) findViewById(R.id.container);
        vPager.setAdapter(statsTabAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(vPager);
    }
}
