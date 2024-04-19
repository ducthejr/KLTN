package com.example.khoaluantn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    CardView cardView_sensor,cardView_control,cardView_rfid,cardView_clock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardView_sensor = findViewById(R.id.tandh);
        cardView_control = findViewById(R.id.rain);
        cardView_rfid = findViewById(R.id.water);
        cardView_clock = findViewById(R.id.clock);

        cardView_sensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, sensor.class));

            }
        });
        cardView_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, control.class));
            }
        });
        cardView_rfid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, rfid.class));
            }
        });
        cardView_clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, clock.class));
            }
        });
    }
}