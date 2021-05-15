package com.schoolproject.MokedOded;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateUserActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    private EditText newUsernameEditText;
    private EditText newPasswordEditText;
    private Button addUserButton;
    private CheckBox adminCheckBox;
    private TextView errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        newUsernameEditText = findViewById(R.id.newUsernameEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        addUserButton = findViewById(R.id.addUserButton);
        adminCheckBox = findViewById(R.id.adminCheckBox);
        errorTextView = findViewById(R.id.errorTextView);

        errorTextView.setText("");
        Toast onSuccessToast = Toast.makeText(this, "המשתמש נוסף בהצלחה", Toast.LENGTH_SHORT);

        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newPasswordEditText.getText().toString().length() < 1  || newUsernameEditText.getText().toString().length() < 1){
                    errorTextView.setText("נא למלא את כל השדות");
                }
                else {
                    NewUser u = NewUser.getInstance();
                    u.username = newUsernameEditText.getText().toString();
                    u.password = newPasswordEditText.getText().toString();
                    u.isAdmin = String.valueOf(adminCheckBox.isChecked());
                    database = FirebaseDatabase.getInstance("https://the-moked-81b25-default-rtdb.europe-west1.firebasedatabase.app/");
                    myRef = database.getReference("users");
                    String uid = String.valueOf(System.currentTimeMillis());
                    myRef.child(uid).setValue(u);
                    onSuccessToast.show();
                    finish();
                }
            }
        });
    }

}