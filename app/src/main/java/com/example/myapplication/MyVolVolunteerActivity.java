package com.example.myapplication;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.model.MyVolVolModel;
import com.example.myapplication.objects.Volunteering;
import java.util.ArrayList;

public class MyVolVolunteerActivity extends AppCompatActivity {
    private ListView listView;
    private VolunteeringAdapter adapter;
    AlertDialog loadingDialog;
    MyVolVolModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vol_association);
        model = new MyVolVolModel(this);

        // dialog "loading"
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_progress, null);
        builder.setView(dialogView);
        builder.setCancelable(false);
        loadingDialog = builder.create();
        loadingDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        listView = findViewById(R.id.myVolAss_ListView);
        this.listView.setOnItemClickListener((parent, view, position, id) -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
            alert.setMessage("מה ברצונך לעשות עם התנדבות זו?");
            alert.setNegativeButton("מחק", (dialog, which) -> {
                Volunteering v = (Volunteering) this.listView.getItemAtPosition(position);
                model.removeVolunteeringFromVolunteer(v);
                dialog.dismiss();
            }).create().show();
            //ToDo
        });
    }

    public void dismissDialog() {
        this.loadingDialog.dismiss();
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
