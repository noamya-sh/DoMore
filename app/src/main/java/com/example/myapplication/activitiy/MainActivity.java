package com.example.myapplication.activitiy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import com.example.myapplication.model.MainModel;
import com.example.myapplication.R;
import com.example.myapplication.model.apiserver.RegisterAPI;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private MainModel model;
//    EditText NoPhone;
//    FloatingActionButton but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model = new MainModel(this);

//        final String url = "http://localhost";


    }

    @Override
    protected void onStart() {
        super.onStart();
        ImageButton insert = findViewById(R.id.insert);
        insert.setOnClickListener(v ->
                model.navigation()
//                Post2Api("4534534","bgfbdfdf")
        );

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