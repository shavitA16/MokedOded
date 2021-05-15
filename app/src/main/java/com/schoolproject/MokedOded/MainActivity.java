package com.schoolproject.MokedOded;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button issuesButton;
    Button reportButton;
    Button createUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reportButton = findViewById(R.id.reportButton);
        issuesButton = findViewById(R.id.issuesButton);
        createUserButton = findViewById(R.id.createUserButton);

//        issuesButton.setVisibility(View.INVISIBLE);
//        issuesButton.setClickable(false);
//        createUserButton.setVisibility(View.INVISIBLE);
//        createUserButton.setClickable(false);

        final Intent homeToReport = new Intent(this, ReportActivity.class);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             startActivity(homeToReport);
            }
        });

        final Intent homeToIssues = new Intent(this, IssuesActivity.class);
        issuesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(homeToIssues);
            }
        });

        final Intent homeToCreateUser = new Intent(this, CreateUserActivity.class);
        createUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(homeToCreateUser);
            }
        });
    }
}