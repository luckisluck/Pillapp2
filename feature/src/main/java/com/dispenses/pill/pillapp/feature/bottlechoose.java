package com.dispenses.pill.pillapp.feature;

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
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
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

/**
 * Created by Win10 on 28/01/2018.
 */

public class bottlechoose extends AppCompatActivity {



    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    MyDBHandler  dbHandler;
    private EditText etname;
    private EditText etstartTime;
    private EditText etendTime;
    private EditText etpillAmt;
    SimpleCursorAdapter simpleCursorAdapter;
    ListView lvProducts;
    TextView idView;
    int count =0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottlechoose);
        dbHandler = new MyDBHandler(this, null, null, 1);
        lvProducts = (ListView) findViewById(R.id.hello);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
        displayxlist();

    }


    public void openDialog(){
        popup_autoX popupTest = new popup_autoX();
        popupTest.show(getSupportFragmentManager(),"example dialog popup");
        displayxlist();
    }



    private void displayxlist() {
        try
        {
            Cursor cursor = dbHandler.getallbottlex();
            if (cursor == null)
            {
                return;
            }
            if (cursor.getCount() == 0)
            {
                return;
            }
            Toast.makeText(this, "bitch i work", Toast.LENGTH_LONG).show();
            String[] columns = new String[] {
                    MyDBHandler.COLUMN_NAME,
                    MyDBHandler.COLUMN_STARTTIME,
                    MyDBHandler.COLUMN_ENDTIMETIME,
                    MyDBHandler.COLUMN_PILLAMT,
            };
            int[] boundTo = new int[] {
                    R.id.Namex,
                    R.id.startx,
                    R.id.endx,
                    R.id.pillamtx,
            };
            simpleCursorAdapter = new SimpleCursorAdapter(this,
                    R.layout.bottlex_list,
                    cursor,
                    columns,
                    boundTo,
                    0);
            lvProducts.setAdapter(simpleCursorAdapter);
        }

        catch (Exception ex)
        {
            Toast.makeText(bottlechoose.this, "version 2", Toast.LENGTH_LONG).show();
        }
    }




}
