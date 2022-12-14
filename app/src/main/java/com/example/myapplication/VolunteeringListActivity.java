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

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class VolunteeringListActivity extends AppCompatActivity implements DialogListener {
    public ArrayList<Volunteering> volList = new ArrayList<>();

    private ListView listView;
    private VolunteeringAdapter adapter;
    AlertDialog loadingDialog;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteering_list);
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
                    //update firestore:
                    Volunteering.updateVolunteeringWithServer(v);
                    //remove from listview
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
        db.collection("volunteering").whereGreaterThan("start",currentDate)
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if (document.exists() && document.getLong("num_vol_left") > 0){
                        init_vol(document);
                        loadingDialog.dismiss();
                    }
                }
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
    }
    public void init_vol(QueryDocumentSnapshot document){
        DocumentReference f = document.getDocumentReference("association");
        DocumentReference dd = db.document(f.getPath());
        dd.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                DocumentSnapshot document2 = task.getResult();
                String association = document2.getString("name");
                Volunteering v = new Volunteering(
                        document.getId(),
                        association,
                        document.getString("title"),
                        document.getString("location"),
                        document.getTimestamp("start").toDate(),
                        document.getTimestamp("end").toDate(),
                        document.getLong("num_vol").intValue(),
                        document.getLong("num_vol_left").intValue());
                volList.add(v);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onFinishDialog(Map<String, Object> query) {
        db = FirebaseFirestore.getInstance();

        if (query.containsKey("association"))
            System.out.println(query.get("association"));
    }
}
