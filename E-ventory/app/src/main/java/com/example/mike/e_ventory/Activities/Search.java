package com.example.mike.e_ventory.Activities;

import com.example.mike.e_ventory.*;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;


public class Search extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        String add = "";

        List<String> search = new ArrayList<String>();

        DBAdapter db = new DBAdapter(this);

        //---query the database: get all contacts---
        db.open();

        Cursor c = db.getAllInventory();

        if ( c.moveToFirst() )
        {
            do {
                add += c.getString(1)+"  " + c.getString(2) +"  "+ c.getString(5);
                search.add(add);
                add = "";
            } while (c.moveToNext());
        }

        db.close();

        String[] stringSearch = new String[search.size()];
        stringSearch = search.toArray(stringSearch);



        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, terminology);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, stringSearch);
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.searchterm);

        textView.setThreshold(4); //minimum characters to create suggestion set to 2 accoridng to lab

        textView.setAdapter(adapter); // connects the array adapter made above to autocompletetextview.
    }

    public void onClickSearch(View view) {

        String add = "";

        List<String> search = new ArrayList<String>();

        DBAdapter db = new DBAdapter(this);

        //---query the database: get all contacts---
        db.open();

        Cursor c = db.getAllInventory();

        if ( c.moveToFirst() )
        {
            do {
                add += c.getString(1)+"  " + c.getString(2) +"  "+ c.getString(5);
                search.add(add);
                add ="";
            } while (c.moveToNext());
        }

        db.close();



        AutoCompleteTextView text = (AutoCompleteTextView) findViewById(R.id.searchterm); //get autcomplete textview (what is entered)
        TextView displaySearch = (TextView) findViewById(R.id.results); //get textview to place defintiion in

        String found = ""; //found a defintion
        //int definitionLocation = -1; //location of stored definition
        Toast entered = Toast.makeText(this, "YOU'VE ENTERED: " + text.getText().toString(), Toast.LENGTH_SHORT); //make toast for value entered



        for(int i = 0; i<search.size() ; i++){ //look for value entered
            if((search.toArray()[i].toString()).contains(text.getText().toString())){
                found += search.get(i) + "\n";
            }
        }

        //if(found) {
        displaySearch.setText(found); //display defintion
        //}else{
        //    displaySearch.setText("Term Entered Is Not Listed"); //no defintion display else
        //}

        entered.show(); //show toast

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() != R.id.searchProduct)
            startActivity(new Intent(this, EVentory.optionsMenu(item)));
        return true;
    }

}