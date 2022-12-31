package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.model.EditMyDetailsVolModel;

public class EditMyDetailsVolActivity extends AppCompatActivity {
    private EditMyDetailsVolModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_volunteer);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
