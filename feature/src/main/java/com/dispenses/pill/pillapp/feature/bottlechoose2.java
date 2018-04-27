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

/**
 * Created by Win10 on 28/01/2018.
 */

public class bottlechoose2 extends AppCompatActivity {

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
        setContentView(R.layout.bottlechoose2);

        dbHandler = new MyDBHandler(this, null, null, 1);
        lvProducts = (ListView) findViewById(R.id.helloY);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);


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

    }

    private void closeFABMenu() {
        isFABOpen = false;
        fab1.animate().translationY(0);
        fab2.animate().translationY(0);
        fab3.animate().translationY(0);

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

}