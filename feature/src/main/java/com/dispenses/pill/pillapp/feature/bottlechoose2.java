package com.dispenses.pill.pillapp.feature;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
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
 * Created by Win10 on 28/01/2018.
 */

public class bottlechoose2 extends AppCompatActivity {

    FloatingActionButton fab1, fab2,fab3;
    boolean isFABOpen = false;
    private TextView add1,del1,edit1;

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    SimpleCursorAdapter simpleCursorAdapter;
    ListView lvProducts;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottlechoose2);

        dbHandler = new MyDBHandler(this, null, null, 1);
        lvProducts = (ListView) findViewById(R.id.helloY);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        add1 = (TextView) findViewById(R.id.add1);
        del1 = (TextView) findViewById(R.id.del1);
        edit1 = (TextView) findViewById(R.id.edit1);

        Cursor cursor = dbHandler.getallbottley();
        if (cursor == null || cursor.getCount() == 0) {
            new AsyncGetConfig().execute();
        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delScheduley();
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edity();
            }
        });
        displayYlist();
    }


    private void showFABMenu() {
        isFABOpen = true;
        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        fab3.animate().translationY(-getResources().getDimension(R.dimen.standard_155));

        add1 = (TextView) findViewById(R.id.add1);
        del1 = (TextView) findViewById(R.id.del1);
        edit1 = (TextView) findViewById(R.id.edit1);
        add1.setVisibility(View.VISIBLE);
        edit1.setVisibility(View.VISIBLE);
        del1.setVisibility(View.VISIBLE);
        add1.animate().translationY(-getResources().getDimension(R.dimen.standard_65));
        add1.animate().translationX(-getResources().getDimension(R.dimen.standard_65));
        del1.animate().translationY(-getResources().getDimension(R.dimen.standard_110));
        del1.animate().translationX(-getResources().getDimension(R.dimen.standard_65));
        edit1.animate().translationY(-getResources().getDimension(R.dimen.standard_160));
        edit1.animate().translationX(-getResources().getDimension(R.dimen.standard_65));

    }

    private void closeFABMenu() {
        isFABOpen = false;
        fab1.animate().translationY(0);
        fab2.animate().translationY(0);
        fab3.animate().translationY(0);
        add1 = (TextView) findViewById(R.id.add1);
        del1 = (TextView) findViewById(R.id.del1);
        edit1 = (TextView) findViewById(R.id.edit1);

        add1.animate().translationX(0);
        add1.animate().translationY(0);
        del1.animate().translationX(0);
        del1.animate().translationY(0);
        edit1.animate().translationX(0);
        edit1.animate().translationY(0);
        add1.setVisibility(View.INVISIBLE);
        edit1.setVisibility(View.INVISIBLE);
        del1.setVisibility(View.INVISIBLE);

    }

    public void openDialog(){
        popupY_schedule popupTest = new popupY_schedule();
        popupTest.show(getSupportFragmentManager(),"example dialog popup");
    }

    public void delScheduley(){
        popupY_del popupTest = new popupY_del();
        popupTest.show(getSupportFragmentManager(),"example dialog popup");
    }

    public void edity(){
        EditPillY popupTest = new EditPillY();
        popupTest.show(getSupportFragmentManager(),"example dialog popup");
        displayYlist();
    }

    private void displayYlist() {
        try
        {
            Cursor cursor = dbHandler.getallbottley();
            if (cursor == null)
            {
                return;
            }
            if (cursor.getCount() == 0)
            {
                return;
            }
            String[] columns = new String[] {
                    MyDBHandler.COLUMN_NAMEY,
                    MyDBHandler.COLUMN_STARTTIMEY,
                    MyDBHandler.COLUMN_ENDTIMETIMEY,
                    MyDBHandler.COLUMN_PILLAMTY,
            };
            int[] boundTo = new int[] {
                    R.id.Namex,
                    R.id.startx,
                    R.id.endx,
                    R.id.pillamtx,
            };
            simpleCursorAdapter = new SimpleCursorAdapter(this,
                    R.layout.bottley_list,
                    cursor,
                    columns,
                    boundTo,
                    0);
            lvProducts.setAdapter(simpleCursorAdapter);
        }

        catch (Exception ex)
        {
            Toast.makeText(bottlechoose2.this, "version 2", Toast.LENGTH_LONG).show();
        }
    }

    private class AsyncGetConfig extends AsyncTask<Void, Void, String> {
        ProgressDialog pdLoading = new ProgressDialog(bottlechoose2.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tDownloading your configuration...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = null;
            try {
                URL url = new URL("http://weighty-beach-183107.appspot.com/getallconfig.php");
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
                JSONArray jsonArray = jsonObject.getJSONArray("pillY");
                for (int i = 0; i < jsonArray.length(); i++) {
                    String timeName = jsonArray.getJSONObject(i).getString("timeName");
                    String startTime = jsonArray.getJSONObject(i).getString("startTime");
                    String endTime = jsonArray.getJSONObject(i).getString("endTime");
                    String pillAmt = jsonArray.getJSONObject(i).getString("pillAmt");
                    int k = Integer.parseInt(pillAmt);
                    try {
                        bottleyGetSet p = new bottleyGetSet(timeName, startTime, endTime, k);
                        dbHandler.bottleyAdd(p);

                    } catch (Exception e) {
                        Toast.makeText(bottlechoose2.this, "didnt add to DB", Toast.LENGTH_LONG).show();
                    }
                }



            } catch (JSONException e) {
                Toast.makeText(bottlechoose2.this, "No configuration found", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

            displayYlist();
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