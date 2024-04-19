package com.example.khoaluantn;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class clock extends AppCompatActivity {
    Button btledon,btledoff,ledstop,btfanon,btfanoff,btmbon,btmboff,btsvon,fanstop,mbstop,svstop;
    TextView Tledon,Tledoff,Tfanon,Tfanoff,Tmbon,Tmboff,Tsvon;
    Calendar calendar;
    TimePicker timePicker;
    AlarmManager alarmLedon,alarmLedoff,alarmFanon,alarmFanoff,alarmMBon,alarmMBoff,alarmServo;
    public static final String CHANNEL_ID = "Thong Bao";
    PendingIntent pendingIntentLedon,pendingIntentLedoff,pendingIntentFanon,pendingIntentFanoff,pendingIntentMBon,pendingIntentMBoff,
    pendingIntentServo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        anhxa();
        calendar  = Calendar.getInstance();
        alarmLedon = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmLedoff = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmFanon = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmFanoff = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmMBon = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmMBoff = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmServo = (AlarmManager)getSystemService(ALARM_SERVICE);;
        Intent intentledon = new Intent(clock.this,ledOn.class);
        Intent intentledoff = new Intent(clock.this,ledOff.class);
        Intent intentfanon = new Intent(clock.this,fanOn.class);
        Intent intentfanoff= new Intent(clock.this,fanOff.class);
        Intent intentmbon = new Intent(clock.this,mbOn.class);
        Intent intentmboff = new Intent(clock.this,mbOff.class);
        Intent intentservoon= new Intent(clock.this,servoOn.class);

        createNotificationLedoff();
        createNotificationLedon();
        createNotificationFanon();
        createNotificationFanoff();
        createNotificationServoon();
        createNotificationMBon();
        createNotificationMBoff();
        btsvon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.set(Calendar.HOUR_OF_DAY,timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE,timePicker.getCurrentMinute());
                int gio = timePicker.getCurrentHour();
                int phut = timePicker.getCurrentMinute();
                Tsvon.setText("Time SERVO ON: " + gio +":" + phut);
                pendingIntentServo = PendingIntent.getBroadcast(clock.this,0,intentservoon,
                        pendingIntentServo.FLAG_UPDATE_CURRENT);
                alarmServo.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntentServo);
            }
        });
        btfanon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.set(Calendar.HOUR_OF_DAY,timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE,timePicker.getCurrentMinute());
                int gio = timePicker.getCurrentHour();
                int phut = timePicker.getCurrentMinute();
                Tfanon.setText("Time FAN ON: " + gio +":" + phut);
                pendingIntentFanon = PendingIntent.getBroadcast(clock.this,0,intentfanon,
                        pendingIntentFanon.FLAG_UPDATE_CURRENT);
                alarmFanon.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntentFanon);
            }
        });
        btfanoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.set(Calendar.HOUR_OF_DAY,timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE,timePicker.getCurrentMinute());
                int gio = timePicker.getCurrentHour();
                int phut = timePicker.getCurrentMinute();
                Tfanoff.setText("Time FAN OFF: " + gio +":" + phut);
                pendingIntentFanoff= PendingIntent.getBroadcast(clock.this,0,intentfanoff,
                        pendingIntentFanoff.FLAG_UPDATE_CURRENT);
                alarmFanoff.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntentFanoff);
            }
        });
        btmbon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.set(Calendar.HOUR_OF_DAY,timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE,timePicker.getCurrentMinute());
                int gio = timePicker.getCurrentHour();
                int phut = timePicker.getCurrentMinute();
                Tmbon.setText("Time PUMP ON: " + gio +":" + phut);
                pendingIntentMBon = PendingIntent.getBroadcast(clock.this,0,intentmbon,
                        pendingIntentMBon.FLAG_UPDATE_CURRENT);
                alarmMBon.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntentMBon);
            }
        });
        btmboff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.set(Calendar.HOUR_OF_DAY,timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE,timePicker.getCurrentMinute());
                int gio = timePicker.getCurrentHour();
                int phut = timePicker.getCurrentMinute();
                Tmboff.setText("Time PUMP OFF: " + gio +":" + phut);
                pendingIntentMBoff = PendingIntent.getBroadcast(clock.this,0,intentmboff,
                        pendingIntentMBoff.FLAG_UPDATE_CURRENT);
                alarmMBoff.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntentMBoff);
            }
        });
        btledon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.set(Calendar.HOUR_OF_DAY,timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE,timePicker.getCurrentMinute());
                int gio = timePicker.getCurrentHour();
                int phut = timePicker.getCurrentMinute();
                Tledon.setText("Time LED ON: " + gio +":" + phut);
                pendingIntentLedon = PendingIntent.getBroadcast(clock.this,0,intentledon,
                        pendingIntentLedon.FLAG_UPDATE_CURRENT);
                alarmLedon.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntentLedon);
            }
        });
        btledoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.set(Calendar.HOUR_OF_DAY,timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE,timePicker.getCurrentMinute());
                int gio = timePicker.getCurrentHour();
                int phut = timePicker.getCurrentMinute();
                Tledoff.setText("Time LED OFF: " + gio +":" + phut);
                pendingIntentLedoff = PendingIntent.getBroadcast(clock.this,0,intentledoff,
                        pendingIntentLedoff.FLAG_UPDATE_CURRENT);
                alarmLedoff.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntentLedoff);
            }
        });
        ledstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopAlarmLed();
                Tledon.setText("");
                Tledoff.setText("");
            }
        });
        fanstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopAlarmFan();
                Tfanoff.setText("");
                Tfanon.setText("");
            }
        });
        mbstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopAlarmPump();
                Tmbon.setText("");
                Tmboff.setText("");
            }
        });
        svstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopAlarmServo();
                Tsvon.setText("");
            }
        });
    }
    public void anhxa(){
        btledon = findViewById(R.id.btledon);
        btledoff = findViewById(R.id.btledoff);
        ledstop = findViewById(R.id.ledstop);
        btfanon = findViewById(R.id.btfanon);
        btfanoff = findViewById(R.id.btfanoff);
        fanstop = findViewById(R.id.fanstop);
        btmbon = findViewById(R.id.btmbon);
        btmboff = findViewById(R.id.btmboff);
        mbstop = findViewById(R.id.mbstop);
        btsvon = findViewById(R.id.btsvon);
        svstop  = findViewById(R.id.servostop);
        Tledon = findViewById(R.id.ledon);
        Tledoff = findViewById(R.id.ledoff);
        Tfanon = findViewById(R.id.fanon);
        Tfanoff = findViewById(R.id.fanoff);
        Tmbon = findViewById(R.id.mbnon);
        Tmboff = findViewById(R.id.mboff);
        Tsvon = findViewById(R.id.svnon);
        timePicker = findViewById(R.id.timePicker);
    }
    private void stopAlarmLed(){
        Intent intent  = new Intent(this,ledOn.class);
        Intent intent1  = new Intent(this,ledOff.class);
        pendingIntentLedon= PendingIntent.getBroadcast(this,0,intent,0);
        if(alarmLedon == null){
            alarmLedon = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }
        alarmLedon.cancel(pendingIntentLedon);
        pendingIntentLedoff= PendingIntent.getBroadcast(this,0,intent1,0);
        if(alarmLedoff == null){
            alarmLedoff = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }
        alarmLedoff.cancel(pendingIntentLedoff);
        Toast.makeText(this, "Hủy bỏ thời gian bật/tắt LED", Toast.LENGTH_SHORT).show();
    }
    private void stopAlarmFan(){
        Intent intent  = new Intent(this,fanOn.class);
        Intent intent1  = new Intent(this,fanOff.class);
        pendingIntentFanon= PendingIntent.getBroadcast(this,0,intent,0);
        if(alarmFanon == null){
            alarmFanon= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }
        alarmFanon.cancel(pendingIntentFanon);
        pendingIntentFanoff= PendingIntent.getBroadcast(this,0,intent1,0);
        if(alarmFanoff == null){
            alarmFanoff = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }
        alarmFanoff.cancel(pendingIntentFanoff);
        Toast.makeText(this, "Hủy bỏ thời gian bật/tắt FAN", Toast.LENGTH_SHORT).show();
    }
    private void stopAlarmPump(){
        Intent intent  = new Intent(this,mbOn.class);
        Intent intent1  = new Intent(this,mbOff.class);
        pendingIntentMBon= PendingIntent.getBroadcast(this,0,intent,0);
        if(alarmMBon == null){
            alarmMBon = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }
        alarmMBon.cancel(pendingIntentMBon);
        pendingIntentMBoff= PendingIntent.getBroadcast(this,0,intent1,0);
        if(alarmMBoff == null){
            alarmMBoff = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }
        alarmMBoff.cancel(pendingIntentMBoff);
        Toast.makeText(this, "Hủy bỏ thời gian bật/tắt MÁY BƠM", Toast.LENGTH_SHORT).show();
    }
    private void stopAlarmServo(){
        Intent intent  = new Intent(this,servoOn.class);
        pendingIntentServo= PendingIntent.getBroadcast(this,0,intent,0);
        if(alarmServo == null){
            alarmServo = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }
        alarmServo.cancel(pendingIntentServo);
        Toast.makeText(this, "Hủy bỏ thời gian bật Servo", Toast.LENGTH_SHORT).show();
    }
    private void createNotificationLedon() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Thông báo";
            String description = "Thông báo";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("LED ON", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            notificationManager.createNotificationChannel(channel);
        }
    }
    private void createNotificationFanon() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Thông báo";
            String description = "Thông báo";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("FAN ON", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            notificationManager.createNotificationChannel(channel);
        }
    }
    private void createNotificationFanoff() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Thông báo";
            String description = "Thông báo";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("FAN OFF", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            notificationManager.createNotificationChannel(channel);
        }
    }
    private void createNotificationMBon() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Thông báo";
            String description = "Thông báo";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("MB ON", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            notificationManager.createNotificationChannel(channel);
        }
    }
    private void createNotificationMBoff() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Thông báo";
            String description = "Thông báo";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("MB OFF", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            notificationManager.createNotificationChannel(channel);
        }
    }
    private void createNotificationServoon() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Thông báo";
            String description = "Thông báo";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("SERVO ON", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            notificationManager.createNotificationChannel(channel);
        }
    }
    private void createNotificationLedoff() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Thông báo";
            String description = "Thông báo";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("LED OFF", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            notificationManager.createNotificationChannel(channel);
        }
    }
}