package com.example.khoaluantn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class control extends AppCompatActivity {
    ToggleButton den,quat,maybom,motor,van;
    Switch sw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference DEN = database.getReference("den");
        DatabaseReference QUAT  = database.getReference("quat");
        DatabaseReference MAYBOM = database.getReference("maybom");
        DatabaseReference VAN = database.getReference("servo");
        DatabaseReference controlState = database.getReference("relayState");
        den = findViewById(R.id.den);
        maybom = findViewById(R.id.maybom); 
        van = findViewById(R.id.van);
        quat = findViewById(R.id.fan);
        sw = findViewById(R.id.sw1);
        controlState.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int state = snapshot.getValue(int.class);
                if(state==1) sw.setChecked(true);
                else sw.setChecked(false);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DEN.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int deN = snapshot.getValue(int.class);
                if(deN==0) den.setChecked(true);
                else den.setChecked(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        QUAT.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int quaT = snapshot.getValue(int.class);
                if(quaT==0) quat.setChecked(true);
                else quat.setChecked(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        MAYBOM.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int mayboM = snapshot.getValue(int.class);
                if(mayboM==0) maybom.setChecked(true);
                else maybom.setChecked(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        VAN.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int vaN = snapshot.getValue(int.class);
                if(vaN==0) van.setChecked(true);
                else van.setChecked(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    controlState.setValue(1);
                    sw.setText("Automatic");
                    den.setEnabled(false);
                    quat.setEnabled(false);
                    van.setEnabled(false);
                    maybom.setEnabled(false);
                }else{
                    controlState.setValue(0);
                    sw.setText("Manual");
                    den.setEnabled(true);
                    quat.setEnabled(true);
                    van.setEnabled(true);
                    maybom.setEnabled(true);
                }
            }
        });

        den.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = den.isChecked();
                if(checked){
                    DEN.setValue(0);
                }else{
                    DEN.setValue(1);
                }
            }
        });
        maybom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = maybom.isChecked();
                if(checked){
                    MAYBOM.setValue(0);
                }else{
                    MAYBOM.setValue(1);
                }
            }
        });
        van.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = van.isChecked();
                if(checked){
                    VAN.setValue(0);
                }else{
                    VAN.setValue(1);
                }
            }
        });
        quat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = quat.isChecked();
                if(checked){
                    QUAT.setValue(0);
                }else{
                    QUAT.setValue(1);
                }
            }
        });
    }
}