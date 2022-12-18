package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = FirebaseAuth.getInstance();
        ImageButton insert = findViewById(R.id.insert);
        insert.setOnClickListener(v -> {
            if (auth.getCurrentUser() != null)
                Users.checkUserType(v.getContext());
            else
                startActivity(new Intent(MainActivity.this, LoginActivity.class));

        });
    }
}