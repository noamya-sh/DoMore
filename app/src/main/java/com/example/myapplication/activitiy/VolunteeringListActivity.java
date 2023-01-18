package com.example.myapplication.activitiy;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityMyVolVolBinding;
import com.example.myapplication.databinding.ActivityVolunteeringListBinding;
import com.example.myapplication.model.VolunteeringListModel;
import com.example.myapplication.R;
import com.example.myapplication.activitiy.dialogs.DialogListener;
import com.example.myapplication.activitiy.dialogs.SearchDialog;
import com.example.myapplication.activitiy.dialogs.VolunteeringDetailsDialog;
import com.example.myapplication.model.objects.Volunteering;
import java.util.ArrayList;
import java.util.Map;

// Display screen of the available volunteers
public class VolunteeringListActivity extends FatherVolunteerMenuActivity implements DialogListener {
    private ListView listView;
    public VolunteeringAdapter adapter;
    AlertDialog loadingDialog;
    private VolunteeringListModel model;
    ActivityVolunteeringListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVolunteeringListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        allocateActivityTitle("חיפוש התנדבויות");
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
        });
        Button search = findViewById(R.id.continue_to_search);
        search.setOnClickListener(v -> {
            SearchDialog sd = new SearchDialog();
            sd.show(getSupportFragmentManager(),"search");
        });
        Button refresh = findViewById(R.id.refresh);
        refresh.setOnClickListener(v -> model.refresh());
    }

    //pass search query from dialog
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
