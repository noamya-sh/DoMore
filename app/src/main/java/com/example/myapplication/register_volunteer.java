package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class register_volunteer extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_volunteer);

        Spinner cit = (Spinner) findViewById(R.id.cities_spinner);
        cit.setPrompt("Select your city!");

    }
}
