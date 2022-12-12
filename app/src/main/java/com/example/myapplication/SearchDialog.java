package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;

public class SearchDialog extends DialogFragment {
    DialogListener mListener;
    // Override onAttach to get a reference to the main class
    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (DialogListener) context;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Override onCreateView to inflate and return the view for the dialog
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for the dialog
        View view = inflater.inflate(R.layout.search_dialog, container, true);

//        // Get the dialog's search field and submit button
////        final EditText searchField = view.findViewById(R.id.search_field);
        Button searchButton = view.findViewById(R.id.search_dialogsearch);
        Button cancel = view.findViewById(R.id.cancel_dialogsearch);
        cancel.setOnClickListener(v -> dismiss());
        TextView from = view.findViewById(R.id.search_from);
        TextView un = view.findViewById(R.id.search_un);
        ArrayList<Timestamp> frts = new ArrayList<>();
        ArrayList<Timestamp> unts = new ArrayList<>();
//        TextView tv1= view.findViewById(R.id.search_from);
        Button calendarButtonFrom = view.findViewById(R.id.calendar_button);
        Button calendarButtonUn = view.findViewById(R.id.calendar_button1);
        Timestamp t_from = getDateTime(calendarButtonFrom,from);
        Timestamp t_un = getDateTime(calendarButtonUn,un);

//
//
//        // Set the submit button's onClick listener
//        searchButton.setOnClickListener(view1 -> {
//            Map<String,Object> query = new HashMap<>();
//            CheckBox ch1 = view1.findViewById(R.id.checkBox1);
//            CheckBox ch2 = view1.findViewById(R.id.checkBox2);
//            CheckBox ch3 = view1.findViewById(R.id.checkBox3);
//            CheckBox ch4 = view1.findViewById(R.id.checkBox4);
//            if (ch1.isChecked()){
//                Spinner sp = view1.findViewById(R.id.search_category);
//                String s = sp.getSelectedItem().toString();
//                query.put("category",s);
//            }
//            if (ch2.isChecked()){
//                EditText et = view1.findViewById(R.id.ass_name_dialogsearch);
//                String s = et.getText().toString();
//                query.put("association",s);
//            }
//            if (ch3.isChecked()){
//
//                calendarButton.setOnClickListener(v -> {
//                    // Open the calendar here
//                    Calendar calendar = Calendar.getInstance();
//                    int year = calendar.get(Calendar.YEAR);
//                    int month = calendar.get(Calendar.MONTH);
//                    int day = calendar.get(Calendar.DAY_OF_MONTH);
//                    int hour = calendar.get(java.util.Calendar.HOUR);
//                    int minute = calendar.get(java.util.Calendar.MINUTE);
//                    DatePicker datePicker = new DatePicker(v.getContext());
//                    datePicker.init(year, month, day, null);
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
//                    builder.setView(datePicker);
//                    builder.setPositiveButton("OK", (dialog, which) -> {
//                        // Get the selected date from the date picker
//                        int year1 = datePicker.getYear();
//                        int month1 = datePicker.getMonth();
//                        int day1 = datePicker.getDayOfMonth();
//
//                        // Create a time picker and set the selected hour and minute
//                        TimePicker timePicker = new TimePicker(v.getContext());
//                        timePicker.setHour(hour);
//                        timePicker.setMinute(minute);
//
//                        AlertDialog.Builder timePickerBuilder = new AlertDialog.Builder(v.getContext());
//                        timePickerBuilder.setView(timePicker);
//                        timePickerBuilder.setPositiveButton("OK", (dialog1, which1) -> {
//                            // Get the selected time from the time picker
//                            int hour1 = timePicker.getHour();
//                            int minute1 = timePicker.getMinute();
//                            Calendar calendar1 = new GregorianCalendar(year1, month1, day1, hour1, minute1);
//                            Timestamp t = new Timestamp(calendar1.getTime());
//                            query.put("from",t);
//                        });
//                        // Show the time picker dialog
//                        timePickerBuilder.show();
//                    });
//                    // Show the date picker dialog
//                    builder.show();
//                });
//            }
//            // Get the entered text and pass it back to the main class
////                String inputText = searchField.getText().toString();
////                mListener.onFinishDialog(inputText);
//            dismiss();
//        });

        return view;
    }
    private Timestamp getDateTime(Button calendarButton,TextView tv){
        Timestamp t = new Timestamp(new Date());
        Stack<Timestamp> st = new Stack<>();
        st.push(t);
        calendarButton.setOnClickListener(v -> {
            // Open the calendar here
            Calendar calendar = Calendar.getInstance();
            DatePicker datePicker = new DatePicker(v.getContext());
            datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH), null);
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setView(datePicker);
            builder.setPositiveButton("OK", (dialog, which) -> {
                // Create a time picker and set the selected hour and minute
                TimePicker timePicker = new TimePicker(v.getContext());
                timePicker.setHour(calendar.get(java.util.Calendar.HOUR));
                timePicker.setMinute(calendar.get(java.util.Calendar.MINUTE));

                AlertDialog.Builder timePickerBuilder = new AlertDialog.Builder(v.getContext());
                timePickerBuilder.setView(timePicker);
                timePickerBuilder.setPositiveButton("OK", (dialog1, which1) -> {
                    // Get the selected time from the time picker
                    Calendar calendar1 = new GregorianCalendar(datePicker.getYear(),
                            datePicker.getMonth(), datePicker.getDayOfMonth(),
                            timePicker.getHour(), timePicker.getMinute());
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy  hh:mm");
                    tv.setText(formatter.format(calendar1.getTime()));
                    st.push(new Timestamp(calendar1.getTime()));
                });
                // Show the time picker dialog
                timePickerBuilder.show();
            });
            // Show the date picker dialog
            builder.show();
        });
        return st.pop();
    }
}
