package com.example.myapplication.objects;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.myapplication.HomeAssoActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class Volunteering {
    final public static int INCREASE = 1,DEACRESE = -1;
    private String uid,association_name, title, location,category;
    private Date start,end;
    private DocumentReference association;
    private int phone,num_vol, num_vol_left;

    public Volunteering(){}

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
    public int getPhone() {
        return phone;
    }
    public void setPhone(int phone) {
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
                        String category, int phone, Date start, Date end,
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

    public void updateFirestore(Activity activity){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("volunteering").document(this.getUid()).set(this);
        activity.startActivity(new Intent(activity, HomeAssoActivity.class));
    }
    public void updateFirebaseVolNumLeft(int act){
        this.num_vol_left += act;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference dr = db.collection("volunteering").document(this.uid);
        dr.set(this);

    }
}
