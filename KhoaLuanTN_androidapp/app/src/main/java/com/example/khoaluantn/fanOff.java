package com.example.khoaluantn;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class fanOff extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Thông báo", "Đã tắt Quạt");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference QUAT = database.getReference("quat");
        QUAT.setValue(1);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"FAN OFF")
                .setSmallIcon(R.drawable.fan)
                .setContentTitle("Thông báo tắt Quạt")
                .setContentText("Quạt đã được tắt ")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationCompat = NotificationManagerCompat.from(context);
        notificationCompat.notify(200,builder.build());
    }
}

