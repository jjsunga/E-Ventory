package com.example.mike.e_ventory.Activities;

import com.example.mike.e_ventory.*;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class ConfirmPopUpShopPurchase extends AppCompatActivity {

    private String[] sortOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_pop_up_shop_purchase);

        sortOptions();
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

    public void onClickConfirm(View view){
        Toast.makeText( getBaseContext(), "Confirm Pressed Pop Up Shop", Toast.LENGTH_LONG ).show();
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
}