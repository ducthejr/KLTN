package com.example.khoaluantn;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class mbOn extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Thông báo", "Đã bật Máy Bơm");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference MB = database.getReference("maybom");
        MB.setValue(0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"MB ON")
                .setSmallIcon(R.drawable.bom)
                .setContentTitle("Thông báo bật Máy Bơm")
                .setContentText("Máy Bơm đã được bật ")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationCompat = NotificationManagerCompat.from(context);
        notificationCompat.notify(200,builder.build());
    }
}

