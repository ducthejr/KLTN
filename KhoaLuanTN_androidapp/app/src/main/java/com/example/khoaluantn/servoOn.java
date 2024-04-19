package com.example.khoaluantn;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class servoOn extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Thông báo", "Đã thêm thức ăn");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference servo = database.getReference("servo");
        servo.setValue(0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"SERVO ON")
                .setSmallIcon(R.drawable.bom)
                .setContentTitle("Thông báo đã thêm thức ăn")
                .setContentText("Thức ăn đã được bổ sung ")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationCompat = NotificationManagerCompat.from(context);
        notificationCompat.notify(200,builder.build());
    }
}

