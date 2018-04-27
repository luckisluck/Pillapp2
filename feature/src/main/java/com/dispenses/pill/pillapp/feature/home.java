package com.dispenses.pill.pillapp.feature;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
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
import android.os.Handler;
import android.app.ProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.dispenses.pill.pillapp.feature.MainActivity.CONNECTION_TIMEOUT;
import static com.dispenses.pill.pillapp.feature.MainActivity.READ_TIMEOUT;


/**
 * Created by Win10 on 10/02/2018.
 */

public class home extends AppCompatActivity {

    String keyx="nameBX";
    String keyY="nameBY";
    String keyZ="nameBZ";
    String getX;
    String getY;
    String getZ;
    String Temperature;
    String Humidity;
    String TempStore="TempStore";
    String HumidStore="HumidStore";
    String PillStoreX="PillStoreX";
    String PillStoreY="PillStoreY";
    String PillStoreZ="PillStoreZ";
    String pillAmtZ;
    String pillAmtY;
    String pillAmtX;
    String getTemp;
    String getHumid;
    String getpillAmtZ;
    String getpillAmtY;
    String getpillAmtX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        new AsyncGetTempHumid().execute();
        new AsyncGetPillAmt().execute();

        runOnUiThread(new Runnable() {
            public void run() {

                getX=getDefaults(keyx,home.this);
                final TextView mTextView = (TextView) findViewById(R.id.textView8);
                mTextView.setText(getX);
                mTextView.invalidate();

                getY=getDefaults(keyY,home.this);
                final TextView mTextView1 = (TextView) findViewById(R.id.textView9);
                mTextView1.setText(getY);
                mTextView1.invalidate();

                getZ=getDefaults(keyZ,home.this);
                final TextView mTextView2 = (TextView) findViewById(R.id.textView11);
                mTextView2.setText(getZ);
                mTextView2.invalidate();

                getTemp=getDefaults(TempStore,home.this);
                final TextView mTextView3 = (TextView) findViewById(R.id.tempshow);
                mTextView3.setText(getTemp+"°C");
                mTextView3.invalidate();

                getHumid=getDefaults(HumidStore,home.this);
                final TextView mTextView4 = (TextView) findViewById(R.id.humidshow);
                mTextView4.setText(getHumid+"°C");
                mTextView4.invalidate();

                getpillAmtX=getDefaults(PillStoreX,home.this);
                final TextView mTextView5 = (TextView) findViewById(R.id.textView12);
                mTextView5.setText(":"+getpillAmtX+" pills");
                mTextView5.invalidate();

                getpillAmtY=getDefaults(PillStoreY,home.this);
                final TextView mTextView6 = (TextView) findViewById(R.id.textView13);
                mTextView6.setText(":"+getpillAmtY+" pills");
                mTextView6.invalidate();


                getpillAmtZ=getDefaults(PillStoreZ,home.this);
                final TextView mTextView7 = (TextView) findViewById(R.id.textView14);
                mTextView7.setText(":"+getpillAmtZ+" pills");
                mTextView7.invalidate();

            }
        });


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if( item.getItemId() == R.id.ic_home )
                {
                    Intent intent1 = new Intent(home.this, home.class);
                    startActivity(intent1);
                    finish();
                }

                else if( item.getItemId() == R.id.ic_schedule )
                {
                    Intent intent1 = new Intent(home.this, schedule.class);
                    startActivity(intent1);
                    finish();
                }


                else if( item.getItemId() == R.id.ic_dispense )
                {
                    Intent intent1 = new Intent(home.this, DispenseManual.class);
                    startActivity(intent1);
                    finish();
                }

                else if( item.getItemId() == R.id.ic_history )
                {
                    Intent intent1 = new Intent(home.this, History.class);
                    startActivity(intent1);
                    finish();
                }

                else if( item.getItemId() == R.id.ic_setting )
                {
                    Intent intent1 = new Intent(home.this, Setting.class);
                    startActivity(intent1);
                    finish();
                }

                return false;
            }
        });

    }




    public void dispenseplz(View arg0) {

        final String dispense = "dispense";
        new AsyncAutoDispense().execute(dispense);

    }

    public void notitemphumid(String messageTitle,String message) {

        int NOTIFICATION_ID = 234;
        String CHANNEL_ID = "my_channel_01";

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "my_channel";
            String Description = "This is my channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(messageTitle)
                .setContentText(message);

        Intent resultIntent = new Intent(this, home.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(home.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);

        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }

    private class AsyncAutoDispense extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(home.this);
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
                Toast.makeText(home.this, "Successfully dispense", Toast.LENGTH_LONG).show();
            }

            else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("false")) {

                Toast.makeText(home.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }


    private class AsyncGetTempHumid extends AsyncTask<Void, Void, String> {
        ProgressDialog pdLoading = new ProgressDialog(home.this);
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
        protected String doInBackground(Void... voids) {
            String result = null;
            try {
                URL url = new URL("http://weighty-beach-183107.appspot.com/returnTempHumid.php");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                result = inputStreamToString(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pdLoading.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("all");
                for (int i = 0; i < jsonArray.length(); i++) {
                    Temperature = jsonArray.getJSONObject(i).getString("Temperature");
                    Humidity = jsonArray.getJSONObject(i).getString("Humidity");
                }

                setDefaultstemphumid(TempStore,Temperature,home.this);
                setDefaultstemphumid(HumidStore,Humidity,home.this);
                int tempcheck = Integer.parseInt(Temperature);
                int humidcheck = Integer.parseInt(Humidity);

                if( tempcheck > 100  )
                {
                    String title = "Dispenser is too hot !!!";
                    String message = "Dispenser is getting overheated please place it somewhere colder";
                    notitemphumid(title,message);
                }

                if(humidcheck > 100)
                {
                    String title = "Dispenser is too humid !!!";
                    String message = "Dispenser is getting too humid please place it somewhere less humid";
                    notitemphumid(title,message);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }


    private class AsyncGetPillAmt extends AsyncTask<Void, Void, String> {
        ProgressDialog pdLoading = new ProgressDialog(home.this);
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
        protected String doInBackground(Void... voids) {
            String result = null;
            try {
                URL url = new URL("http://weighty-beach-183107.appspot.com/getpillAmt.php");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                result = inputStreamToString(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pdLoading.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("allp");
                for (int i = 0; i < jsonArray.length(); i++) {
                    pillAmtX = jsonArray.getJSONObject(i).getString("pillAmtX");
                    pillAmtY = jsonArray.getJSONObject(i).getString("pillAmtY");
                    pillAmtZ = jsonArray.getJSONObject(i).getString("pillAmtZ");
                }


                setDefaultstemphumid(PillStoreX,pillAmtX,home.this);
                setDefaultstemphumid(PillStoreY,pillAmtY,home.this);
                setDefaultstemphumid(PillStoreZ,pillAmtZ,home.this);

                int pillAmtx = Integer.parseInt(pillAmtX);
                int pillAmty = Integer.parseInt(pillAmtY);
                int pillAmtz = Integer.parseInt(pillAmtZ);

                if( pillAmtx < 5  )
                {
                    String title = "Only "+pillAmtX+" pill left";
                    String message ="Only "+pillAmtX+" left in pill bottle "+getX+" .Consider refilling it soon!!" ;
                    notitemphumid(title,message);
                }

                if(pillAmty < 5)
                {
                    String title = "Only "+pillAmtY+" pill left";
                    String message ="Only "+pillAmtY+" left in pill bottle "+getY+" .Consider refilling it soon!!" ;
                    notitemphumid(title,message);
                }

                if(pillAmtz < 5)
                {
                    String title = "Only "+pillAmtZ+" pill left";
                    String message ="Only "+pillAmtZ+" left in pill bottle "+getZ+" .Consider refilling it soon!!" ;
                    notitemphumid(title,message);
                }

                updatetextview();

            } catch (JSONException e) {
                e.printStackTrace();

            }

        }

    }

    public void updatetextview()
    {

        getX=getDefaults(keyx,home.this);
        final TextView mTextView = (TextView) findViewById(R.id.textView8);
        mTextView.setText(getX);
        mTextView.invalidate();

        getY=getDefaults(keyY,home.this);
        final TextView mTextView1 = (TextView) findViewById(R.id.textView9);
        mTextView1.setText(getY);
        mTextView1.invalidate();

        getZ=getDefaults(keyZ,home.this);
        final TextView mTextView2 = (TextView) findViewById(R.id.textView11);
        mTextView2.setText(getZ);
        mTextView2.invalidate();

        getTemp=getDefaults(TempStore,home.this);
        final TextView mTextView3 = (TextView) findViewById(R.id.tempshow);
        mTextView3.setText(getTemp+"°C");
        mTextView3.invalidate();

        getHumid=getDefaults(HumidStore,home.this);
        final TextView mTextView4 = (TextView) findViewById(R.id.humidshow);
        mTextView4.setText(getHumid+"°C");
        mTextView4.invalidate();

        getpillAmtX=getDefaults(PillStoreX,home.this);
        final TextView mTextView5 = (TextView) findViewById(R.id.textView12);
        mTextView5.setText(":"+getpillAmtX+" pills");
        mTextView5.invalidate();

        getpillAmtY=getDefaults(PillStoreY,home.this);
        final TextView mTextView6 = (TextView) findViewById(R.id.textView13);
        mTextView6.setText(":"+getpillAmtY+" pills");
        mTextView6.invalidate();


        getpillAmtZ=getDefaults(PillStoreZ,home.this);
        final TextView mTextView7 = (TextView) findViewById(R.id.textView14);
        mTextView7.setText(":"+getpillAmtZ+" pills");
        mTextView7.invalidate();

    }


    public static String getDefaults(String key, Context context) {

        String Default = "Null";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        switch (key) {
            case "nameBX":
                Default = preferences.getString(key, "Box-X");
                break;
            case "nameBY":
                Default = preferences.getString(key, "Box-Y");
                break;
            case "nameBZ":
                Default = preferences.getString(key, "Box-Z");
                break;

            case "PillStoreX":
                Default = preferences.getString(key, "No Pill Added to this box");
                break;

            case "PillStoreY":
                Default = preferences.getString(key, "No Pill Added to this box");
                break;

            case "PillStoreZ":
                Default = preferences.getString(key, "No Pill Added to this box");
                break;

            case "TempStore":
                Default = preferences.getString(key, "0");
                break;

            case "HumidStore":
                Default = preferences.getString(key, "0");
                break;

        }

        return Default;

    }


    public static void setDefaultstemphumid(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private String inputStreamToString(InputStream is) {
        String rLine = "";
        StringBuilder answer = new StringBuilder();

        InputStreamReader isr = new InputStreamReader(is);

        BufferedReader rd = new BufferedReader(isr);

        try {
            while ((rLine = rd.readLine()) != null) {
                answer.append(rLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer.toString();
    }


}
