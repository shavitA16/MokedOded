package com.schoolproject.MokedOded;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button issuesButton;
    Button reportButton;
    Button createUserButton;
    Button disconnectButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        disconnectButton = findViewById(R.id.disconnectButton);
        reportButton = findViewById(R.id.reportButton);
        issuesButton = findViewById(R.id.issuesButton);
        createUserButton = findViewById(R.id.createUserButton);
        String adminUID = getResources().getString(R.string.admin_uid);
        String currentUserUID = mAuth.getUid();
        if (currentUserUID.equals(adminUID)){
            issuesButton.setVisibility(View.VISIBLE);
        createUserButton.setVisibility(View.VISIBLE);

        }

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

        final Intent homeToLogin = new Intent(this, LoginActivity.class);
        disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(homeToLogin);
                finish();
            }
        });
    }
}