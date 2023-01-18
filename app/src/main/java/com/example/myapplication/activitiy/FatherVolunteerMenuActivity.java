package com.example.myapplication.activitiy;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myapplication.R;
import com.example.myapplication.model.HomeVoluModel;
import com.google.android.material.navigation.NavigationView;

//for volunteer menu
public class FatherVolunteerMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    protected HomeVoluModel FatherModel = new HomeVoluModel();
    protected DrawerLayout dl;

    @Override
    public void setContentView(View view) {

        dl = (DrawerLayout) getLayoutInflater().inflate(R.layout.drawer_volunteer,null);
        FrameLayout container = dl.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(dl);

        Toolbar toolbar = dl.findViewById(R.id.toolBar1);
        setSupportActionBar(toolbar);
        NavigationView nv = findViewById(R.id.volhome_nv);
        nv.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle ab = new ActionBarDrawerToggle(this,dl,toolbar,R.string.drawer_open,R.string.drawer_close);
        ab.getDrawerArrowDrawable().setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);
        dl.addDrawerListener(ab);
        ab.syncState();
    }
    protected void allocateActivityTitle(String s){
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(s);
        }
    }


    protected void goSearch(){
        startActivity(new Intent(this, VolunteeringListActivity.class));
    }
    protected void goMyVol(){
        startActivity(new Intent(this, MyVolVolunteerActivity.class));
    }
    protected void goEditDetails(){
        startActivity(new Intent(this,EditMyDetailsVolActivity.class));
    }
    protected void goLogOut(){
        FatherModel.signOut();
        Toast.makeText(this,"התנתקת בהצלחה",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
    protected void goHome(){
        startActivity(new Intent(this,HomeVolunteerActivity.class));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mvh_search:
                goSearch();
                break;
            case R.id.mvh_acde:
                goEditDetails();
                break;
            case R.id.mvh_myvol:
                goMyVol();
                break;
            case R.id.mvh_logout:
                goLogOut();
                break;
            case R.id.mvh_return:
                finish();
                break;
            case R.id.mvh_home:
                goHome();
                break;
        }
        dl.closeDrawer(GravityCompat.START);
        return true;
    }
}
