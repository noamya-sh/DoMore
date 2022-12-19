package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.nocomplete.MyVolAssociation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeAssoActivity extends AppCompatActivity {
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
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                DocumentSnapshot document = task.getResult();
                if (document != null) {
                    String k = "שלום עמותת " + document.getString("name");
                    welcome.setText(k);
                } else {
                    Log.d("LOGGER", "No such document");
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Button pub_vol = findViewById(R.id.addvo);
        pub_vol.setOnClickListener(v ->
                startActivity(new Intent(HomeAssoActivity.this, AddVolunteeringActivity.class)));
        Button my_vol = findViewById(R.id.ah_myvol);
        my_vol.setOnClickListener(v -> startActivity(new Intent(HomeAssoActivity.this, MyVolAssociation.class)));
        ImageButton ib = findViewById(R.id.assohome_logout);
        Users.logOut(ib, HomeAssoActivity.this,this);
    }
}
