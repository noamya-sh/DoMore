package com.example.myapplication.activitiy;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import com.example.myapplication.databinding.ActivityVolHomeBinding;
import com.example.myapplication.R;
import com.google.firebase.firestore.DocumentSnapshot;


public class HomeVolunteerActivity extends FatherVolunteerMenuActivity {
    ActivityVolHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVolHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        allocateActivityTitle("בית");

        TextView welcome = findViewById(R.id.volunteerName);
        FatherModel.getName(task -> {
            if (task.isSuccessful()){
                DocumentSnapshot document = task.getResult();
                if (document != null) {
                    String k = "ברוך הבא " + document.getString("name");
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
        //get list of volunteering
        Button srch = findViewById(R.id.searvo);
        srch.setOnClickListener(v -> goSearch());
        Button my_vol = findViewById(R.id.vh_myvol);
        my_vol.setOnClickListener(v -> goMyVol());
        Button edit_details = findViewById(R.id.editde);
        edit_details.setOnClickListener(v -> goEditDetails());

    }
}
