package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Users extends Activity {

    public static void logOut(ImageButton ib, Context c,Activity a, FirebaseAuth auth){
        ib.setOnClickListener(v -> {
            auth.signOut();
            a.startActivity(new Intent(c,MainActivity.class));
            a.finish();
            Toast.makeText(c,"Logout successful",Toast.LENGTH_SHORT).show();
        });
    }
    public static void checkCollection(FirebaseAuth auth, FirebaseFirestore db, Context mContext) {
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        DocumentReference docIdRef = rootRef.collection("volunteers").document(user.getUid());
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        mContext.startActivity(new Intent(mContext, vol_home.class));
                    } else {
                        mContext.startActivity(new Intent(mContext, asso_home.class));
                    }
                } else {
                    System.out.println("failed");
                }
            }
        });
    }

}
