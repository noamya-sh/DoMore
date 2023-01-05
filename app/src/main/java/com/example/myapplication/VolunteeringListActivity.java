package com.example.myapplication;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.model.VolunteeringListModel;
import com.example.myapplication.dialogs.DialogListener;
import com.example.myapplication.dialogs.SearchDialog;
import com.example.myapplication.dialogs.VolunteeringDetailsDialog;
import com.example.myapplication.objects.Volunteering;
import java.util.ArrayList;
import java.util.Map;

public class VolunteeringListActivity extends AppCompatActivity implements DialogListener {
    private ListView listView;
    public VolunteeringAdapter adapter;
    AlertDialog loadingDialog;
    private VolunteeringListModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteering_list);
        model = new VolunteeringListModel(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        listView = findViewById(R.id.shapesListView);

        // dialog "loading"
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_progress, null);
        builder.setView(dialogView);
        builder.setCancelable(false);
        loadingDialog = builder.create();

        initData();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Volunteering v = (Volunteering) listView.getItemAtPosition(position);
            VolunteeringDetailsDialog vd = new VolunteeringDetailsDialog(v, this,
                    model, VolunteeringDetailsDialog.Type.candidate);
            vd.show(getSupportFragmentManager(),"search");
//            AlertDialog.Builder alert = new AlertDialog.Builder(VolunteeringListActivity.this);
//            alert.setMessage("האם ברצונך להרשם להתנדבות זו?");
//            alert.setPositiveButton("רשום אותי", (dialog, which) -> {
//                Volunteering v = (Volunteering) listView.getItemAtPosition(position);
//                model.addVolunteeringToVolunteer(v);
//                //update firestore
//                model.removeVolunteering(v);
//                adapter.notifyDataSetChanged();
//
//                Toast.makeText(VolunteeringListActivity.this,"שובצת להתנדבות זו",Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }).setNegativeButton("חזור", (dialog, which) -> dialog.cancel()).create().show();
        });
        Button search = findViewById(R.id.continue_to_search);
        search.setOnClickListener(v -> {
            SearchDialog sd = new SearchDialog();
            sd.show(getSupportFragmentManager(),"search");
        });
        Button refresh = findViewById(R.id.refresh);
        refresh.setOnClickListener(v -> model.refresh());
    }
    @Override
    public void onFinishDialog(Map<String, Object> query) {
        model.search(query);
    }
    private void initData(){
        loadingDialog.show();
        model.getData();
    }
    public void setData(ArrayList<Volunteering> volList) {
        adapter = new VolunteeringAdapter(this, volList);
        listView.setAdapter(adapter);
    }
    public void dismissLoadingDialog(){
        loadingDialog.dismiss();
    }
    public void notifyAdapter() {
        adapter.notifyDataSetChanged();
    }}
