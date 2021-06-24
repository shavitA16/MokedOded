package com.schoolproject.MokedOded;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class IssuesActivity extends AppCompatActivity {

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    Switch statusFilterSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues);



        Intent loading = new Intent(this, LoadingActivity.class);
        startActivity(loading);

        ArrayList<Issue> issuesList = new ArrayList<Issue>();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance("https://the-moked-81b25-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("issues");
        RecyclerView recyclerView = findViewById(R.id.issuesRecyclerView);
        statusFilterSwitch = findViewById(R.id.statusFilterSwitch);

        ValueEventListener getData = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                issuesList.clear();
                for (DataSnapshot child: snapshot.getChildren()){
                    if (child.getKey().equals("cheat")){
                        continue;
                    }
                    String path = child.getKey();
                    int location =  Integer.parseInt(child.child("location").getValue().toString());
                    int issue = Integer.parseInt(child.child("issue").getValue().toString());
                    String description = child.child("description").getValue().toString();
                    String date = child.child("date").getValue().toString();
                    String imgURL = child.child("imgURL").getValue().toString();
                    String status = child.child("status").getValue().toString();
                    String userEmail = child.child("userEmail").getValue().toString();
                    if (statusFilterSwitch.isChecked()){
                        if (status.equals("לא טופל")){
                            issuesList.add(new Issue(path, issue, location, description, date, imgURL, status, userEmail));
                        }
                    }
                    else {
                        issuesList.add(new Issue(path, issue, location, description, date, imgURL, status, userEmail));
                    }
                }
                issueAdapter mIssueAdapter = new issueAdapter(issuesList, IssuesActivity.this, mDatabaseRef);
                recyclerView.setAdapter(mIssueAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(IssuesActivity.this));
                recyclerView.setHasFixedSize(true);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(IssuesActivity.this, "something is broken somewhere", Toast.LENGTH_SHORT).show();
                finish();
            }
        };



        mDatabaseRef.addValueEventListener(getData);
        mDatabaseRef.addListenerForSingleValueEvent(getData);

        statusFilterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mDatabaseRef.child("cheat").child("description").setValue(String.valueOf(System.currentTimeMillis()));
            }
        });
    }
}