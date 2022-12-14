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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

        Button searchButton = view.findViewById(R.id.search_dialogsearch);
        Button cancel = view.findViewById(R.id.cancel_dialogsearch);
        cancel.setOnClickListener(v -> dismiss());
        TextView from = view.findViewById(R.id.search_from);
        TextView un = view.findViewById(R.id.search_un);
        Button calendarButtonFrom = view.findViewById(R.id.calendar_button);
        Button calendarButtonUn = view.findViewById(R.id.calendar_button1);
        Stack<Timestamp> sts1 = new Stack<>();
        Stack<Timestamp> sts2 = new Stack<>();
        Timestamp now = new Timestamp(new Date());
        sts1.push(now);
        sts2.push(now);
        getDateTime(calendarButtonFrom,from,sts1);
        getDateTime(calendarButtonUn,un,sts2);
        CheckBox ch1 = view.findViewById(R.id.checkBox1);
        CheckBox ch2 = view.findViewById(R.id.checkBox2);
        CheckBox ch3 = view.findViewById(R.id.checkBox3);
        CheckBox ch4 = view.findViewById(R.id.checkBox4);
        EditText et = view.findViewById(R.id.ass_name_dialogsearch);
        Spinner sp = view.findViewById(R.id.search_category);
        // Set the submit button's onClick listener
        searchButton.setOnClickListener(view1 -> {
            Map<String,Object> query = new HashMap<>();
            if (ch1.isChecked()){
                String s = sp.getSelectedItem().toString();
                if (!s.equals("בחר קטגוריה"))
                    query.put("category",s);
            }
            if (ch2.isChecked()){
                String s = et.getText().toString();
                if (!s.equals(""))
                    query.put("association",s);
            }
            String s = getResources().getString(R.string.null_date);
            if (ch3.isChecked()){
                if (!from.getText().toString().equals(s))
                    query.put("from",sts1.pop());
            }
            if (ch4.isChecked()){
                if (!un.getText().toString().equals(s))
                    query.put("un",sts1.pop());
            }

            // Get the entered text and pass it back to the main class
//                String inputText = searchField.getText().toString();
            mListener.onFinishDialog(query);
            dismiss();
        });

        return view;
    }
    public static void getDateTime(Button calendarButton,TextView tv,Stack<Timestamp> ts){

        calendarButton.setOnClickListener(v -> {
            // Open the calendar here
            Calendar calendar = Calendar.getInstance();
            DatePicker datePicker = new DatePicker(v.getContext());
            datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH), null);
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setView(datePicker);
            builder.setPositiveButton("בחר", (dialog, which) -> {
                // Create a time picker and set the selected hour and minute
                TimePicker timePicker = new TimePicker(v.getContext());
                timePicker.setHour(calendar.get(java.util.Calendar.HOUR));
                timePicker.setMinute(calendar.get(java.util.Calendar.MINUTE));

                AlertDialog.Builder timePickerBuilder = new AlertDialog.Builder(v.getContext());
                timePickerBuilder.setView(timePicker);
                timePickerBuilder.setPositiveButton("בחר", (dialog1, which1) -> {
                    // Get the selected time from the time picker
                    Calendar calendar1 = new GregorianCalendar(datePicker.getYear(),
                            datePicker.getMonth(), datePicker.getDayOfMonth(),
                            timePicker.getHour(), timePicker.getMinute());
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy  hh:mm");
                    tv.setText(formatter.format(calendar1.getTime()));
                    ts.push(new Timestamp(calendar1.getTime()));
                });
                // Show the time picker dialog
                timePickerBuilder.show();
            });
            // Show the date picker dialog
            builder.show();
        });
//        return st.pop();
    }
}
