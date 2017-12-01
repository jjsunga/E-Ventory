package com.example.mike.e_ventory.Activities;

import com.example.mike.e_ventory.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class AddProduct extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() != R.id.addProduct)
            startActivity(new Intent(this, EVentory.optionsMenu(item)));
        return true;
    }

    public void onClickConfirmAdd(View view){
        SharedPreferences.Editor sharedPrefEdit = getSharedPreferences("newProdPref",
                                                                MODE_PRIVATE).edit();

        EditText prodNameET = (EditText) findViewById(R.id.prodET);
        EditText descET = (EditText) findViewById(R.id.descET);
        EditText priceET = (EditText) findViewById(R.id.priceET);
        EditText qtyET = (EditText) findViewById(R.id.qtyET);
        CheckBox etsyCB = (CheckBox) findViewById(R.id.vendorEtsyCB);
        CheckBox shopifyCB = (CheckBox) findViewById(R.id.vendorEbayCB);

        String prodName = prodNameET.getText().toString();
        String desc = descET.getText().toString();
        float price = 0;
        int qty = 0;
        boolean noErr = true;

        try {
            price = Float.parseFloat(priceET.getText().toString());
        }catch (NumberFormatException e){
            noErr = false;
        }

        try {
            qty = Integer.parseInt(qtyET.getText().toString());
        }catch (NumberFormatException e){
            noErr = false;
        }

        String vendor = "";

        if(etsyCB.isChecked() && shopifyCB.isChecked()){
            vendor = "Both";
        }
        else if(etsyCB.isChecked()){
            vendor = "Etsy";
        }
        else if(shopifyCB.isChecked()){
            vendor = "Shopify";
        }

        if(prodName == "" || desc == "" || vendor == ""){
            noErr = false;
        }

        prodNameET.setText("");
        descET.setText("");
        priceET.setText("");
        qtyET.setText("");
        etsyCB.setChecked(false);
        shopifyCB.setChecked(false);

        sharedPrefEdit.putString("prodName", prodName);
        sharedPrefEdit.putString("desc", desc);
        sharedPrefEdit.putFloat("price", price);
        sharedPrefEdit.putInt("qty", qty);
        sharedPrefEdit.putString("vendor", vendor);

        sharedPrefEdit.apply();

        if(postProduct() && noErr){
            DBAdapter db = new DBAdapter(this);
            db.open();
            db.insertInventory(prodName, price, null, qty, desc, vendor, null, null );
            db.close();
            Toast.makeText( getBaseContext(), "Item has been added.", Toast.LENGTH_LONG ).show();
        }
        else{
            Toast.makeText( getBaseContext(), "Unable to add item.", Toast.LENGTH_LONG ).show();
        }
    }

    boolean postProduct(){
        return true;
    }
}
