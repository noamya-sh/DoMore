package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class Users {

//    public static void logOut(@NonNull ImageButton ib, Context c, Activity a){
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        ib.setOnClickListener(v -> {
//            auth.signOut();
//            a.startActivity(new Intent(c,MainActivity.class));
//            a.finish();
//            Toast.makeText(c,"Logout successful",Toast.LENGTH_SHORT).show();
//        });
//    }
//    public static void checkUserType(Context mContext) {
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        FirebaseUser user = auth.getCurrentUser();
//        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
//        DocumentReference docIdRef = rootRef.collection("volunteers").document(user.getUid());
//        docIdRef.get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                DocumentSnapshot document = task.getResult();
//                if (document.exists()) {
//                    mContext.startActivity(new Intent(mContext, HomeVolunteerActivity.class));
//                } else {
//                    mContext.startActivity(new Intent(mContext, HomeAssoActivity.class));
//                }
//            } else {
//                System.out.println("failed");
//            }
//        });
//    }
//    public static void register_emailAndPassowrd(Context mcontext, @NonNull Map<String,Object> m, String collection){
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        String email = (String) m.get("email");
//        String password = (String) m.get("password");
//        if (email != null && password != null){
//        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((Activity) mcontext, task -> {
//            if (task.isSuccessful()) {
//                Toast.makeText(mcontext, "Registration succeeded!", Toast.LENGTH_LONG).show();
//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                if (user != null) {
//                    m.put("uid", user.getUid());
//                    db.collection(collection).document(user.getUid()).set(m);
//                    Users.checkUserType(mcontext);
//                } else {
//                    // No user is signed in
//                    Toast.makeText(mcontext, "Connection Error", Toast.LENGTH_LONG).show();
//                }
//            } else
//                Toast.makeText(mcontext, "Registration failed!", Toast.LENGTH_LONG).show();
//        });
//        }
//    }
}
