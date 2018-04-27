package com.dispenses.pill.pillapp.feature;

/**
 * Created by Win10 on 16/03/2018.
 */
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
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

import java.util.Calendar;

public class addalarm_popup extends AppCompatDialogFragment {

    private EditText addname;
    private EditText addtime;
    private EditText clickthis;
    MyDBHandler  dbHandler;
    SimpleCursorAdapter simpleCursorAdapter;
    TextView idView;
    EditText            etName;
    EditText            etQuantity;
    ListView lvProducts;
    TimePickerDialog timePickerDialog;
    EditText txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    int RQS_1 = 1;
    int cancelAlarm = 0 ;
    private Context globalContext = null;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.addalarm, null);
        final Context context = inflater.getContext();
        dbHandler = new MyDBHandler(context, null, null, 1);

        builder.setView(view).setTitle("Add Alarm").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        final String name = addname.getText().toString();
                        final String time = addtime.getText().toString();
                        try {
                            Alarm p = new Alarm(name, time);
                            dbHandler.addAlarm(p);
                            Toast.makeText(getActivity(), "Addded", Toast.LENGTH_LONG).show();
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(getActivity(), "didnt add to DB", Toast.LENGTH_LONG).show();
                        }

                        Intent intent1 = new Intent(context, AlarmAdd.class);
                        startActivity(intent1);
                    }

                });


        addtime = (EditText) view.findViewById(R.id.editText9);
        addtime.setClickable(true);
        addtime.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                addtime.setText(hourOfDay + ":" + minute);
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
        });
        addname = view.findViewById(R.id.editText4);



        return builder.create();
    }


    private void setAlarm(Calendar targetCal){

        globalContext = this.getActivity();
        Intent intent = new Intent(globalContext, AlarmReceiver.class);
        LoadInt();
        RQS_1 = RQS_1 +1 ;
        SaveInt("key",RQS_1);
        intent.putExtra("id", RQS_1);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(globalContext, RQS_1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)globalContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);

    }

    public void SaveInt(String key, int value)
    {
        globalContext = this.getActivity();
        SharedPreferences sp = globalContext.getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }



    public void LoadInt( )
    {
        SharedPreferences sp = globalContext.getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        RQS_1 = sp.getInt("key", 1);
    }





}
