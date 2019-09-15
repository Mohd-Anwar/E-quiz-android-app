package com.example.e_quiz;

import android.os.CountDownTimer;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class Data
{
    int qn;
    private DatabaseReference mDatabase;
    int  getcount(String sub)
    {
        mDatabase = FirebaseDatabase.getInstance().getReference("Setting").child(sub);
        //   DatabaseReference data = mDatabase.child("Users").child(auth.getCurrentUser().getUid()).child("name");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
               qn=(int)snapshot.getChildrenCount();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return qn;
    }
}
