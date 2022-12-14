package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register_volunteer extends Activity {
    FirebaseFirestore db;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_volunteer);
//        EditText email = (EditText)findViewById(R.id.volEmailAddress);
//        EditText password = (EditText)findViewById(R.id.volPassword);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        Button register = findViewById(R.id.regvol);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String str_email = email.getText().toString();
//                String str_pass =  password.getText().toString();
                EditText name =  findViewById(R.id.volunteerName);
                EditText email =  findViewById(R.id.volEmailAddress);
                EditText password = findViewById(R.id.volPassword);
                EditText phone = findViewById(R.id.volPhone);
                Spinner cities = findViewById(R.id.cities_spinner);
                Map<String,String> m = new HashMap<>();
                m.put("name", name.getText().toString());
                m.put("email", email.getText().toString());
                m.put("phone", phone.getText().toString());
                m.put("password", password.getText().toString());
                m.put("city", cities.getSelectedItem().toString());
                Users.register_emailAndPassowrd(register_volunteer.this,m,"volunteers");
//                registerAssociation(str_email,str_pass);
            }
        });
    }
}
