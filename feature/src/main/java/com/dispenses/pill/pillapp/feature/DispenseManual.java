package com.dispenses.pill.pillapp.feature;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by Win10 on 10/02/2018.
 */

public class DispenseManual extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dispense);

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



}
