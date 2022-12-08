package com.example.myapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivitySecondBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class SecondActivity extends AppCompatActivity {
    public static ArrayList<volunteering> shapeList = new ArrayList<volunteering>();

    private ListView listView;

//    private ArrayList<String> selectedFilters = new ArrayList<String>();
//    private String currentSearchText = "";
//    private SearchView searchView;
//    ActivitySecondBinding bind;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        setupData();
        setUpList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("yess");
            }
        });


    }

    private void setupData() {
        db = FirebaseFirestore.getInstance();
        Date d = new Date();
        db.collection("volunteering").whereGreaterThan("start",d).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            @SuppressLint("SetTextI18n")
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.exists()){
                            String association;
                            DocumentReference f = document.getDocumentReference("association");
                            Task<DocumentSnapshot> task2 = FirebaseFirestore.getInstance()
                                    .document(f.getPath()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()){
                                        DocumentSnapshot document2 = task.getResult();
                                        String association = document2.getString("name");
                                        volunteering v = new volunteering(
                                                association,
                                                document.getString("title"),
                                                document.getString("location"),
                                                Objects.requireNonNull(document.getTimestamp("start")).toDate(),
                                                Objects.requireNonNull(document.getTimestamp("end")).toDate(),
                                                Objects.requireNonNull(document.getLong("num_vol")).intValue(),
                                                Objects.requireNonNull(document.getLong("num_vol_left")).intValue());
                                        System.out.println(association);
                                        System.out.println(v);
                                        shapeList.add(v);
                                        Log.d(TAG, document.getId() + " => " + document.getData());

                                    }
                                }
                            });
                        }}
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }

//            private String get_association(DocumentReference dr) {
//                Task<DocumentSnapshot> task = dr.get();
//                if (task.isSuccessful()){
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()){
//                        System.out.println(document.getString("name"));
//                        return document.getString("name");
//                    }
//                }
//                return null;
//            }
        });
    }

    private void setAdapter(ArrayList<volunteering> shapeList) {
        listAdapterVolunteering adapter = new listAdapterVolunteering(getApplicationContext(), 0, shapeList);
        listView.setAdapter(adapter);
    }
    private void setUpList() {
        listView = (ListView) findViewById(R.id.shapesListView);
        setAdapter(shapeList);
        listView.setVisibility(View.VISIBLE);
    }


}
