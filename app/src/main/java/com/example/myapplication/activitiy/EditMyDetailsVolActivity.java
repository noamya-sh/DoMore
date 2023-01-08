package com.example.myapplication.activitiy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityRegisterVolunteerBinding;
import com.example.myapplication.model.EditMyDetailsVolModel;
import com.example.myapplication.R;

public class EditMyDetailsVolActivity extends FatherVolunteerMenuActivity {
    ActivityRegisterVolunteerBinding binding;
    private EditMyDetailsVolModel model;
    EditText name;
    Spinner city;
    EditText email;
    EditText phone;
    EditText password;
    EditText ver_pass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterVolunteerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        allocateActivityTitle("עריכת פרטי חשבון");
        name = findViewById(R.id.volunteerName);
        city = findViewById(R.id.cities_spinner);
        email = findViewById(R.id.volEmailAddress);
        phone = findViewById(R.id.volPhone);
        password = findViewById(R.id.volPassword);
        ver_pass = findViewById(R.id.volPassword2);
        model = new EditMyDetailsVolModel(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Button save = findViewById(R.id.regvol);
        save.setText("שמור");
        save.setOnClickListener(v -> {
            boolean flag = model.getChanges(name.getText().toString(),city.getSelectedItem().toString(),
                    phone.getText().toString(), password.getText().toString(),
                    ver_pass.getText().toString());
            if (flag){
                Toast.makeText(EditMyDetailsVolActivity.this, "הנתונים נשמרו בהצלחה", Toast.LENGTH_LONG).show();
                startActivity(new Intent(EditMyDetailsVolActivity.this,HomeVolunteerActivity.class));
            }
            else
                Toast.makeText(EditMyDetailsVolActivity.this, "הסיסמה אינה תואמת", Toast.LENGTH_LONG).show();


        });
    }

    @SuppressLint("SetTextI18n")
    public void setDetails(String name, String category, String email, String phone) {
        this.name.setText(name);
        setSpinText(this.city,category);
        this.email.setText(email);
        this.email.setFocusable(false);
        this.phone.setText(phone);
        this.password.setHint("סיסמה חדשה");
    }
    private void setSpinText(Spinner spin, String text) {
        for(int i = 0; i < spin.getAdapter().getCount(); i++)
            if(spin.getAdapter().getItem(i).toString().contains(text))
                spin.setSelection(i);
    }

}
