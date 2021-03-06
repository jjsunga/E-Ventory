package com.example.mike.e_ventory.Activities;

import com.example.mike.e_ventory.*;
import com.example.mike.e_ventory.SwipeTabAdapters.SwipeTabAdapterMain;
import android.support.design.widget.TabLayout;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Main extends AppCompatActivity {

    private String[] sortOptions;
    private SwipeTabAdapterMain tabSelectAdapter;
    private ViewPager vPager;
    private Intent sortIntent;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sortIntent = new Intent("MainActivity");

        bundle = new Bundle();

        bundle.putInt("sortBy", 1);
        sortIntent.putExtras(bundle);

        sortOptions();
        tabs();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tabs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(this, EVentory.optionsMenu(item)));
        return true;
    }

    //Sort Options Dropdown.
    public void sortOptions() {
        sortOptions = getResources().getStringArray(R.array.sortOptions);

        Spinner s1 = (Spinner) findViewById(R.id.sortOption);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_single_choice, sortOptions);

        s1.setAdapter(adapter);

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                int index = arg0.getSelectedItemPosition();

                bundle.putInt("sortBy", index);

                sortIntent.putExtras(bundle);

                tabs();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    //Swipe Tabs.
    public void tabs() {
        vPager = (ViewPager) findViewById(R.id.container);
        int pos = vPager.getCurrentItem();

        tabSelectAdapter = new SwipeTabAdapterMain(getSupportFragmentManager());
        tabSelectAdapter.setSortIntent(sortIntent);

        vPager.setAdapter(tabSelectAdapter);
        vPager.setCurrentItem(pos);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(vPager);
    }

    public void onClickConfirm(View view) {
        startActivity(new Intent(this, ConfirmPopUpShopPurchase.class));
    }
}
