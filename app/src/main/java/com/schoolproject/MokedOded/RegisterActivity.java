package com.schoolproject.MokedOded;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar myToolbar = findViewById(R.id.toolbarReg);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuRegister:
                Toast.makeText(this, "אתה כבר נמצא בדף ההרשמה", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.menuLogin:
                Intent regToLog = new Intent(this, LoginActivity.class);
                startActivity(regToLog);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}