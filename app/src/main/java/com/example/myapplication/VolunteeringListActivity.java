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
    private Volunteer volunteer = new Volunteer();
    private ListView listView;
    private VolunteeringAdapter adapter;
    private boolean CHANGED = false;
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
            if(CHANGED){
                volList.clear();
                adapter = new VolunteeringAdapter(this, volList);
                listView.setAdapter(adapter);
                setupData();
                CHANGED = false;
            }
            SearchDialog sd = new SearchDialog();
            sd.show(getSupportFragmentManager(),"search");
        });

    }

    private void setupData() {

        loadingDialog.show();
        Date currentDate = new Date();

        db.collection("volunteering").whereGreaterThan("start",currentDate)
                .get().addOnCompleteListener(task -> {
                    loadingDialog.dismiss();
                    if (task.isSuccessful())
                        for (QueryDocumentSnapshot document : task.getResult())
                            if (!volunteer.my_volunteering.containsKey(document.getId()) &&
                                    document.getLong("num_vol_left") > 0){
                                init_vol(document);
                            }
                });
//        this.CHANGED =false;
    }
    public void init_vol(QueryDocumentSnapshot document){
        Volunteering v = document.toObject(Volunteering.class);
        v.setUid(document.getId());
        volList.add(v);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFinishDialog(Map<String, Object> query) {
        ArrayList<Volunteering> newVol = new ArrayList<>(volList);
        if (query.containsKey("association")){
            String substring = (String) query.get("association");
            for (Volunteering v:volList){
                if (!v.getAssociation_name().toUpperCase().contains(substring.toUpperCase()))
                    newVol.remove(v);
            }
        }
        if (query.containsKey("category")){
            for (Volunteering v:volList){
                if (!v.getCategory().equals(query.get("category")))
                    newVol.remove(v);
            }
        }
        if (query.containsKey("from")){
            for (Volunteering v:volList){
                if (v.getStart().before((Date) query.get("from"))){
                    newVol.remove(v);
                }
            }
        }
        if (query.containsKey("un")){
            for (Volunteering v:volList){
                if (v.getStart().after((Date) query.get("un"))){
                    newVol.remove(v);
                }
            }
        }
        volList = newVol;
        adapter = new VolunteeringAdapter(this, volList);
        listView.setAdapter(adapter);
        CHANGED =true;
    }
}
