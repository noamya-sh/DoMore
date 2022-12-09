package com.example.myapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;

public class SecondActivity extends AppCompatActivity {
    public ArrayList<volunteering> shapeList = new ArrayList<>();

    private ListView listView;
    private VolunteeringAdapter adapter;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        listView = findViewById(R.id.shapesListView);
        adapter = new VolunteeringAdapter(this,shapeList);
        // Set the adapter for the ListView
        listView.setAdapter(adapter);

        setupData();
//        setUpList();

        listView.setOnItemClickListener((parent, view, position, id) -> System.out.println("yess"));


    }

    private void setupData() {
        db = FirebaseFirestore.getInstance();
        Date d = new Date();
        db.collection("volunteering").whereGreaterThan("start",d).get()
                .addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if (document.exists()){
                        DocumentReference f = document.getDocumentReference("association");
                        FirebaseFirestore.getInstance().document(f.getPath()).get().addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()){
                                        DocumentSnapshot document2 = task1.getResult();
                                        String association = document2.getString("name");
                                        volunteering v = new volunteering(
                                                association,
                                                document.getString("title"),
                                                document.getString("location"),
                                                document.getTimestamp("start").toDate(),
                                                document.getTimestamp("end").toDate(),
                                                document.getLong("num_vol").intValue(),
                                                document.getLong("num_vol_left").intValue());
                                        shapeList.add(v);
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                    }
                }
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
    }
}
