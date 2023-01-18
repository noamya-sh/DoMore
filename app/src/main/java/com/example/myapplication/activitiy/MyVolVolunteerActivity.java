package com.example.myapplication.activitiy;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.example.myapplication.databinding.ActivityMyVolVolBinding;
import com.example.myapplication.model.MyVolVolModel;
import com.example.myapplication.R;
import com.example.myapplication.activitiy.dialogs.VolunteeringDetailsDialog;
import com.example.myapplication.model.objects.Volunteering;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
//list view of all volunteering of this volunteer
public class MyVolVolunteerActivity extends FatherVolunteerMenuActivity {
    private ListView listView;
    private VolunteeringAdapter adapter;
    AlertDialog loadingDialog;
    MyVolVolModel model;
    ActivityMyVolVolBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyVolVolBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        allocateActivityTitle("ההתנדבויות שלי");
        model = new MyVolVolModel(this);

        // dialog "loading"
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_progress, null);
        builder.setView(dialogView);
        builder.setCancelable(false);
        loadingDialog = builder.create();
    }

    @Override
    protected void onStart() {
        super.onStart();
        listView = findViewById(R.id.myVolAss_ListView);
        this.listView.setOnItemClickListener((parent, view, position, id) -> {
            Volunteering v = (Volunteering) listView.getItemAtPosition(position);
            VolunteeringDetailsDialog vd = new VolunteeringDetailsDialog(v, this,
                    model, VolunteeringDetailsDialog.Type.registered);
            vd.show(getSupportFragmentManager(),"");
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadingDialog.dismiss();
    }

    public void dismissDialog() {
        this.loadingDialog.dismiss();
    }
    public void showDialog() {
        this.loadingDialog.show();
    }
    public void setData(ArrayList<Volunteering> volList) {
        adapter = new VolunteeringAdapter(this, volList);
        listView.setAdapter(adapter);
    }
    public void showNotExistVolunteering() {
        Toast.makeText(MyVolVolunteerActivity.this,"עדיין לא נרשמת להתנדבויות",Toast.LENGTH_SHORT).show();
    }

    public void notifyAdapter() {
        adapter.notifyDataSetChanged();
    }
}
