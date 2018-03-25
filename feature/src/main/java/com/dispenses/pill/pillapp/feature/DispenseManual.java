package com.dispenses.pill.pillapp.feature;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import static com.dispenses.pill.pillapp.feature.MainActivity.CONNECTION_TIMEOUT;
import static com.dispenses.pill.pillapp.feature.MainActivity.READ_TIMEOUT;

/**
 * Created by Win10 on 10/02/2018.
 */

public class DispenseManual extends AppCompatActivity {

    CheckBox fee_checkbox1;
    CheckBox fee_checkbox2;
    CheckBox fee_checkbox3;
    EditText pillAmt1;
    EditText pillAmt2;
    EditText pillAmt3;
    String keyx="nameBX";
    String keyY="nameBY";
    String keyZ="nameBZ";
    String getX;
    String getY;
    String getZ;
    int box1=0;
    int box2=0;
    int box3=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dispense);

        fee_checkbox1 = (CheckBox)findViewById(R.id.checkBox);
        fee_checkbox2 = (CheckBox)findViewById(R.id.checkBox2);
        fee_checkbox3 = (CheckBox)findViewById(R.id.checkBox3);
        pillAmt1 = (EditText) findViewById(R.id.editText6);
        pillAmt2 = (EditText) findViewById(R.id.editText7);
        pillAmt3 = (EditText) findViewById(R.id.editText8);

        getX=getDefaults(keyx,this);
        final TextView mTextView = (TextView) findViewById(R.id.checkBox);
        mTextView.setText(getX);

        getY=getDefaults(keyY,this);
        final TextView mTextView1 = (TextView) findViewById(R.id.checkBox2);
        mTextView1.setText(getY);

        getZ=getDefaults(keyZ,this);
        final TextView mTextView2 = (TextView) findViewById(R.id.checkBox3);
        mTextView2.setText(getZ);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if( item.getItemId() == R.id.ic_home )
                {
                    Intent intent1 = new Intent(DispenseManual.this, home.class);
                    startActivity(intent1);
                }

                else if( item.getItemId() == R.id.ic_schedule )
                {
                    Intent intent1 = new Intent(DispenseManual.this, schedule.class);
                    startActivity(intent1);
                }


                else if( item.getItemId() == R.id.ic_dispense )
                {
                    Intent intent1 = new Intent(DispenseManual.this, DispenseManual.class);
                    startActivity(intent1);
                }

                else if( item.getItemId() == R.id.ic_history )
                {
                    Intent intent1 = new Intent(DispenseManual.this, History.class);
                    startActivity(intent1);
                }

                else if( item.getItemId() == R.id.ic_setting )
                {
                    Intent intent1 = new Intent(DispenseManual.this, Setting.class);
                    startActivity(intent1);
                }

                return false;
            }
        });





    }

    public void checkbox1(View v)
    {

        if(fee_checkbox1.isChecked())
            {
            box1=1;

            }

        else
            {
                box1=0;
            }

    }

    public void checkbox2(View v)
    {

        if(fee_checkbox2.isChecked())
        {
            box2=1;

        }

        else
        {
            box2=0;
        }

    }

    public void checkbox3(View v)
    {

        if(fee_checkbox3.isChecked())
        {
            box3=1;

        }

        else
        {
            box3=0;
        }

    }

    public void disman(View v)
    {

      if(box1 == 1)
      {
          final String pillAmtx = pillAmt1.getText().toString();
          final String columnX ="pillAmt_X";
          final String tablex ="dispenseX_auto";
          new DispenseManual.AsyncManual().execute(pillAmtx,tablex,columnX);

      }


        if(box2 == 1)
        {
            final String pillAmty = pillAmt2.getText().toString();
            final String tabley ="dispenseY_auto";
            final String columnY ="pillAmt_Y";
            new DispenseManual.AsyncManual().execute(pillAmty,tabley,columnY);

        }

        if(box3 == 1)
        {
            final String pillAmtz = pillAmt3.getText().toString();
            final String tablez ="dispenseZ_auto";
            final String columnZ ="pillAmt_Z";
            new DispenseManual.AsyncManual().execute(pillAmtz,tablez,columnZ);

        }

    }

    private class AsyncManual extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(DispenseManual.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL("http://weighty-beach-183107.appspot.com/DispenseManual.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("pillAmt", params[0])
                        .appendQueryParameter("table", params[1])
                        .appendQueryParameter("Cname", params[2]);
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {
                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return(result.toString());
                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }


        }
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();

            if(Objects.equals(result, "true")) {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */
                Toast.makeText(DispenseManual.this, "Work.", Toast.LENGTH_LONG).show();
            }

            else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("false")) {

                Toast.makeText(DispenseManual.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }


    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, "Edit Name Inside Schedule");
    }




}
