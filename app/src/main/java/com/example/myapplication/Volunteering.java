package com.example.myapplication;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Volunteering {
    final public static int INCREASE = 1,DEACRESE = -1;
    private String uid,association_name, title, location,category,phone;
    private Date start,end;
    private DocumentReference association;
    private int num_vol, num_vol_left;

    public Volunteering(){
    }

    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getAssociation_name() {
        return association_name;
    }
    public void setAssociation_name(String association_name) {
        this.association_name = association_name;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Date getStart() {
        return start;
    }
    public void setStart(Date start) {
        this.start = start;
    }
    public Date getEnd() {
        return end;
    }
    public void setEnd(Date end) {
        this.end = end;
    }
    public DocumentReference getAssociation() {
        return association;
    }
    public void setAssociation(DocumentReference association) {
        this.association = association;
    }
    public int getNum_vol() {
        return num_vol;
    }
    public void setNum_vol(int num_vol) {
        this.num_vol = num_vol;
    }
    public int getNum_vol_left() {
        return num_vol_left;
    }
    public void setNum_vol_left(int num_vol_left) {
        this.num_vol_left = num_vol_left;
    }

    public Volunteering(String uid, String association_name, String title, String location,
                        String category, String phone, Date start, Date end,
                        DocumentReference association, int num_vol, int num_vol_left) {
        this.uid = uid;
        this.association_name = association_name;
        this.title = title;
        this.location = location;
        this.category = category;
        this.phone = phone;
        this.start = start;
        this.end = end;
        this.association = association;
        this.num_vol = num_vol;
        this.num_vol_left = num_vol_left;
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
                .collection("my_volunteering").document(v.uid);
        DocumentReference dr_vol = db.collection("volunteering").document(v.uid);
        Map<String,Object> map = new HashMap<>();
        map.put("reference",dr_vol);
        volCol.set(map);
        updateVolunteeringAmount(dr_vol,DEACRESE);
    }}

    public static void updateVolunteeringWithServer(Volunteering volunteering, String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null){
            DocumentReference dr = volunteering.getAssociation();
            dr.get().addOnCompleteListener(task -> {
                dr.update("my_volunteering.".concat(id),db.collection("volunteering").document(id));
            });
        }
    }
}
