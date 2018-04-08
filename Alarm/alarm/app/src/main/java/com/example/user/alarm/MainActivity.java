package com.example.user.alarm;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener{
    MediaPlayer mySound;
    int paused;
    TextView textView,textView1,textView2,textView3;
    private NotificationHelper mNotificationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView=findViewById(R.id.textView3);//time
        textView2=findViewById(R.id.textView);//time title

        textView1=findViewById(R.id.textView6);//date
        textView3=findViewById(R.id.textView5);//date title

        mNotificationHelper=new NotificationHelper(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent=new Intent(MainActivity.this,AlarmActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /*----------------------set date ----------------------*/
    public void date(View view){
        DialogFragment datePicker=new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(),"date picker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c=Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String currentDate= DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        textView1.setText(currentDate);
    }

    /*----------------------set time ----------------------*/
    public void time(View view){
        DialogFragment timePicker=new TimepickerFragment();
        timePicker.show(getSupportFragmentManager(),"time picker");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        textView.setText("Hr: "+hourOfDay+" Min: "+minute);
    }

    /*----------------------music player----------------------*/
    public void play(View view){
        if(mySound==null){
            mySound= MediaPlayer.create(this,R.raw.arabic_ton);
            mySound.setLooping(true);
            mySound.start();
        }else if(!mySound.isPlaying()) {
            mySound.seekTo(paused);
            mySound.start();
        }
    }
    public void pause(View view){
        mySound.pause();
        paused=mySound.getCurrentPosition();
    }
    public void stop(View view){
        mySound.release();
        mySound=null;
    }

    /*----------------------Notification----------------------*/

    public void notify(View view) {
        sendOnChannel1(textView2.getText().toString(),textView.getText().toString());//notification for time
        sendOnChannel2(textView3.getText().toString(),textView1.getText().toString());//notification for date
    }

    private void sendOnChannel1(String title, String message) {
        NotificationCompat.Builder nb = mNotificationHelper.getChannel1Notification(title,message);
        mNotificationHelper.getManager().notify(1,nb.build());
    }

    private void sendOnChannel2(String title, String message) {
        NotificationCompat.Builder nb = mNotificationHelper.getChannel2Notification(title,message);
        mNotificationHelper.getManager().notify(2,nb.build());
    }
}
