package com.example.khoaluantn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class sensor extends AppCompatActivity {
    TextView nhietdo,doam,thoitiet,mucnuoc,khoiluong;
    ImageView Tt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        nhietdo = findViewById(R.id.nd);
        doam = findViewById(R.id.da);
        thoitiet = findViewById(R.id.sunandrain);
        mucnuoc = findViewById(R.id.waterlevel);
        khoiluong = findViewById(R.id.foodlevel);
        Tt = findViewById(R.id.imageView2);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ND = database.getReference("nhietdo");
        DatabaseReference DA = database.getReference("doam");
        DatabaseReference KC = database.getReference("water");
        DatabaseReference TT = database.getReference("TT");
        DatabaseReference Weight = database.getReference("weight");
        ND.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                float ndo = dataSnapshot.getValue(float.class);
                nhietdo.setText("Nhiệt độ : "+ndo +"°C");
                if(ndo > 39) thongbaond(ndo);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                nhietdo.setText("Chưa kết nối FireBase");

            }
        });
        DA.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                float dam = snapshot.getValue(float.class);
                doam.setText("Độ Ẩm : "+dam + "%");
                if(dam > 90) thongbaoda(dam);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                doam.setText("Chưa kết nối FireBase");
            }
        });
        KC.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                int water = dataSnapshot.getValue(int.class);
                mucnuoc.setText("Mực nước "+water+"%");
                if(water < 5) thongbaowl(water);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                mucnuoc.setText("Chưa kết nối FireBase");

            }
        });
        TT.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                int tt = dataSnapshot.getValue(int.class);
               if(tt==0) {thoitiet.setText("Trời Mưa");
               Tt.setImageResource(R.drawable.rain);
               thongbaorain();
               }
               if(tt==1) {thoitiet.setText("Trời Tạnh");
                   Tt.setImageResource(R.drawable.sun);
               }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                    thoitiet.setText("Chưa kết nối FireBase");
            }
        });
        Weight.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                float weight = snapshot.getValue(float.class);
                khoiluong.setText("Lượng thức ăn : "+weight+"KG");
                if(weight < 0.002) thongbaowe(weight);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                khoiluong.setText("Chưa kết nối FireBase");
            }
        });
    }
    private void thongbaoda(float doam){
        Bitmap bitmap  = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);

        Notification notification = new NotificationCompat.Builder(this, KLTN.CHANNEL_ID)
                .setContentTitle("Cảnh báo")
                .setContentText("Độ ẩm hiện tại là "+doam+"%\nThấp hơn mức cho phép")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setLargeIcon(bitmap)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager != null){
            notificationManager.notify(getNoti(),notification);
        }
    }
    private void thongbaorain(){
        Bitmap bitmap  = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);

        Notification notification = new NotificationCompat.Builder(this, KLTN.CHANNEL_ID)
                .setContentTitle("Cảnh báo")
                .setContentText("Trời mưa , hãy khởi động mái che")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setLargeIcon(bitmap)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager != null){
            notificationManager.notify(getNoti(),notification);
        }
    }
    private void thongbaond(float nd){
        Bitmap bitmap  = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);

        Notification notification = new NotificationCompat.Builder(this, KLTN.CHANNEL_ID)
                .setContentTitle("Cảnh báo")
                .setContentText("Nhiệt độ hiện tại là "+nd+"°C\nCao hơn mức cho phép")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setLargeIcon(bitmap)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager != null){
            notificationManager.notify(getNoti(),notification);
        }
    }
    private void thongbaowe(float we){
        Bitmap bitmap  = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);

        Notification notification = new NotificationCompat.Builder(this, KLTN.CHANNEL_ID)
                .setContentTitle("Cảnh báo")
                .setContentText("Lượng thức ăn hiện tại là "+we+"%\nHãy thêm thức ăn")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setLargeIcon(bitmap)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager != null){
            notificationManager.notify(getNoti(),notification);
        }
    }
    private void thongbaowl(int wl){
        Bitmap bitmap  = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);

        Notification notification = new NotificationCompat.Builder(this, KLTN.CHANNEL_ID)
                .setContentTitle("Cảnh báo")
                .setContentText("Mực nước hiện tại là "+wl+"%\nHãy cung cấp nước")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setLargeIcon(bitmap)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager != null){
            notificationManager.notify(getNoti(),notification);
        }
    }
    private int getNoti(){
        return (int) new Date().getTime();
    }
}