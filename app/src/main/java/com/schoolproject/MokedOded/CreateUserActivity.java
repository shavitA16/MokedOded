package com.schoolproject.MokedOded;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class CreateUserActivity extends AppCompatActivity {
    private static final String TAG = "Signup Activity";

    private EditText newUsernameEditText;
    private EditText newPasswordEditText;
    private Button addUserButton;
    private CheckBox adminCheckBox;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        mAuth = FirebaseAuth.getInstance();
        updateUI(mAuth.getCurrentUser());

        newUsernameEditText = findViewById(R.id.emailEditText);
        newPasswordEditText = findViewById(R.id.passwordEditText);
        addUserButton = findViewById(R.id.addUserButton);
        adminCheckBox = findViewById(R.id.adminCheckBox);



        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
    }


    private void createUser(){
        String email = newUsernameEditText.getText().toString();
        String password = newPasswordEditText.getText().toString();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            // email input is not valid
            newUsernameEditText.setError("Please enter a valid email");
            newUsernameEditText.requestFocus();
            return;
        }
        if (password.isEmpty()){
            newPasswordEditText.setError("Please enter a valid password");
            newPasswordEditText.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(CreateUserActivity.this, "המשתמש נוסף בהצלחה", Toast.LENGTH_SHORT).show();
                    updateUI(user);
                    finish();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(CreateUserActivity.this, "הוספת המשתמש נכשלה",
                            Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }
    private void updateUI(FirebaseUser user) {
        /*if (user == null) return;

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();*/
    }

}