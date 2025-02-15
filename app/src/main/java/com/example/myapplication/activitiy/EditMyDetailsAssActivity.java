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

import com.example.myapplication.databinding.ActivityRegisterAssociationBinding;
import com.example.myapplication.model.EditMyDetailsAssModel;
import com.example.myapplication.R;

//To edit association user details.
public class EditMyDetailsAssActivity extends FatherAssociationMenuActivity {
    EditMyDetailsAssModel model;
    EditText name;
    Spinner category;
    EditText email;
    EditText phone;
    EditText password;
    EditText ver_pass;
    ActivityRegisterAssociationBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterAssociationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        allocateActivityTitle("עריכת פרטי חשבון");
        name = findViewById(R.id.editTextAsssociationName);
        category = findViewById(R.id.planets_spinner);
        email = findViewById(R.id.editTextTextEmailAddress);
        phone = findViewById(R.id.editTextPhone);
        password = findViewById(R.id.editTextTextPassword);
        ver_pass = findViewById(R.id.editTextTextPassword2);
        model = new EditMyDetailsAssModel(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Button save = findViewById(R.id.acc);
        save.setText("שמור");
        save.setOnClickListener(v -> {
            boolean flag = model.getChanges(name.getText().toString(),category.getSelectedItem().toString(),
                    phone.getText().toString(), password.getText().toString(),
                    ver_pass.getText().toString());
            if (flag){
                Toast.makeText(EditMyDetailsAssActivity.this, "הנתונים נשמרו בהצלחה", Toast.LENGTH_LONG).show();
                startActivity(new Intent(EditMyDetailsAssActivity.this,HomeAssoActivity.class));
            }
            else
                Toast.makeText(EditMyDetailsAssActivity.this, "הסיסמה אינה תואמת", Toast.LENGTH_LONG).show();


        });
    }

    @SuppressLint("SetTextI18n")
    public void setDetails(String name, String category, String email, String phone) {
        this.name.setText(name);
        setSpinText(this.category,category);
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
