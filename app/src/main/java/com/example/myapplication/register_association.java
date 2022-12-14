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

public class register_association extends Activity {
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
            Map<String, String> m = new HashMap<>();
            m.put("name",associationName.getText().toString());
            m.put("email", email.getText().toString());
            m.put("phone",phone.getText().toString());
            m.put("password", password.getText().toString());
            m.put("category", category.getSelectedItem().toString());
            Users.register_emailAndPassowrd(register_association.this,m,"associations");
        });
    }

    private void registerAssociation(String str_email, String str_pass) {
        auth.createUserWithEmailAndPassword(str_email, str_pass).addOnCompleteListener(register_association.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(register_association.this, "Registration succeeded!", Toast.LENGTH_LONG).show();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    EditText associationName = findViewById(R.id.editTextAsssociationName);
                    EditText email = findViewById(R.id.editTextTextEmailAddress);
                    EditText password = findViewById(R.id.editTextTextPassword);
                    EditText phone = findViewById(R.id.editTextPhone);
                    Spinner category = findViewById(R.id.planets_spinner);
                    Map<String, Object> m = new HashMap<>();
//                    m.put("uid",user.getUid());
                    m.put("name",associationName.getText().toString());
                    m.put("email", email.getText().toString());
                    m.put("phone",Integer.parseInt(phone.getText().toString()));
                    m.put("password", password.getText().toString());
                    m.put("category", category.getSelectedItem().toString());
                    db.collection("associations").document(user.getUid()).set(m);
                    Users.checkUserType(auth,register_association.this);
                } else {
                    // No user is signed in
                    System.out.println("NNNNNNNN");
                }
            }
                else
                    Toast.makeText(register_association.this,"Registration failed!",Toast.LENGTH_LONG).show();
            }
        });
    }
}
