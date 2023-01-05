package com.example.myapplication.activitiy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.model.LoginModel;
import com.example.myapplication.R;
import com.example.myapplication.activitiy.dialogs.BeforeRegisterDialog;

public class LoginActivity extends AppCompatActivity {
    private LoginModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        model = new LoginModel(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Button login = findViewById(R.id.log);
        login.setOnClickListener(v -> {
            EditText username = findViewById(R.id.editTextEmailAddress);
            EditText password = findViewById(R.id.editTextPassword);
            model.login(username.getText().toString(), password.getText().toString());
        });
        TextView goReg = findViewById(R.id.log_opendialog);
        goReg.setOnClickListener(v -> {
            BeforeRegisterDialog brd = new BeforeRegisterDialog();
            brd.show(getSupportFragmentManager(),"search");
        });
    }
    public void FailureLogin() {
        Toast.makeText(LoginActivity.this, "שם משתמש/סיסמה אינם נכונים", Toast.LENGTH_LONG).show();
    }

    public void goAssHome() {
        startActivity(new Intent(LoginActivity.this, HomeAssoActivity.class));
    }

    public void goVolHome() {
        startActivity(new Intent(LoginActivity.this, HomeVolunteerActivity.class));
    }
}