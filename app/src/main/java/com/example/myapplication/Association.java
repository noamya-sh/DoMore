package com.example.myapplication;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class Association {
    String uid,name,email,password,category;
    int phone;
    Map<String,DocumentReference> my_volunteering = new HashMap<>();


    public Association(){}
    public Association(String uid, String name, String email, String password, String category,
                       int phone, Map<String, DocumentReference> volunteering) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.password = password;
        this.category = category;
        this.phone = phone;
        this.my_volunteering = volunteering;
    }

    public Association(DocumentReference dr){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        dr.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                DocumentSnapshot ds = task.getResult();
                this.uid = ds.getId();
                this.category = ds.getString("category");
                this.email = ds.getString("email");
                this.password = ds.getString("category");
                this.name = ds.getString("category");
                this.phone = Objects.requireNonNull(ds.getDouble("phone")).intValue();
                this.my_volunteering = new HashMap<>();
                dr.collection("my_volunteering").get().addOnCompleteListener(task1 -> {
                    if(task1.isSuccessful())
                        for(QueryDocumentSnapshot doc: task1.getResult())
                            if (doc.exists())
                                my_volunteering.put(doc.getId(), doc.getDocumentReference("refernce"));
                });
            }
        });
    }

    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public int getPhone() {
        return phone;
    }
    public void setPhone(int phone) {
        this.phone = phone;
    }
    public Map<String, DocumentReference> getMy_volunteering() {
        return my_volunteering;
    }
    public void setMy_volunteering(Map<String, DocumentReference> my_volunteering) {
        this.my_volunteering = my_volunteering;
    }

    public void updateFirestore(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("associations").document(this.uid).set(this);
    }
    public void addVolunteering(Volunteering v){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        this.my_volunteering.put(v.getUid(),db.collection("volunteering").document(v.getUid()));
        updateFirestore();
    }
    public void removeVolunteering(Volunteering v){
        if (this.my_volunteering.containsKey(v.getUid())){
            this.my_volunteering.remove(v.getUid());
            updateFirestore();
        }
    }
}
