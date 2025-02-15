package com.example.myapplication.activitiy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.activitiy.dialogs.MyVolunteeringAssDetailsDialog;
import com.example.myapplication.databinding.ActivityMyVolVolBinding;
import com.example.myapplication.model.MyVolAssModel;
import com.example.myapplication.model.objects.Volunteering;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//list view of all volunteering of this association
public class MyVolAssociationActivity extends FatherAssociationMenuActivity {
    private ListView listView;
    private VolunteeringAdapter adapter;
    private MyVolAssModel model;
    AlertDialog loadingDialog;
    MyVolunteeringAssDetailsDialog mvd;
    ActivityMyVolVolBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyVolVolBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        allocateActivityTitle("ההתנדבויות שלי");
        model = new MyVolAssModel(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        listView = findViewById(R.id.myVolAss_ListView);
        // dialog "loading"
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_progress, null);
        builder.setView(dialogView);
        builder.setCancelable(false);
        loadingDialog = builder.create();

        this.listView.setOnItemClickListener((parent, view, position, id) -> {
            Volunteering v = (Volunteering) listView.getItemAtPosition(position);
            mvd = new MyVolunteeringAssDetailsDialog(v,this,model);
            mvd.show(getSupportFragmentManager(),"");
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadingDialog.dismiss();
    }

    public void showNotExistVolunteering() {
        Toast.makeText(MyVolAssociationActivity.this,
                "עדיין לא הוספת התנדבויות",Toast.LENGTH_SHORT).show();
    }
    public void dismissDialog(){
        this.loadingDialog.dismiss();
    }

    public void setData(ArrayList<Volunteering> volList) {
        adapter = new VolunteeringAdapter(this, volList);
        listView.setAdapter(adapter);
    }

    public void notifyAdapter() {
        adapter.notifyDataSetChanged();
    }

    public Intent passData(String id, String title, String city, int num_vol, Date from, Date un, String phone){
        Intent i = new Intent(MyVolAssociationActivity.this, EditMyVolAssActivity.class);
        i.putExtra("id",id);
        i.putExtra("title",title);
        i.putExtra("city",city);
        i.putExtra("num_vol",num_vol);
        i.putExtra("from",from.getTime());
        i.putExtra("un",un.getTime());
        i.putExtra("phone",phone);
        return i;
    }

    public void sendMail(List<String> emails) {
        Log.i("Send email", "");

        String[] TO = emails.toArray(new String[0]);
        String[] CC = model.getAssociationMailAddress();
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");

        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");
        emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            mvd.dismiss();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }


    public void ShowToastSendSMS() {
        Toast.makeText(this, "הSMS נשלח בהצלחה", Toast.LENGTH_SHORT).show();
    }
}