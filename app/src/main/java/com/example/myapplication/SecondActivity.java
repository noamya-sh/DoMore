package com.example.myapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;

public class SecondActivity extends AppCompatActivity {
    public ArrayList<volunteering> volList = new ArrayList<>();

    private ListView listView;
    private VolunteeringAdapter adapter;
    private Button search;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        listView = findViewById(R.id.shapesListView);
        adapter = new VolunteeringAdapter(this, volList);
        listView.setAdapter(adapter);
        Spinner temp = findViewById(R.id.search_ass_name);

        setupData();
        listView.setOnItemClickListener((parent, view, position, id) -> System.out.println("yess"));
        Button search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner cat = findViewById(R.id.search_category);
                String s = cat.getSelectedItem().toString();
                ArrayList<volunteering> new_volList = new ArrayList<>();

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

    private void setupData() {
        db = FirebaseFirestore.getInstance();
        Date d = new Date();
        db.collection("volunteering").whereGreaterThan("start",d).get()
                .addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if (document.exists()){
                        init_vol(document);
                    }
                }
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
    }
    public void init_vol(QueryDocumentSnapshot document){
        ArrayList<volunteering> temp = new ArrayList<>();
        DocumentReference f = document.getDocumentReference("association");
        DocumentReference dd = db.document(f.getPath());
        dd.get().addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()){
                DocumentSnapshot document2 = task1.getResult();
                String association = document2.getString("name");
                System.out.println(association);
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
}
