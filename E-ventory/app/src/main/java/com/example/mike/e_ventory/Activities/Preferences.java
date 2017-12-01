package com.example.mike.e_ventory.Activities;

import com.example.mike.e_ventory.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;


public class Preferences extends AppCompatActivity {

    private SharedPreferences savedState;
    private DBAdapter db = new DBAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        savedState = getSharedPreferences( "shopNames", MODE_PRIVATE );

        String etsyShopName = "";
        String ebayShopName = "";

        ebayShopName = savedState.getString( "ebayShopName" , "" );
        etsyShopName = savedState.getString( "etsyShopName" , "" );



        if(!ebayShopName.equals("") || !etsyShopName.equals("")) {
            EditText editEtsy = (EditText) findViewById(R.id.EtsyShopName);
            EditText editEbay = (EditText) findViewById(R.id.EbayShopName);
            editEbay.setText(ebayShopName);
            editEtsy.setText(etsyShopName);
        }

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


    private String readJSONFeed( String urlString ) {

        StringBuilder stringBuilder = new StringBuilder();

        // 1. HTTP processing
        //    - connect to a web service by using the HTTP GET method

        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet( urlString );

        try {

            Log.d("JSON", "HTTPClinet: execute " + urlString);
            HttpResponse response = client.execute( httpGet );

            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();

            if (statusCode == 200) {

                HttpEntity entity = response.getEntity();

                InputStream content = entity.getContent();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader( content ) );

                String line;

                // 2. Build the JSON string
                while ((line = reader.readLine()) != null) {

                    stringBuilder.append(line);
                }
            } else {
                Log.d( "JSON", "Failed to download file" );
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.e ("JSON", stringBuilder.toString() );
        return stringBuilder.toString();
    }

    /* asynchronous processing: AsyncTask */
    private class etsyJSONFeedTask extends AsyncTask<String, Void, String> { // generic types: <...>

        // 1. invoked on the background thread (i.e. asynchronous processing)!
        protected String doInBackground( String... urls ) {

            return readJSONFeed( urls[0] ); // get the JSON string by web service and return it
        }

        // 2. post-processing
        // - result: the JSON string returned by doInBackground( )
        // - invoked on the UI thread
        protected void onPostExecute( String result ) {

            String title="",price="",currencyCode="",url="",quantity="",description="",state="",listingid="";

            try {

                Log.d( "JSON", result );

                JSONObject jsonObject = new JSONObject( result ); // {

                float f = 0;

                JSONArray jArray = jsonObject.getJSONArray("results");

                for(int i = 0; i<jArray.length(); i++){


                    JSONObject obj = jArray.getJSONObject(i);
                    title = obj.getString("title");
                    price = obj.getString("price");
                    url = obj.getString("url");
                    quantity = obj.getString("quantity");
                    //currencyCode = obj.getString("currency_code");
                    description = obj.getString("description");
                    listingid = obj.getString("listing_id");
                    state = obj.getString("state");

                    db.open();
                    db.insertInventory(title, Float.parseFloat(price), url, Integer.parseInt(quantity),description, "Etsy", listingid, state );
                    db.close();

                }


            } catch ( Exception e ) { Log.d( "JSON", e.toString() ); }


            Toast.makeText(getApplicationContext(), "Finished", Toast.LENGTH_SHORT).show();



            // b) interaction between AsyncTask and UI - update the UI (TextView)
            //textView.setText( finalResult );
        }
    }

    private class ebayJSONFeedTask extends AsyncTask<String, Void, String> { // generic types: <...>

        protected String doInBackground( String... urls ) {

            return readJSONFeed( urls[0] ); // get the JSON string by web service and return it
        }

        protected void onPostExecute( String result ) {

            String title="",price="",currencyCode="",url="",quantity="",description="",state="",listingid="";

            try {

                Log.d( "JSON", result );

                JSONObject jsonObject = new JSONObject( result ); // {

                float f = 0;

                JSONArray jArray = jsonObject.getJSONArray("searchResult");

                for(int i = 0; i<jArray.length(); i++){


                    JSONObject obj = jArray.getJSONObject(i);
                    title = obj.getString("title");
                    price = obj.getString("currentPrice");
                    url = obj.getString("viewItemUrl");
                    //quantity = obj.getString("quantity");
                    //currencyCode = obj.getString("currency_code");
                    //description = obj.getString("description");
                    listingid = obj.getString("itemId");
                    state = obj.getString("sellingState");

                    db.open();
                    db.insertInventory(title, Float.parseFloat(price), url, 0 , "", "Etsy", listingid, state );
                    db.close();

                }


            } catch ( Exception e ) { Log.d( "JSON", e.toString() ); }


            Toast.makeText(getApplicationContext(), "Finished", Toast.LENGTH_SHORT).show();



            // b) interaction between AsyncTask and UI - update the UI (TextView)
            //textView.setText( finalResult );
        }
    }


    public void onClick(View view){

        try {
            String destPath = "/data/data/" + getPackageName() +
                    "/databases";

            File f = new File( destPath );

            if ( !f.exists() ) {

                Log.i( "Database", "create directory: /databases/" );

                f.mkdirs();
                f.createNewFile();

                // copy the database from the assets folder (/assets) into
                // the databases folder on the device (/data/data/<package name>/databases)
                // - database file name at /assets: mydb

                copyDB( getBaseContext().getAssets().open( "mydb" ),
                        new FileOutputStream( destPath + "/MyDB" ) ); // Watch out: UPPERCASE LETTERS
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SharedPreferences.Editor editor = savedState.edit();

        EditText editEtsy = (EditText) findViewById(R.id.EtsyShopName);
        EditText editEbay = (EditText) findViewById(R.id.EbayShopName);

        String etsy ="";
        String ebay ="";

        etsy = editEtsy.getText().toString();
        ebay = editEbay.getText().toString();



        editor.putString( "etsyShopName", etsy );
        editor.putString( "ebayShopName", ebay );
        editor.apply(); // write operation on the preferences file

        BuildRequest request = new BuildRequest();

        //(String platform, String shopName, String updateGetOrAdd, String listingId)

        request.setRequest("etsy", etsy, "get", "");

        Log.i("DISPLAY", request.getRequest());

        new etsyJSONFeedTask().execute( request.getRequest() );

        request.setRequest("ebay", ebay, "get", "");

        new ebayJSONFeedTask().execute( request.getRequest() );

        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();


        //request.setRequest("etsy", etsy, "get", "");

        //new EbayJSonFeedTasl().execute( request.getRequest() );


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



}