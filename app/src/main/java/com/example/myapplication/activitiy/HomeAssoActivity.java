package com.example.myapplication.activitiy;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityAssoHomeBinding;
import com.example.myapplication.model.HomeAssModel;
import com.example.myapplication.R;
import com.google.firebase.firestore.DocumentSnapshot;

//home page of association
public class HomeAssoActivity extends FatherAssociationMenuActivity {
    ActivityAssoHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAssoHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        allocateActivityTitle("בית");
        TextView welcome = findViewById(R.id.welcomAssociationName);
        FatherModel.getName(task -> {
            if (task.isSuccessful()){
                DocumentSnapshot document = task.getResult();
                if (document != null) {
                    String k = "שלום עמותת " + document.getString("name");
                    welcome.setText(k);
                } else {
                    Log.d("LOGGER", "No such document");
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Button pub_vol = findViewById(R.id.addvo);
        pub_vol.setOnClickListener(v -> goAddVol());
        Button my_vol = findViewById(R.id.ah_myvol);
        my_vol.setOnClickListener(v -> goMyVol());
        Button editDetail = findViewById(R.id.editde);
        editDetail.setOnClickListener(v -> goEditDetails());
    }
}
