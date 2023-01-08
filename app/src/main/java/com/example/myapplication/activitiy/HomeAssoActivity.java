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
import com.example.myapplication.model.HomeAssModel;
import com.example.myapplication.R;
import com.google.firebase.firestore.DocumentSnapshot;

public class HomeAssoActivity extends AppCompatActivity {
    private HomeAssModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asso_home);
        model = new HomeAssModel(this);

        TextView welcome = findViewById(R.id.welcomAssociationName);
        model.getName(task -> {
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
        pub_vol.setOnClickListener(v ->
                startActivity(new Intent(HomeAssoActivity.this, AddVolunteeringActivity.class)));
        Button my_vol = findViewById(R.id.ah_myvol);
        my_vol.setOnClickListener(v -> startActivity(new Intent(HomeAssoActivity.this, MyVolAssociationActivity.class)));
        Button editDetail = findViewById(R.id.editde);
        editDetail.setOnClickListener(v -> startActivity(new Intent(HomeAssoActivity.this, EditMyDetailsAssActivity.class)));
        ImageButton ib = findViewById(R.id.assohome_logout);
        ib.setOnClickListener(v -> {
            model.signOut();
            Toast.makeText(HomeAssoActivity.this,"התנתקת בהצלחה",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeAssoActivity.this,MainActivity.class));
            finish();
        });
    }
}
