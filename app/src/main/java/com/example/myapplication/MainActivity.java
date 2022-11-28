package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

//        Map<String, Integer> m = new HashMap<>();
//        m.put("eitan",1);
//        m.put("tamar", 2);
//        m.put("ori", 3);
//        m.put("shoham", 4);
//        db.collection("family").add(m).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(getApplicationContext(), "failure", Toast.LENGTH_SHORT).show();
//            }
//        });

        Button btn = findViewById(R.id.open_activity_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, second_activity.class));
                }
            });

        Button myButton = findViewById(R.id.first);
        myButton.setOnClickListener(this::onMyButtonClicked);

    }

    private void onMyButtonClicked(View view) {
        if (view.getId() == R.id.first) {
            startActivity(new Intent(MainActivity.this, register_association.class));
        }
    }

}