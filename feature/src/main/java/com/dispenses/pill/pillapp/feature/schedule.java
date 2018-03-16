package com.dispenses.pill.pillapp.feature;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

/**
 * Created by Win10 on 28/01/2018.
 */

public class schedule extends AppCompatActivity implements View.OnClickListener {

    private CardView bottle1,bottle2,bottle3;
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
}
