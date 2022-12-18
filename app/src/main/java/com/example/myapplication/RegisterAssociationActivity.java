package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterAssociationActivity extends Activity {
    FirebaseFirestore db;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_association);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        Button register = findViewById(R.id.acc);
        register.setOnClickListener(view -> {
            EditText associationName = findViewById(R.id.editTextAsssociationName);
            EditText email = findViewById(R.id.editTextTextEmailAddress);
            EditText password = findViewById(R.id.editTextTextPassword);
            EditText phone = findViewById(R.id.editTextPhone);
            Spinner category = findViewById(R.id.planets_spinner);
            Map<String, Object> m = new HashMap<>();
            m.put("name",associationName.getText().toString());
            m.put("email", email.getText().toString());
            m.put("phone",Integer.parseInt(phone.getText().toString()));
            m.put("password", password.getText().toString());
            m.put("category", category.getSelectedItem().toString());
            Users.register_emailAndPassowrd(RegisterAssociationActivity.this,m,"associations");
        });
    }
}
