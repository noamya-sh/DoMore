package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class login extends Activity {
    FirebaseFirestore db;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        System.out.println(user.getUid());
        Button login = findViewById(R.id.log);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = findViewById(R.id.editTextEmailAddress);
                String un_str = username.getText().toString();
                EditText password = findViewById(R.id.editTextPassword);
                String ps_str = password.getText().toString();
                auth.signInWithEmailAndPassword(un_str,ps_str).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            checkCollection(auth,db);
//                            startActivity(new Intent(login.this, vol_home.class));
                        }
                        else {
                            Toast.makeText(login.this, "שם משתמש/סיסמה אינם נכונים", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

            private void checkCollection(FirebaseAuth auth, FirebaseFirestore db) {
                auth = FirebaseAuth.getInstance();
                db = FirebaseFirestore.getInstance();
                FirebaseUser user = auth.getCurrentUser();
                System.out.println(user.getUid());
                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                DocumentReference docIdRef = rootRef.collection("volunteers").document(user.getUid());
                docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                startActivity(new Intent(login.this, vol_home.class));
                            } else {
                                startActivity(new Intent(login.this, asso_home.class));
                            }
                        } else {
                            System.out.println("failed");
                        }
                    }
                });
            }
        });

    }
}