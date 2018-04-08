package com.example.user.alarm;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {
    public static final String channel1ID="channel1ID";
    public static final String channel1Name="Channel time";

    public static final String channel2ID="channel2ID";
    public static final String channel2Name="Channel date";

    private NotificationManager mMannager;

    public NotificationHelper(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
            createChannels();
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannels() {
        //channel1
        NotificationChannel channel1=new NotificationChannel(channel1ID,channel1Name, NotificationManager.IMPORTANCE_DEFAULT);
        channel1.enableLights(true);
        channel1.enableVibration(true);
        channel1.setLightColor(R.color.colorPrimary);
        channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(channel1);

        //channel2
        NotificationChannel channel2=new NotificationChannel(channel2ID,channel2Name, NotificationManager.IMPORTANCE_DEFAULT);
        channel2.enableLights(true);
        channel2.enableVibration(true);
        channel2.setLightColor(R.color.colorPrimary);
        channel2.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(channel2);
    }

    public NotificationManager getManager(){
        if(mMannager==null){
            mMannager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mMannager;
    }

    /*----------------------For Time----------------------*/
    public NotificationCompat.Builder getChannel1Notification(String title,String message){
        Intent intent=new Intent(this,MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder1 = new NotificationCompat.Builder(this,channel1ID)
                .setSmallIcon(R.drawable.ic_time)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        return mBuilder1;
    }

    /*----------------------For Date----------------------*/
    public NotificationCompat.Builder getChannel2Notification(String title,String message){
        Intent intent=new Intent(this,MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,2,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder2 = new NotificationCompat.Builder(this,channel2ID)
                .setSmallIcon(R.drawable.ic_date)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        return mBuilder2;
    }

    /*----------------------For Alarm----------------------*/
    public NotificationCompat.Builder getChannelNotification() {
        Intent intent=new Intent(this,AlarmActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder3 = new NotificationCompat.Builder(this,channel1ID)
                .setSmallIcon(R.drawable.ic_time)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        return mBuilder3;
    }
}