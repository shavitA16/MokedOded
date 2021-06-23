package com.schoolproject.MokedOded;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private EditText EmailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView invalidInputTextView;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar myToolbar = findViewById(R.id.toolbarLog);
        setSupportActionBar(myToolbar);

        mAuth = FirebaseAuth.getInstance();
        updateUI(mAuth.getCurrentUser());

        EmailEditText = findViewById(R.id.emailEditText);
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
        String email = EmailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            // email input is not valid
            EmailEditText.setError("Please enter a valid email");
            EmailEditText.requestFocus();
            return;
        }
        if (password.isEmpty()){
            passwordEditText.setError("Please enter a valid password");
            passwordEditText.requestFocus();
            return;
        }if (password.length()<6){
            passwordEditText.setError("password must be at least 6 characters");
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
                    Toast.makeText(LoginActivity.this, "שלום "+ user.getEmail(),
                            Toast.LENGTH_SHORT).show();
                    updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "התחברות נכשלה",
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuLogin:
                Toast.makeText(this, "אתה כבר נמצא בדף ההתחברות", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuRegister:
                Intent logToReg = new Intent(this, RegisterActivity.class);
                startActivity(logToReg);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}