package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.objects.Association;
import com.example.myapplication.objects.Volunteering;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class MyVolAssociation extends AppCompatActivity {
    public ArrayList<Volunteering> volList = new ArrayList<>();
    private ListView listView;
    private VolunteeringAdapter adapter;
    AlertDialog loadingDialog;
    Association a;
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

        DocumentReference dr = db.collection("associations").document(auth.getCurrentUser().getUid());
        dr.get().addOnSuccessListener(task -> {
            a = task.toObject(Association.class);
        });
        setUpData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //ToDo
            }
        });

    }

    private void setUpData() {
        if (a.getMy_volunteering().size() > 0){
            DocumentReference dr = db.collection("associations").document(a.getUid());
            Query query = db.collection("volunteering").whereEqualTo("association",a.getUid());
            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        Volunteering v = document.toObject(Volunteering.class);
                        volList.add(v);
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }
        else {
            Toast.makeText(MyVolAssociation.this,"עדיין לא הוספת התנדבויות",Toast.LENGTH_SHORT).show();
        }
        loadingDialog.dismiss();
    }
}