package com.dispenses.pill.pillapp.feature;

/**
 * Created by Win10 on 28/01/2018.
 */


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.widget.Toast;
import android.os.Vibrator;
import android.content.Context;
import android.app.Notification;
import android.app.NotificationManager;

import java.util.Calendar;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.content.Context.NOTIFICATION_SERVICE;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

public class AlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1)
        {
            long id = arg1.getLongExtra("id", -1);
            Toast.makeText(arg0, "Alarm received!", Toast.LENGTH_LONG).show();
            // Vibrate for 500 milliseconds
            Vibrator v = (Vibrator) arg0.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(500);


            Intent notificationIntent = new Intent(arg0,AlarmCancel.class);
            notificationIntent.putExtra("getid", id);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(arg0);
            stackBuilder.addParentStack(AlarmCancel.class);
            stackBuilder.addNextIntent(notificationIntent);
            PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, Intent.FLAG_ACTIVITY_NEW_TASK);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(arg0);
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Notification notification = builder.setContentTitle("Time To Eat Your Pill !!!")
                    .setContentText("Open Application To Dispense The Pill")
                    .setTicker("New Message Alert!")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setSound(alarmSound)
                    .setContentIntent(pendingIntent).build();

            NotificationManager notificationManager = (NotificationManager) arg0.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notification);

            Intent i = new Intent(arg0, RingtonePlayingService.class);
            arg0.startService(i);

        }

}
