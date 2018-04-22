package com.dispenses.pill.pillapp.feature;

/**
 * Created by Win10 on 28/01/2018.
 */


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
            int NOTIFICATION_ID = 234;
            String CHANNEL_ID = "my_channel_01";
            long id = arg1.getLongExtra("id", -1);
            Toast.makeText(arg0, "Alarm received!", Toast.LENGTH_LONG).show();
            // Vibrate for 500 milliseconds
            Vibrator v = (Vibrator) arg0.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(500);

            arg1 = new Intent();
            arg1.setClass(arg0, showalarm.class); //Test is a dummy class name where to redirect
            arg1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            arg0.startActivity(arg1);

            NotificationManager notificationManager = (NotificationManager) arg0.getSystemService(Context.NOTIFICATION_SERVICE);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                CharSequence name = "my_channel";
                String Description = "This is my channel";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                mChannel.setDescription(Description);
                mChannel.enableLights(true);
                mChannel.setLightColor(Color.RED);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                mChannel.setShowBadge(false);
                notificationManager.createNotificationChannel(mChannel);
            }


            NotificationCompat.Builder builder = new NotificationCompat.Builder(arg0, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Time To Eat Your Pill !!!")
                    .setContentText("Open Application To Dispense The Pill");

            Intent resultIntent = new Intent(arg0, showalarm.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(arg0);
            stackBuilder.addParentStack(showalarm.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setContentIntent(resultPendingIntent);

            notificationManager.notify(NOTIFICATION_ID, builder.build());


         /*   Intent notificationIntent = new Intent(arg0,AlarmCancel.class);
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
            notificationManager.notify(0, notification); */

            Intent i = new Intent(arg0, RingtonePlayingService.class);
            arg0.startService(i);


        }





}
