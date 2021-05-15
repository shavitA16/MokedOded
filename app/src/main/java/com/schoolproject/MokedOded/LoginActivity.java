package com.schoolproject.MokedOded;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "";
    FirebaseDatabase database;
    DatabaseReference myRef;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView invalidInputTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.newUsernameEditText);
        passwordEditText = findViewById(R.id.newPasswordEditText);
        loginButton = findViewById(R.id.addUserButton);
        invalidInputTextView = findViewById(R.id.invalidInputTextView);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invalidInputTextView.setText("");
                if(InputIsValid()){

                }
                else {

                }
            }
        });
    }

    private boolean InputIsValid(){
        if (usernameEditText.getText().toString().length() < 1 || passwordEditText.getText().toString().length() < 1 ){
            invalidInputTextView.setText("נא למלא את כל השדות");
            return false;
        }
        return true;
    }
}