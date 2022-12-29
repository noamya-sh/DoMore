package com.example.myapplication.nocomplete;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import com.example.model.MyVolAssModel;
import com.example.myapplication.R;
import com.example.myapplication.VolunteeringAdapter;
import com.example.myapplication.objects.Volunteering;

import java.util.ArrayList;

public class MyVolAssociation extends AppCompatActivity {
    private ListView listView;
    private VolunteeringAdapter adapter;
    private MyVolAssModel model;
    AlertDialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vol_association);
        model = new MyVolAssModel(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        listView = findViewById(R.id.myVolAss_ListView);
        // dialog "loading"
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_progress, null);
        builder.setView(dialogView);
        builder.setCancelable(false);
        loadingDialog = builder.create();
        loadingDialog.show();

        this.listView.setOnItemClickListener((parent, view, position, id) -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
            alert.setMessage("מה ברצונך לעשות עם התנדבות זו?");
            alert.setPositiveButton("ערוך", (dialog, which) -> {
                //Todo new dialog with full details
                dialog.dismiss();
            }).setNegativeButton("מחק", (dialog, which) -> {
                Volunteering v = (Volunteering) this.listView.getItemAtPosition(position);
                model.removeVolunteering(v);
                dialog.dismiss();
            }).create().show();
            //ToDo
        });

    }

    public void showNotExistVolunteering() {
        Toast.makeText(MyVolAssociation.this,"עדיין לא הוספת התנדבויות",Toast.LENGTH_SHORT).show();
    }
    public void dismissDialog(){
        this.loadingDialog.dismiss();
    }

    public void setData(ArrayList<Volunteering> volList) {
        adapter = new VolunteeringAdapter(this, volList);
        listView.setAdapter(adapter);
    }

    public void notifyAdapter() {
        adapter.notifyDataSetChanged();
    }
}