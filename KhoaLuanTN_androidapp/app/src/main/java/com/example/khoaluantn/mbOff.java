package com.example.khoaluantn;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class mbOff extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Thông báo", "Đã tắt Máy Bơm");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference MB = database.getReference("maybom");
        MB.setValue(1);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"MB OFF")
                .setSmallIcon(R.drawable.bom)
                .setContentTitle("Thông báo tắt Máy Bơm")
                .setContentText("Máy Bơm đã được tắt ")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationCompat = NotificationManagerCompat.from(context);
        notificationCompat.notify(200,builder.build());
    }
}

