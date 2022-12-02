package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
                FirebaseUser user = auth.getCurrentUser();
                if (db.collection("volunteers").document(user.getUid()).get().isSuccessful()){
                    startActivity(new Intent(login.this, vol_home.class));
                }
                else if (db.collection("associations").document(user.getUid()).get().isSuccessful()){
                    startActivity(new Intent(login.this, asso_home.class));
                }
            }
        });

    }
}