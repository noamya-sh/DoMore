package com.example.myapplication.nocomplete;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.VolunteeringAdapter;
import com.example.myapplication.objects.Association;
import com.example.myapplication.objects.Volunteer;
import com.example.myapplication.objects.Volunteering;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;

public class MyVolVolunteer extends AppCompatActivity {
    public ArrayList<Volunteering> volList = new ArrayList<>();
    private ListView listView;
    private VolunteeringAdapter adapter;
    AlertDialog loadingDialog;
    Volunteer volunteer;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vol_association);
        listView = findViewById(R.id.myVolAss_ListView);
        adapter = new VolunteeringAdapter(this, volList);
        listView.setAdapter(adapter);

        // dialog "loading"
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_progress, null);
        builder.setView(dialogView);
        builder.setCancelable(false);
        loadingDialog = builder.create();
        loadingDialog.show();
        // init association

        DocumentReference dr = db.collection("volunteers").document(auth.getCurrentUser().getUid());
        dr.get().addOnSuccessListener(task -> {
            volunteer = task.toObject(Volunteer.class);
            setUpData();
        });


    }

    private void setUpData() {
        if (this.volunteer.getMy_volunteering().size() > 0){
//            DocumentReference dr = this.db.collection("associations").document(volunteer.getUid());
            Query query = this.db.collection("volunteering").whereIn("uid", Arrays.asList(this.volunteer.getMy_volunteering().keySet().toArray()));
            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        Volunteering v = document.toObject(Volunteering.class);
                        this.volList.add(v);
                        this.adapter.notifyDataSetChanged();
                    }
                }
            });
        }
        else {
            Toast.makeText(MyVolVolunteer.this,"עדיין לא נרשמת להתנדבויות",Toast.LENGTH_SHORT).show();
        }
        this.loadingDialog.dismiss();
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.listView.setOnItemClickListener((parent, view, position, id) -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
            alert.setMessage("מה ברצונך לעשות עם התנדבות זו?");
            alert.setPositiveButton("ערוך", (dialog, which) -> {
                //Todo new dialog with full details
                dialog.dismiss();
            }).setNegativeButton("מחק", (dialog, which) -> {
                Volunteering v = (Volunteering) this.listView.getItemAtPosition(position);
                this.volunteer.removeVolunteering(v);
                this.volList.remove(v);
                this.adapter.notifyDataSetChanged();
                dialog.dismiss();
            }).create().show();
            //ToDo
        });

    }
}
