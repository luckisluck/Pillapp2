package com.dispenses.pill.pillapp.feature;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;

import static com.dispenses.pill.pillapp.feature.MainActivity.CONNECTION_TIMEOUT;
import static com.dispenses.pill.pillapp.feature.MainActivity.READ_TIMEOUT;

public class showalarm extends AppCompatActivity {

    final String dispense = "dispense";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarmshow);
        TextView mTextView = (TextView) findViewById(R.id.textView15);

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        mTextView.setText(currentDateTimeString);
    }

    public void stooooop(View arg0) {
        Intent i = new Intent(this, RingtonePlayingService.class);
        stopService(i);
        this.finish();
    }

    public void take(View arg0) {
        Intent i = new Intent(this, RingtonePlayingService.class);
        stopService(i);
        new showalarm.AsyncAutoDispense1().execute(dispense);
        this.finish();
    }

    private class AsyncAutoDispense1 extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(showalarm.this);
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
                url = new URL("http://weighty-beach-183107.appspot.com/DispenseAuto.php");

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

                Uri.Builder builder = new Uri.Builder().appendQueryParameter("dispense", params[0]);
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

                    return ("fail");
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
                Toast.makeText(showalarm.this, "Successfully dispense", Toast.LENGTH_LONG).show();
            }

            else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("false")) {

                Toast.makeText(showalarm.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }
}