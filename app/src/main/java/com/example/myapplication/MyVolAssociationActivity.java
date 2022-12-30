package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import com.example.model.MyVolAssModel;
import com.example.myapplication.objects.Volunteering;

import java.util.ArrayList;
import java.util.Date;

public class MyVolAssociationActivity extends AppCompatActivity {
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
                Volunteering v = (Volunteering) this.listView.getItemAtPosition(position);
                Intent intent = passData(v.getUid(),v.getTitle(),v.getLocation(),v.getNum_vol(),
                        v.getStart(),v.getEnd(),v.getPhone());
                startActivity(intent);
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
        Toast.makeText(MyVolAssociationActivity.this,
                "עדיין לא הוספת התנדבויות",Toast.LENGTH_SHORT).show();
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

    public Intent passData(String id, String title, String city, int num_vol, Date from, Date un, int phone){
        Intent i = new Intent(MyVolAssociationActivity.this, EditMyVolAssActivity.class);
        i.putExtra("id",id);
        i.putExtra("title",title);
        i.putExtra("city",city);
        i.putExtra("num_vol",num_vol);
        i.putExtra("from",from.getTime());
        i.putExtra("un",un.getTime());
        i.putExtra("phone",phone);
        return i;
    }
}