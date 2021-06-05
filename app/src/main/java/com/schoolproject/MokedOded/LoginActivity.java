package com.schoolproject.MokedOded;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "Login Activity";
    FirebaseDatabase database;
    DatabaseReference myRef;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView invalidInputTextView;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        updateUI(mAuth.getCurrentUser());

        usernameEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.addUserButton);
        invalidInputTextView = findViewById(R.id.invalidInputTextView);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invalidInputTextView.setText("");
                loginUser();
            }
        });
    }

    private void loginUser(){
        String email = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            // email input is not valid
            usernameEditText.setError("Please enter a valid email");
            usernameEditText.requestFocus();
            return;
        }
        if (password.isEmpty()){
            passwordEditText.setError("Please enter a valid password");
            passwordEditText.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });

    }

    private void updateUI(FirebaseUser user) {
        if (user == null) return;

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean InputIsValid(){
        if (usernameEditText.getText().toString().length() < 1 || passwordEditText.getText().toString().length() < 1 ){
            invalidInputTextView.setText("נא למלא את כל השדות");
            return false;
        }
        return true;
    }
}