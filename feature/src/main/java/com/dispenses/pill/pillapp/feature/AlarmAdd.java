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

    MyDBHandler         dbHandler;
    SimpleCursorAdapter simpleCursorAdapter;
    TextView            idView;
    EditText            etName;
    EditText            etQuantity;
    ListView            lvProducts;

    FloatingActionButton fab1, fab2;
    boolean isFABOpen=false;


    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    TimePicker myTimePicker;
    Button buttonstartSetDialog;
    TextView textAlarmPrompt;
    public static final int DAILY_REMINDER_REQUEST_CODE=100;
    public static final String TAG="NotificationScheduler";

    TimePickerDialog timePickerDialog;
    EditText txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    int RQS_1 = 1;
    int cancelAlarm = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

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

    @Override
    public void onResume(){
        super.onResume();
        setContentView(R.layout.activity_alarm);

        dbHandler = new MyDBHandler(this, null, null, 1);
        lvProducts = (ListView) findViewById(R.id.hello);
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

    public void gettime(View v) {

        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        etQuantity.setText(hourOfDay + ":" + minute);
                        Calendar calNow = Calendar.getInstance();
                        Calendar calSet = (Calendar) calNow.clone();

                        calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calSet.set(Calendar.MINUTE, minute);
                        calSet.set(Calendar.SECOND, 0);
                        calSet.set(Calendar.MILLISECOND, 0);

                        if(calSet.compareTo(calNow) <= 0){
                            //Today Set time passed, count to tomorrow
                            calSet.add(Calendar.DATE, 1);
                        }
                        setAlarm(calSet);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    public void SaveInt(String key, int value)
    {
        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }



    public void LoadInt( )
    {
    SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
    RQS_1 = sp.getInt("key", 1);
    }



    private void setAlarm(Calendar targetCal){

        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        LoadInt();
        RQS_1 = RQS_1 +1 ;
        SaveInt("key",RQS_1);
        intent.putExtra("id", RQS_1);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);

    }

    public void newProduct(View v) {
        try {
            Alarm p = new Alarm(etName.getText().toString(), etQuantity.getText().toString());
            dbHandler.addAlarm(p);
            etName.setText("");
            etQuantity.setText("");
            displayProductList();
        } catch (Exception e) {

        }
    }

    public void lookupProduct(View v) {
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
    }

    public void updateProduct(View v) {
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
    }

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
            Toast.makeText(this, "bitch i work", Toast.LENGTH_LONG).show();
            String[] columns = new String[] {
                    MyDBHandler.COLUMN_ALARMNAME,
                    MyDBHandler.COLUMN_TIME
            };
            int[] boundTo = new int[] {
                    R.id.pName,
                    R.id.pQuantity
            };
            simpleCursorAdapter = new SimpleCursorAdapter(this,
                    R.layout.alarm_list,
                    cursor,
                    columns,
                    boundTo,
                    0);
            lvProducts.setAdapter(simpleCursorAdapter);
        }
        catch (Exception ex)
        {
            idView.setText("There was an error!");
        }
    }


}
