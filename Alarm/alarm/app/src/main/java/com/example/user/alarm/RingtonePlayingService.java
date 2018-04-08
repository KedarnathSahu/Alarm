package com.example.user.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import java.util.Random;

public class RingtonePlayingService extends Service {
    private boolean isRunning;
    MediaPlayer mMediaPlayer;
    private int startId;//no state=0 & yes state=1

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //notification
        final NotificationManager mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this.getApplicationContext(), AlarmActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, 0);
        Notification mNotify  = new Notification.Builder(this)
                .setContentTitle("!!!  Alarm  Alert  !!!")
                .setContentText("Click Me :)")
                .setSmallIcon(R.drawable.ic_time)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();

        //alert music
        String state = intent.getExtras().getString("extra");

        assert state != null;
        switch (state) {
            case "no":
                startId = 0;
                break;
            case "yes":
                startId = 1;
                break;
            default:
                startId = 0;
                break;
        }

        if(!this.isRunning && startId == 1) {//if there is no sound and you want start
            int min = 1;
            int max = 4;
            Random r = new Random();
            int random_number = r.nextInt(max - min + 1) + min;//String.valueOf(random_number)

            if (random_number == 1) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.rooster);
            }
            else if (random_number == 2) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.arabic_ton);
            }
            else if (random_number == 3) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.sony_ericsson);
            }
            else if (random_number == 4) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.police);
            } else {
                mMediaPlayer = MediaPlayer.create(this, R.raw.rooster);
            }

            mMediaPlayer.start();
            mNM.notify(0, mNotify);

            this.isRunning = true;
            this.startId = 0;//state=no

        } else if (!this.isRunning && startId == 0){//if there is no sound and you want end
            this.isRunning = false;
            this.startId = 0;
        } else if (this.isRunning && startId == 1){//if there is sound and you want start
            this.isRunning = true;
            this.startId = 0;
        } else {//if there is sound and you want end
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            this.isRunning = false;
            this.startId = 0;
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.isRunning = false;
    }
}
