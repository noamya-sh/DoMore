package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.model.EditMyVolAssModel;
import com.example.myapplication.dialogs.SearchDialog;
import com.example.myapplication.objects.Association;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;

public class EditMyVolAssActivity extends AppCompatActivity {
    EditMyVolAssModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vol);
        model = new EditMyVolAssModel(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        super.onStart();
        EditText title = findViewById(R.id.volunteering_details);
        Spinner city = findViewById(R.id.cities_spinner_for_publish);
        EditText num_vol = findViewById(R.id.Num_vol_required);
        TextView start = findViewById(R.id.addvol_start);
        TextView end = findViewById(R.id.addvol_end);
        EditText phone = findViewById(R.id.contact_Phone);
        Button edit = findViewById(R.id.publish_vol);
        Button from = findViewById(R.id.addvol_from);
        Button un = findViewById(R.id.addvol_un);

        edit.setText("שמור שינויים");
        Bundle extras = getIntent().getExtras();
        String id = extras.getString("id");
        title.setText(extras.getString("title"));
        setSpinText(city,extras.getString("city"));
        num_vol.setText(Integer.toString(extras.getInt("num_vol")));
        SimpleDateFormat dformat = new SimpleDateFormat("dd/MM/yyyy  HH:mm");
        start.setText(dformat.format(new Date(extras.getLong("from"))));
        end.setText(dformat.format(new Date(extras.getLong("un"))));
        phone.setText(Integer.toString(extras.getInt("phone")));

        Stack<Timestamp> sts1 = new Stack<>();
        sts1.push(new Timestamp(new Date(extras.getLong("from"))));
        Stack<Timestamp> sts2 = new Stack<>();
        sts2.push(new Timestamp(new Date(extras.getLong("un"))));
        SearchDialog.getDateTime(from, start, sts1);
        SearchDialog.getDateTime(un, end, sts2);

        edit.setOnClickListener(v -> {
            model.saveChanges(id,title.getText().toString(), city.getSelectedItem().toString(),
                    Integer.parseInt(num_vol.getText().toString()), sts1.pop().toDate(),
                    sts2.pop().toDate(), Integer.parseInt(num_vol.getText().toString()));
            Toast.makeText(EditMyVolAssActivity.this,"העריכה בוצעה בהצלחה",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(EditMyVolAssActivity.this, HomeAssoActivity.class));

        });

    }
    public void setSpinText(Spinner spin, String text) {
        for(int i = 0; i < spin.getAdapter().getCount(); i++)
            if(spin.getAdapter().getItem(i).toString().contains(text))
                spin.setSelection(i);
    }
}
