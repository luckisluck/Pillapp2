package com.dispenses.pill.pillapp.feature;


import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


import android.widget.Button;
import android.widget.TimePicker;
import android.app.TimePickerDialog;
import java.util.Calendar;
import android.view.WindowManager;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.app.Notification;
import android.app.NotificationManager;
import android.provider.Settings;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by Win10 on 07/02/2018.
 */

public class AlarmAdd extends AppCompatActivity {



    FloatingActionButton fab1, fab2;
    boolean isFABOpen=false;


    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    TimePicker myTimePicker;
    Button buttonstartSetDialog;
    TextView textAlarmPrompt;
    public static final int DAILY_REMINDER_REQUEST_CODE=100;
    public static final String TAG="NotificationScheduler";
    MyDBHandler  dbHandler;
    SimpleCursorAdapter simpleCursorAdapter;
    ListView lvProducts1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        lvProducts1 = (ListView) findViewById(R.id.productList1);
        dbHandler = new MyDBHandler(this, null, null, 1);
        displayProductList();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                del();
            }
        });


    }


    private void showFABMenu(){
        isFABOpen=true;
        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_105));

    }

    private void closeFABMenu(){
        isFABOpen=false;
        fab1.animate().translationY(0);
        fab2.animate().translationY(0);
    }

    public void add(){
        addalarm_popup popupTest = new addalarm_popup();
        popupTest.show(getSupportFragmentManager(),"example dialog popup");
    }

    public void del(){
        delalarm_popup popupTest = new delalarm_popup();
        popupTest.show(getSupportFragmentManager(),"example dialog popup");
    }


 /*   public void lookupProduct(View v) {
        Alarm p = dbHandler.findProduct(etName.getText().toString());
        if (p == null)
        {
            return;
        }
        etQuantity.setText(String.valueOf(p.getTime()));
    }

    public void deleteProduct(View v) {
        if (dbHandler.deleteProduct(etName.getText().toString())) {
            etName.setText("");
            etQuantity.setText("");
            displayProductList();
        } else {

        }
    }   */

   /* public void updateProduct(View v) {
        try {
            Alarm p = new Alarm(Integer.parseInt(idView.getText().toString()), etName.getText().toString(), etQuantity.getText().toString());
            if (dbHandler.updateProduct(p)) {

            } else {

            }
        } catch (Exception e) {
            idView.setText("Find a product \nfirst. Check \n all fields.");
        }
        etName.setText("");
        etQuantity.setText("");
        displayProductList();
    }

    public void deleteAllProducts(View v) {
        dbHandler.deleteAllProducts();
        etName.setText("");
        etQuantity.setText("");
        lvProducts.setAdapter(null);
    } */

    private void displayProductList() {
        try
        {
            Cursor cursor = dbHandler.getAllProducts();
            if (cursor == null)
            {
                return;
            }
            if (cursor.getCount() == 0)
            {
                return;
            }
           /* Toast.makeText(this, "bitch i work", Toast.LENGTH_LONG).show(); */
            String[] columns = new String[] {
                    MyDBHandler.COLUMN_ALARMNAME,
                    MyDBHandler.COLUMN_TIME
            };
            int[] boundTo = new int[] {
                    R.id.pName,
                    R.id.pQuantity
            };
            simpleCursorAdapter = new SimpleCursorAdapter(this,
                    R.layout.alarm_list2,
                    cursor,
                    columns,
                    boundTo,
                    0);
            lvProducts1.setAdapter(simpleCursorAdapter);
        }
        catch (Exception ex)
        {
            Toast.makeText(this, "There was an error!", Toast.LENGTH_LONG).show();
        }
    }


}
