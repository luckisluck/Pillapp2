package com.dispenses.pill.pillapp.feature;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Win10 on 10/02/2018.
 */

public class History extends AppCompatActivity {

    MyDBHandler  dbHandler;
    SimpleCursorAdapter simpleCursorAdapter;
    ListView lvProducts;
    String pillAmt_X;
    String time_x;
    String pillAmt_Y;
    String time_Y;
    String pillAmt_Z;
    String time_Z;
    int count =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        dbHandler = new MyDBHandler(History.this, null, null, 1);
        lvProducts = (ListView) findViewById(R.id.historyList);
        new AsyncGetHistory().execute();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if( item.getItemId() == R.id.ic_home )
                {
                    Intent intent1 = new Intent(History.this, home.class);
                    startActivity(intent1);
                }

                else if( item.getItemId() == R.id.ic_schedule )
                {
                    Intent intent1 = new Intent(History.this, schedule.class);
                    startActivity(intent1);
                }


                else if( item.getItemId() == R.id.ic_dispense )
                {
                    Intent intent1 = new Intent(History.this, DispenseManual.class);
                    startActivity(intent1);
                }

                else if( item.getItemId() == R.id.ic_history )
                {
                    Intent intent1 = new Intent(History.this, History.class);
                    startActivity(intent1);
                }

                else if( item.getItemId() == R.id.ic_setting )
                {
                    Intent intent1 = new Intent(History.this, Setting.class);
                    startActivity(intent1);
                }

                return false;
            }
        });

    }

    private void displayHlist() {
        try
        {
            Cursor cursor = dbHandler.gethistory();
            if (cursor == null)
            {
                return;
            }
            if (cursor.getCount() == 0)
            {
                return;
            }

            String[] columns = new String[] {
                    MyDBHandler.COLUMN_pillAmountH,
                    MyDBHandler.COLUMN_TIMEH,
                    MyDBHandler.COLUMN_pillBox,
            };
            int[] boundTo = new int[] {
                    R.id.pillAmtH,
                    R.id.timeH,
                    R.id.boxH,
            };
            simpleCursorAdapter = new SimpleCursorAdapter(this,
                    R.layout.history_list,
                    cursor,
                    columns,
                    boundTo,
                    0);
            lvProducts.setAdapter(simpleCursorAdapter);
        }

        catch (Exception ex)
        {
            Toast.makeText(History.this, "Not working", Toast.LENGTH_LONG).show();
        }
    }

    private class AsyncGetHistory extends AsyncTask<Void, Void, String> {
        ProgressDialog pdLoading = new ProgressDialog(History.this);
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
                URL url = new URL("http://weighty-beach-183107.appspot.com/history.php");
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
                JSONArray jsonArray = jsonObject.getJSONArray("pillX");
                for (int i = 0; i < jsonArray.length(); i++) {
                    pillAmt_X = jsonArray.getJSONObject(i).getString("pillAmt_X");
                    time_x = jsonArray.getJSONObject(i).getString("time_x");

                    try {
                        Cursor cursor = dbHandler.gethistory();
                        if (cursor == null || cursor.getCount() == 0)
                        {
                            historyGetSet p = new historyGetSet("boxX",pillAmt_X,time_x);
                            dbHandler.Addhistory(p);
                            Toast.makeText(History.this, "addded to DB", Toast.LENGTH_LONG).show();
                        }

                        else
                        {
                            if(count == 0) {
                                dbHandler.deleteAllHistory();
                                count = 1;
                            }
                            historyGetSet p = new historyGetSet("boxX",pillAmt_X,time_x);
                            dbHandler.Addhistory(p);
                            Toast.makeText(History.this, "addded to DB", Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(History.this, "didnt add to DB", Toast.LENGTH_LONG).show();
                    }
                }

                JSONArray jsonArrayY = jsonObject.getJSONArray("pillY");
                for (int i = 0; i < jsonArrayY.length(); i++) {
                    pillAmt_Y = jsonArrayY.getJSONObject(i).getString("pillAmt_Y");
                    time_Y = jsonArrayY.getJSONObject(i).getString("time_y");

                    try {
                        historyGetSet p = new historyGetSet("boxY",pillAmt_Y,time_Y);
                        dbHandler.Addhistory(p);
                        Toast.makeText(History.this, "addded to DB Y", Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Toast.makeText(History.this, "didnt add to DB Y", Toast.LENGTH_LONG).show();
                    }
                }


                JSONArray jsonArrayZ = jsonObject.getJSONArray("pillZ");
                for (int i = 0; i < jsonArrayZ.length(); i++) {
                    pillAmt_Z = jsonArrayZ.getJSONObject(i).getString("pillAmt_Z");
                    time_Z = jsonArrayZ.getJSONObject(i).getString("time_z");

                    try {
                        historyGetSet p = new historyGetSet("boxZ",pillAmt_Z,time_Z);
                        dbHandler.Addhistory(p);
                        Toast.makeText(History.this, "addded to DB Y", Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Toast.makeText(History.this, "didnt add to DB Y", Toast.LENGTH_LONG).show();
                    }
                }




            } catch (JSONException e) {
                e.printStackTrace();
            }

            displayHlist();
        }

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
