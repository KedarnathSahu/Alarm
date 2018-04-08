package com.example.user.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    TextView textView;
    ConstraintLayout constraintLayout;

    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Context context;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        this.context=this;
        intent=new Intent(this.context,AlertReceiver.class);

        textView=(TextView)findViewById(R.id.textView4);
        constraintLayout=(ConstraintLayout)findViewById(R.id.myRoot);

        alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
    }

    public void set(View view) {
        DialogFragment timePicker=new TimepickerFragment();
        timePicker.show(getSupportFragmentManager(),"Time picker");
    }

    public void cancel(View view) {
        cancelAlarm();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c= Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
        c.set(Calendar.MINUTE,minute);
        c.set(Calendar.SECOND,0);
        updateTimeText(c);//method 4 updating UI
        startAlarm(c);//method 2 start pending intent
    }

    private void startAlarm(Calendar c) {
        intent.putExtra("extra","yes");
        pendingIntent=PendingIntent.getBroadcast(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//        if(c.before(Calendar.getInstance())){
//            c.add(Calendar.DATE,1);//if user chose past time then alarm set for next day
//        }
        alarmManager.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
        Snackbar.make(constraintLayout,"Alarm Starts in "+DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime()),Snackbar.LENGTH_LONG).show();
    }

    private void updateTimeText(Calendar c) {
        String timeText="Alarm @: ";
        timeText+= DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        textView.setText(timeText);
    }

    private void cancelAlarm() {
        intent.putExtra("extra", "no");
        sendBroadcast(intent);
        alarmManager.cancel(pendingIntent);
        textView.setText("Alarm Canceled");
        Snackbar.make(constraintLayout,"Alarm Canceled",Snackbar.LENGTH_LONG).show();
    }
}
