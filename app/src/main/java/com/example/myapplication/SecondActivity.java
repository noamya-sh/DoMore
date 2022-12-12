package com.example.myapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class SecondActivity extends AppCompatActivity implements DialogListener {
    public ArrayList<volunteering> volList = new ArrayList<>();

    private ListView listView;
    private VolunteeringAdapter adapter;
    private Button search;
    AlertDialog dialog;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        listView = findViewById(R.id.shapesListView);
        adapter = new VolunteeringAdapter(this, volList);
        listView.setAdapter(adapter);
//        Activity activity = getParent();

//        SearchDialog sd = new SearchDialog();
//        sd.show(getSupportFragmentManager(),"search");
        // dialog "loading"
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_progress, null);
        builder.setView(dialogView);
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();

        setupData();
        listView.setOnItemClickListener((parent, view, position, id) -> System.out.println("yess"));
        Button search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                func();

                SearchDialog sd = new SearchDialog();
                sd.show(getSupportFragmentManager(),"search");
//                Spinner cat = findViewById(R.id.search_category);
//                String s = cat.getSelectedItem().toString();
//                ArrayList<volunteering> new_volList = new ArrayList<>();

            }
        });
        Button calendarButton = findViewById(R.id.calendar_button);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the calendar here
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(java.util.Calendar.HOUR);
                int minute = calendar.get(java.util.Calendar.MINUTE);
                DatePicker datePicker = new DatePicker(v.getContext());
                datePicker.init(year, month, day, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setView(datePicker);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Get the selected date from the date picker
                        int year = datePicker.getYear();
                        int month = datePicker.getMonth();
                        int day = datePicker.getDayOfMonth();

                        // Create a time picker and set the selected hour and minute
                        TimePicker timePicker = new TimePicker(v.getContext());
                        timePicker.setHour(hour);
                        timePicker.setMinute(minute);

                        AlertDialog.Builder timePickerBuilder = new AlertDialog.Builder(v.getContext());
                        timePickerBuilder.setView(timePicker);
                        timePickerBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Get the selected time from the time picker
                                int hour = timePicker.getHour();
                                int minute = timePicker.getMinute();

                                // Do something with the selected date and time
                            }
                        });
                        // Show the time picker dialog
                        timePickerBuilder.show();
                    }
                });

                // Show the date picker dialog
                builder.show();
            }
        });
    }

    private void func() {
        // Create a new AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

// Set the title of the dialog
        builder.setTitle("My Dialog");

// Set the message of the dialog
        builder.setMessage("Please enter some text and select the checkbox:");

// Create a LinearLayout to hold the views
        ConstraintLayout layout = new ConstraintLayout(this);
//        layout.setOrientation(LinearLayout.VERTICAL);

// Create an EditText view
        final EditText input = new EditText(this);

// Create a CheckBox view
        final CheckBox checkBox = new CheckBox(this);

// Add the EditText and CheckBox views to the LinearLayout
        layout.addView(input);
        layout.addView(checkBox);

// Set the layout of the dialog to the LinearLayout
        builder.setView(layout);

// Set the positive button to submit the input
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get the text from the EditText
                String text = input.getText().toString();

                // Get the state of the CheckBox
                boolean isChecked = checkBox.isChecked();

                // Do something with the text and checkbox state
            }
        });

// Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setupData() {
        db = FirebaseFirestore.getInstance();
        Date currentDate = new Date();
        db.collection("volunteering").whereGreaterThan("start",currentDate).get()
                .addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if (document.exists()){
                        init_vol(document);
                        dialog.dismiss();
                    }
                }
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
    }
    public void init_vol(QueryDocumentSnapshot document){
        DocumentReference f = document.getDocumentReference("association");
        DocumentReference dd = db.document(f.getPath());
        dd.get().addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()){
                DocumentSnapshot document2 = task1.getResult();
                String association = document2.getString("name");
                volunteering v = new volunteering(
                        association,
                        document.getString("title"),
                        document.getString("location"),
                        document.getTimestamp("start").toDate(),
                        document.getTimestamp("end").toDate(),
                        document.getLong("num_vol").intValue(),
                        document.getLong("num_vol_left").intValue());
                volList.add(v);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onFinishDialog(Map<String, Object> inputText) {
        System.out.println("hi");
    }
}
