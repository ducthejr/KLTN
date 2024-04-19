package com.example.khoaluantn;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ledOn extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Thông báo", "Đã bật đèn");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference DEN = database.getReference("den");
        DEN.setValue(0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"LED ON")
                .setSmallIcon(R.drawable.onlamp)
                .setContentTitle("Thông báo bật đèn")
                .setContentText("Đèn đã được bật ")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationCompat = NotificationManagerCompat.from(context);
        notificationCompat.notify(200,builder.build());
    }
}

