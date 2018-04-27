package com.dispenses.pill.pillapp.feature;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by Win10 on 10/02/2018.
 */

public class Setting extends AppCompatActivity {

    String remember="pass";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if( item.getItemId() == R.id.ic_home )
                {
                    Intent intent1 = new Intent(Setting.this, home.class);
                    startActivity(intent1);
                    finish();
                }

                else if( item.getItemId() == R.id.ic_schedule )
                {
                    Intent intent1 = new Intent(Setting.this, schedule.class);
                    startActivity(intent1);
                    finish();
                }


                else if( item.getItemId() == R.id.ic_dispense )
                {
                    Intent intent1 = new Intent(Setting.this, DispenseManual.class);
                    startActivity(intent1);
                    finish();
                }

                else if( item.getItemId() == R.id.ic_history )
                {
                    Intent intent1 = new Intent(Setting.this, History.class);
                    startActivity(intent1);
                    finish();
                }

                else if( item.getItemId() == R.id.ic_setting )
                {
                    Intent intent1 = new Intent(Setting.this, Setting.class);
                    startActivity(intent1);
                    finish();
                }

                return false;
            }
        });

    }

    public void clear(View arg0) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Setting.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(remember);
        editor.apply();
        finish();

    }





}

