package com.dispenses.pill.pillapp.feature;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

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
        displayZlist();
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
            Toast.makeText(this, "bitch i work", Toast.LENGTH_LONG).show();
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


}
