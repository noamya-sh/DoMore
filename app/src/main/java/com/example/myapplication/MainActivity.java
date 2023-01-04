package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import com.example.model.MainModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {
    private MainModel model;
//    EditText NoPhone;
//    FloatingActionButton but;

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
        insert.setOnClickListener(v -> model.navigation());

//        but = findViewById(R.id.floatingActionButton);
//        NoPhone = findViewById(R.id.fffff);
//        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},100);
//        }
//        but.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String phone = NoPhone.getText().toString();
//                Intent i = new Intent(Intent.ACTION_CALL);
//                i.setData(Uri.parse("tel:"+phone));
//                startActivity(i);
//            }
//        });
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