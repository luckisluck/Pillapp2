package com.dispenses.pill.pillapp.feature;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Created by Win10 on 10/02/2018.
 */

public class AlarmCancel extends AppCompatActivity {

    long cancelAlarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_cancel);

        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            cancelAlarm =(long) b.get("getid");
        }
    }


    public void bottle100(View arg0){

        Intent i = new Intent(this, RingtonePlayingService.class);
        stopService(i);
        int ii = (int) cancelAlarm;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), ii, intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);

    }

}
