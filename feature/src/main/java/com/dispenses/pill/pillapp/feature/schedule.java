package com.dispenses.pill.pillapp.feature;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


/**
 * Created by Win10 on 28/01/2018.
 */

public class schedule extends AppCompatActivity implements View.OnClickListener {

    private CardView bottle1,bottle2,bottle3;
    String keyx="nameBX";
    String keyY="nameBY";
    String keyZ="nameBZ";
    String getX;
    String getY;
    String getZ;

    private static final String PREFS_NAME = "set";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);

        bottle1 = (CardView) findViewById(R.id.cardView);
        bottle2 = (CardView) findViewById(R.id.cardView2);
        bottle3 = (CardView) findViewById(R.id.cardView4);

        bottle1.setOnClickListener(this);
        bottle2.setOnClickListener(this);
        bottle3.setOnClickListener(this);

        runOnUiThread(new Runnable() {
            public void run() {
                getX=getDefaults(keyx,schedule.this);
                final TextView mTextView = (TextView) findViewById(R.id.textView2);
                mTextView.setText(getX);

                getY=getDefaults(keyY,schedule.this);
                final TextView mTextView1 = (TextView) findViewById(R.id.textView3);
                mTextView1.setText(getY);

                getZ=getDefaults(keyZ,schedule.this);
                final TextView mTextView2 = (TextView) findViewById(R.id.textView1);
                mTextView2.setText(getZ);
            }
        });



        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if( item.getItemId() == R.id.ic_home )
                {
                    Intent intent1 = new Intent(schedule.this, home.class);
                    startActivity(intent1);
                }

                else if( item.getItemId() == R.id.ic_schedule )
                {
                    Intent intent1 = new Intent(schedule.this, schedule.class);
                    startActivity(intent1);
                }


                else if( item.getItemId() == R.id.ic_dispense )
                {
                    Intent intent1 = new Intent(schedule.this, DispenseManual.class);
                    startActivity(intent1);

                }

                else if( item.getItemId() == R.id.ic_history )
                {
                    Intent intent1 = new Intent(schedule.this, History.class);
                    startActivity(intent1);
                }

                else if( item.getItemId() == R.id.ic_setting )
                {
                    Intent intent1 = new Intent(schedule.this, Setting.class);
                    startActivity(intent1);
                }


                return false;
            }
        });






    }


    @Override
    public void onResume() {
        super.onResume();
        setContentView(R.layout.schedule);


        bottle1 = (CardView) findViewById(R.id.cardView);
        bottle2 = (CardView) findViewById(R.id.cardView2);
        bottle3 = (CardView) findViewById(R.id.cardView4);

        bottle1.setOnClickListener(this);
        bottle2.setOnClickListener(this);
        bottle3.setOnClickListener(this);


        runOnUiThread(new Runnable() {
            public void run() {
                getX=getDefaults(keyx,schedule.this);
                final TextView mTextView = (TextView) findViewById(R.id.textView2);
                mTextView.setText(getX);

                getY=getDefaults(keyY,schedule.this);
                final TextView mTextView1 = (TextView) findViewById(R.id.textView3);
                mTextView1.setText(getY);

                getZ=getDefaults(keyZ,schedule.this);
                final TextView mTextView2 = (TextView) findViewById(R.id.textView1);
                mTextView2.setText(getZ);
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if( item.getItemId() == R.id.ic_home )
                {
                    Intent intent1 = new Intent(schedule.this, home.class);
                    startActivity(intent1);
                }

                else if( item.getItemId() == R.id.ic_schedule )
                {
                    Intent intent1 = new Intent(schedule.this, schedule.class);
                    startActivity(intent1);
                }


                else if( item.getItemId() == R.id.ic_dispense )
                {
                    Intent intent1 = new Intent(schedule.this, DispenseManual.class);
                    startActivity(intent1);

                }

                else if( item.getItemId() == R.id.ic_history )
                {
                    Intent intent1 = new Intent(schedule.this, History.class);
                    startActivity(intent1);
                }

                else if( item.getItemId() == R.id.ic_setting )
                {
                    Intent intent1 = new Intent(schedule.this, Setting.class);
                    startActivity(intent1);
                }


                return false;
            }
        });






    }

    public void goalarm(View arg0) {

        Intent intent1 = new Intent(schedule.this, AlarmAdd.class);
        startActivity(intent1);

    }






    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.cardView)
            {
            Intent intent1 = new Intent(schedule.this, bottlechoose.class);
            startActivity(intent1);
            }

        else if (view.getId() == R.id.cardView2)

                {
                Intent intent1 = new Intent(schedule.this, bottlechoose2.class);
                startActivity(intent1);
                }

        else if (view.getId() == R.id.cardView4)

                {
                Intent intent1 = new Intent(schedule.this, bottlechoose3.class);
                startActivity(intent1);
                }


    }

    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, "Edit Name Inside");
    }
}
