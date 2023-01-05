package com.example.myapplication.model.objects;


import com.google.firebase.firestore.DocumentReference;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Volunteering {
    private String uid,association_name, title, location, category, phone;
    private Date start,end;
    private DocumentReference association;
    private int num_vol, num_vol_left;
    private Map<String,DocumentReference> SignUpForVolunteering = new HashMap<>();

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
    public Map<String, DocumentReference> getSignUpForVolunteering() {
        return SignUpForVolunteering;
    }
    public void setSignUpForVolunteering(Map<String, DocumentReference> signUpForVolunteering) {
        SignUpForVolunteering = signUpForVolunteering;
    }

    public Volunteering(String uid, String association_name, String title, String location,
                        String category, String phone, Date start, Date end,
                        DocumentReference association, int num_vol, int num_vol_left,Map<String,DocumentReference> sufv) {
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
        this.SignUpForVolunteering = sufv;
    }


}
