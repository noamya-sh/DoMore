package com.example.myapplication.model;
import com.example.myapplication.db.UserDB;
import com.example.myapplication.db.VolunteerDB;
import com.example.myapplication.activitiy.EditMyDetailsVolActivity;
import com.example.myapplication.model.objects.Volunteer;

public class EditMyDetailsVolModel {
    Volunteer volunteer;
    VolunteerDB vdb = new VolunteerDB();
    UserDB udb = new UserDB();
    EditMyDetailsVolActivity activity;

    public EditMyDetailsVolModel(EditMyDetailsVolActivity activity){
        this.activity = activity;
        initVolunteer();
    }

    private void initVolunteer() {
        vdb.getVolunteer(doc -> {
            volunteer = doc.toObject(Volunteer.class);
            activity.setDetails(volunteer.getName(),volunteer.getCity(),
                    volunteer.getEmail(),volunteer.getPhone());
        });
    }

    public boolean getChanges(String name, String city, String phone, String password, String ver_pass) {
        boolean changed = false;
        if (name.length()>0 && !volunteer.getName().equals(name)){
            volunteer.setName(name);
            changed = true;
        }
        if (city.length()>0 && !volunteer.getCity().equals(city)){
            volunteer.setCity(city);
            changed = true;
        }
        if (phone.length()>0 && !volunteer.getPhone().equals(phone)){
            volunteer.setPhone(phone);
            changed = true;
        }
        if (password.length() > 0){
            if (!password.equals(ver_pass))
                return false;
            else{
                volunteer.setPassword(password);
                udb.updatePassword(password);
                changed = true;
            }
        }
        if (changed)
            vdb.setVolunteer(volunteer);
        return true;
    }
}
