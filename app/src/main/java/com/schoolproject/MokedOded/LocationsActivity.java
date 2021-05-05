package com.schoolproject.MokedOded;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LocationsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Button north1Button = findViewById(R.id.north1Button);
        Button north2Button = findViewById(R.id.north2Button);
        Button north3Button = findViewById(R.id.north3Button);
        Button south1Button = findViewById(R.id.south1Button);
        Button south2Button = findViewById(R.id.south2Button);
        Button south3Button = findViewById(R.id.south3Button);
        Button mainGardenButton = findViewById(R.id.mainGardenButton);
        Button northGardenButton = findViewById(R.id.northGardenButton);
        Button southGardenButton = findViewById(R.id.southGardenButton);

        north1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton s = Singleton.getInstance();
                s.location = 1;
                finish();
            }
        });
        north2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton s = Singleton.getInstance();
                s.location = 2;
                finish();
            }
        });
        north3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton s = Singleton.getInstance();
                s.location = 3;
                finish();
            }
        });
        south1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton s = Singleton.getInstance();
                s.location = 11;
                finish();
            }
        });
        south2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton s = Singleton.getInstance();
                s.location = 12;
                finish();
            }
        });
        south3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton s = Singleton.getInstance();
                s.location = 13;
                finish();
            }
        });
        northGardenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton s = Singleton.getInstance();
                s.location = 91;
                finish();
            }
        });
        southGardenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton s = Singleton.getInstance();
                s.location = 92;
                finish();
            }
        });
        mainGardenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton s = Singleton.getInstance();
                s.location = 93;
                finish();
            }
        });
    }
}