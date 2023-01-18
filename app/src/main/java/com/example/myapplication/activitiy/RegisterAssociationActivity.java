package com.example.myapplication.activitiy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.myapplication.model.RegisterAssModel;
import com.example.myapplication.R;

//register screen of association
public class RegisterAssociationActivity extends Activity {
    private RegisterAssModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_association);
        model = new RegisterAssModel(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EditText ver_pass = findViewById(R.id.editTextTextPassword2);
        ver_pass.setVisibility(View.GONE);
        Button register = findViewById(R.id.acc);
        register.setOnClickListener(view -> {
            EditText associationName = findViewById(R.id.editTextAsssociationName);
            EditText email = findViewById(R.id.editTextTextEmailAddress);
            EditText password = findViewById(R.id.editTextTextPassword);
            EditText phone = findViewById(R.id.editTextPhone);
            Spinner category = findViewById(R.id.planets_spinner);
            model.registerNewAssociation(email.getText().toString(),password.getText().toString(),
                    associationName.getText().toString(),category.getSelectedItem().toString(),
                    phone.getText().toString());
        });
    }

    public void goHomeAssociation() {
        startActivity(new Intent(RegisterAssociationActivity.this,HomeAssoActivity.class));
    }

    public void registerFailed() {
        Toast.makeText(RegisterAssociationActivity.this, "Registration failed!", Toast.LENGTH_LONG).show();
    }
}
