package com.example.myapplication;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Volunteering {
    final public static int INCREASE = 1,DEACRESE = -1;
    String ID,association, title, location,category;
    Date start_date,end_date;
    int number_vol, number_vol_left;

    public Volunteering(String id,String association, String title, String location, Date start_date, Date end_date, int number_vol, int number_vol_left,String category) {
        this.ID = id;
        this.association = association;
        this.title = title;
        this.location = location;
        this.start_date = start_date;
        this.end_date = end_date;
        this.number_vol = number_vol;
        this.number_vol_left = number_vol_left;
        this.category = category;
    }
    public static void updateVolunteeringAmount(DocumentReference dr,int x){
        dr.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                DocumentSnapshot document = task.getResult();
                int res = Objects.requireNonNull(document.getLong("num_vol_left")).intValue();
                Map<String,Object> map = new HashMap<>();
                map.put("num_vol_left",res+x);
                dr.update(map);
            }
        });
    }
    public static void updateVolunteeringWithServer(Volunteering v){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null){
            DocumentReference volCol = db.collection("volunteers").document(user.getUid())
                .collection("my_volunteering").document(v.ID);
        DocumentReference dr_vol = db.collection("volunteering").document(v.ID);
        Map<String,Object> map = new HashMap<>();
        map.put("reference",dr_vol);
        volCol.set(map);
        updateVolunteeringAmount(dr_vol,DEACRESE);
    }}

    public static void updateVolunteeringWithServer(Map<String, Object> m, String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null){
            DocumentReference dr = (DocumentReference) m.get("association");
            assert dr != null;
            dr.get().addOnCompleteListener(task -> {
                DocumentReference volCol = db.collection("associations").document(user.getUid())
                        .collection("my_volunteering").document(id);
                Map<String,Object> map = new HashMap<>();
                map.put("reference",db.collection("volunteering").document(id));
                volCol.set(map);
            });
        }
    }
}
