package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.model.MainModel;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private MainModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model = new MainModel(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ImageButton insert = findViewById(R.id.insert);
        insert.setOnClickListener(v -> {model.navigation();});
    }

    public void goLogin() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    public void goVolHome() {
        startActivity(new Intent(MainActivity.this, HomeVolunteerActivity.class));
    }

    public void goAssHome() {
        startActivity(new Intent(MainActivity.this, HomeAssoActivity.class));
    }
}