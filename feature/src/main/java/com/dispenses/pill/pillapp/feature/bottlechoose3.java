package com.dispenses.pill.pillapp.feature;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
 * Created by Win10 on 28/01/2018.
 */

public class bottlechoose3 extends AppCompatActivity {

    FloatingActionButton fab1, fab2,fab3;
    boolean isFABOpen = false;


    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    SimpleCursorAdapter simpleCursorAdapter;
    ListView lvProducts;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottlechoose3);

        dbHandler = new MyDBHandler(this, null, null, 1);
        lvProducts = (ListView) findViewById(R.id.helloZ);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);


        Cursor cursor = dbHandler.getallbottlez();
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
                delSchedulez();
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editz();
            }
        });
        displayZlist();


    }


    private void showFABMenu() {
        isFABOpen = true;
        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        fab3.animate().translationY(-getResources().getDimension(R.dimen.standard_155));

    }

    private void closeFABMenu() {
        isFABOpen = false;
        fab1.animate().translationY(0);
        fab2.animate().translationY(0);
        fab3.animate().translationY(0);
    }

    public void openDialog(){
        popupZ_schedule popupTest = new popupZ_schedule();
        popupTest.show(getSupportFragmentManager(),"example dialog popup");
    }

    public void delSchedulez(){
        popupZ_del popupTest = new popupZ_del();
        popupTest.show(getSupportFragmentManager(),"example dialog popup");
    }

    public void editz(){
        EditPillZ popupTest = new EditPillZ();
        popupTest.show(getSupportFragmentManager(),"example dialog popup");
    }

    private void displayZlist() {
        try
        {
            Cursor cursor = dbHandler.getallbottlez();
            if (cursor == null)
            {
                return;
            }
            if (cursor.getCount() == 0)
            {
                return;
            }
            String[] columns = new String[] {
                    MyDBHandler.COLUMN_NAMEZ,
                    MyDBHandler.COLUMN_STARTTIMEZ,
                    MyDBHandler.COLUMN_ENDTIMETIMEZ,
                    MyDBHandler.COLUMN_PILLAMTZ,
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
            Toast.makeText(bottlechoose3.this, "version 2", Toast.LENGTH_LONG).show();
        }
    }

    private class AsyncGetConfig extends AsyncTask<Void, Void, String> {
        ProgressDialog pdLoading = new ProgressDialog(bottlechoose3.this);
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
                JSONArray jsonArray = jsonObject.getJSONArray("pillZ");
                for (int i = 0; i < jsonArray.length(); i++) {
                    String timeName = jsonArray.getJSONObject(i).getString("timeName");
                    String startTime = jsonArray.getJSONObject(i).getString("startTime");
                    String endTime = jsonArray.getJSONObject(i).getString("endTime");
                    String pillAmt = jsonArray.getJSONObject(i).getString("pillAmt");
                    int k = Integer.parseInt(pillAmt);
                    try {
                        bottlezGetSet p = new bottlezGetSet(timeName, startTime, endTime, k);
                        dbHandler.bottlezAdd(p);

                    } catch (Exception e) {
                        Toast.makeText(bottlechoose3.this, "didnt add to DB", Toast.LENGTH_LONG).show();
                    }
                }



            } catch (JSONException e) {
                Toast.makeText(bottlechoose3.this, "No configuration found", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

            displayZlist();
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
