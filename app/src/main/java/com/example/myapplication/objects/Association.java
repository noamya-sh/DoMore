package com.example.myapplication.objects;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class Association implements Serializable {
    private String uid,name,email,password,category,phone;
    private Map<String,DocumentReference> my_volunteering = new HashMap<>();


    public Association(){}
    public Association(String uid, String name, String email, String password, String category,
                       String phone, Map<String, DocumentReference> volunteering) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.password = password;
        this.category = category;
        this.phone = phone;
        this.my_volunteering = volunteering;
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
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Map<String, DocumentReference> getMy_volunteering() {
        return my_volunteering;
    }
    public void setMy_volunteering(Map<String, DocumentReference> my_volunteering) {
        this.my_volunteering = my_volunteering;
    }

    public void removeVolunteering(Volunteering v){
        this.my_volunteering.remove(v.getUid());
    }
}
