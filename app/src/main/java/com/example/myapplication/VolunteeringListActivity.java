package com.example.myapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class VolunteeringListActivity extends AppCompatActivity implements DialogListener {
    public ArrayList<Volunteering> volList = new ArrayList<>();
    Volunteer volunteer = new Volunteer();
    private ListView listView;
    private VolunteeringAdapter adapter;
    AlertDialog loadingDialog;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteering_list);
        DocumentReference dr = db.collection("volunteers").document(auth.getCurrentUser().getUid());
        dr.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                this.volunteer = task.getResult().toObject(Volunteer.class);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        listView = findViewById(R.id.shapesListView);
        adapter = new VolunteeringAdapter(this, volList);
        listView.setAdapter(adapter);

        // dialog "loading"
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_progress, null);
        builder.setView(dialogView);
        builder.setCancelable(false);
        loadingDialog = builder.create();
        loadingDialog.show();

        setupData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(VolunteeringListActivity.this);
                alert.setMessage("האם ברצונך להרשם להתנדבות זו?");
                alert.setPositiveButton("רשום אותי", (dialog, which) -> {
                    Volunteering v = (Volunteering) listView.getItemAtPosition(position);
                    //update firestore
                    volunteer.addVolunteering(v);
                    volList.remove(v);
                    adapter.notifyDataSetChanged();

                    Toast.makeText(VolunteeringListActivity.this,"שובצת להתנדבות זו",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }).setNegativeButton("חזור", (dialog, which) -> dialog.cancel()).create().show();
            }
        });
        Button search = findViewById(R.id.continue_to_search);
        search.setOnClickListener(v -> {
            SearchDialog sd = new SearchDialog();
            sd.show(getSupportFragmentManager(),"search");
        });

    }

    private void setupData() {
        db = FirebaseFirestore.getInstance();
        Date currentDate = new Date();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        CollectionReference collection = db.collection("volunteers")
                .document(user.getUid()).collection("my_volunteering");
        collection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<String> ids = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult())
                    ids.add(document.getId());
                db.collection("volunteering").whereGreaterThan("start",currentDate).get().addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()){
                        loadingDialog.dismiss();
                        List<QueryDocumentSnapshot> list = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task1.getResult())
                            if (document.exists() && document.getLong("num_vol_left") > 0)
                                list.add(document);
                        for (QueryDocumentSnapshot document : list)
                            if (ids.size() == 0 || !ids.contains(document.getId()))
                                init_vol(document);
                    }
                });
            }
        });
    }
    public void init_vol(QueryDocumentSnapshot document){
        Volunteering v = document.toObject(Volunteering.class);
        v.setUid(document.getId());
        volList.add(v);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFinishDialog(Map<String, Object> query) {
        db = FirebaseFirestore.getInstance();

        if (query.containsKey("association")){
            String substring = (String) query.get("association");
            ArrayList<Volunteering> newVol = new ArrayList<>();
            for (Volunteering v:volList){
                if (v.getAssociation_name().toUpperCase().contains(substring.toUpperCase()))
                    newVol.add(v);
            }
            adapter = new VolunteeringAdapter(this, newVol);
            listView.setAdapter(adapter);
        }
        if (query.containsKey("category")){
            ArrayList<Volunteering> newVol = new ArrayList<>();
            for (Volunteering v:volList){
                if (v.getCategory().equals(query.get("category")))
                    newVol.add(v);
            }
            adapter = new VolunteeringAdapter(this, newVol);
            listView.setAdapter(adapter);
        }
        if (query.containsKey("from")){
            ArrayList<Volunteering> newVol = new ArrayList<>();
            for (Volunteering v:volList){

                if (v.getStart().after((Date) query.get("from"))){
                    newVol.add(v);
                }
            }
            adapter = new VolunteeringAdapter(this, newVol);
            listView.setAdapter(adapter);
        }
        if (query.containsKey("un")){
            ArrayList<Volunteering> newVol = new ArrayList<>();
            for (Volunteering v:volList){
                if (v.getStart().before((Date) query.get("un"))){
                    newVol.add(v);
                }
            }
            adapter = new VolunteeringAdapter(this, newVol);
            listView.setAdapter(adapter);
        }
    }
}
