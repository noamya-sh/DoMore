package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class asso_home extends AppCompatActivity {
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asso_home);
        db = FirebaseFirestore.getInstance();
        TextView welcome = findViewById(R.id.welcomAssociationName);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        DocumentReference docRef = db.collection("associations").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    System.out.println(document);
                    if (document != null) {
                        String k = "שלום עמותת " + document.getString("name");
                        welcome.setText(k);
                    } else {
                        Log.d("LOGGER", "No such document");
                    }
                }
            }
        });
        Button pub_vol = findViewById(R.id.addvo);
        pub_vol.setOnClickListener(v ->
                startActivity(new Intent(asso_home.this, AddVolunteeringActivity.class)));
        ImageButton ib = findViewById(R.id.assohome_logout);
        Users.logOut(ib,asso_home.this,this);
    }
}
