package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.model.HomeVoluModel;
import com.example.myapplication.nocomplete.MyVolVolunteer;
import com.google.firebase.firestore.DocumentSnapshot;


public class HomeVolunteerActivity extends AppCompatActivity {
    private HomeVoluModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vol_home);
        model = new HomeVoluModel(this);

        TextView welcome = findViewById(R.id.volunteerName);
        model.getName(task -> {
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
        srch.setOnClickListener(v ->
                startActivity(new Intent(HomeVolunteerActivity.this, VolunteeringListActivity.class)));
        Button my_vol = findViewById(R.id.vh_myvol);
        my_vol.setOnClickListener(v -> startActivity(new Intent(HomeVolunteerActivity.this, MyVolVolunteer.class)));
        ImageButton ib = findViewById(R.id.volhome_logout);
        //log out
        ib.setOnClickListener(v -> {
            model.signOut();
            Toast.makeText(HomeVolunteerActivity.this,"Logout successful",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeVolunteerActivity.this,MainActivity.class));
            finish();
        });
    }
}
