package com.example.mike.e_ventory.Activities;

import com.example.mike.e_ventory.*;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Notifications extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        TextView notes = (TextView) findViewById(R.id.notes);

        String notifications="";

        DBAdapter db = new DBAdapter(this);



        //---query the database: get all contacts---
        db.open();

        Cursor c = db.getAllInventory();

        if ( c.moveToFirst() )
        {
            do {
                if(!getBelowTen(c).equals(""))
                    notifications += "The inventory item "+ getBelowTen(c) + " has 10 or less items left in stock\n";
            } while (c.moveToNext());
        }
        db.close();
        if(!notifications.equals(""))
            notes.setText(notifications);
        else
            notes.setText("All products have more than 10 in stock.");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() != R.id.notifications)
            startActivity(new Intent(this, EVentory.optionsMenu(item)));
        return true;
    }

    private void copyDB( InputStream inputStream,
                         OutputStream outputStream ) throws IOException {
        //---copy 1K bytes at a time---
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        inputStream.close();
        outputStream.close();

        Log.i( "Database", "copying done" );
    }

    private String getBelowTen(Cursor c)
    {

        String quantity = c.getString(4);
        String title=""; //none by default unless quantity found is less than 10

        int intQuantity = Integer.parseInt(quantity);

        if(intQuantity < 10){
            title = c.getString(5);
        }
        return title;
    }
}