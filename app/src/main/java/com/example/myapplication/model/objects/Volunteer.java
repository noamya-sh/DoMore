package com.example.myapplication.model.objects;

import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

//volunteer class for volunteer user.

public class Volunteer {
    String uid,name,city,email,password,phone;
    Map<String, DocumentReference> my_volunteering = new HashMap<>();
    public Volunteer(){}
    public Volunteer(String uid, String name, String city, String email, String password, String phone,
                     Map<String, DocumentReference> my_volunteering) {
        this.uid = uid;
        this.name = name;
        this.city = city;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.my_volunteering = my_volunteering;
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
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
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
