package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.dialogs.BeforeRegisterDialog;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Button login = findViewById(R.id.log);
        login.setOnClickListener(v -> {
            EditText username = findViewById(R.id.editTextEmailAddress);
            String un_str = username.getText().toString();
            EditText password = findViewById(R.id.editTextPassword);
            String ps_str = password.getText().toString();
            auth.signInWithEmailAndPassword(un_str,ps_str).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Users.checkUserType(v.getContext());
                }
                else {
                    Toast.makeText(LoginActivity.this, "שם משתמש/סיסמה אינם נכונים", Toast.LENGTH_LONG).show();
                }
            });
        });
        TextView goReg = findViewById(R.id.log_opendialog);
        goReg.setOnClickListener(v -> {
            BeforeRegisterDialog brd = new BeforeRegisterDialog();
            brd.show(getSupportFragmentManager(),"search");
        });
    }
}