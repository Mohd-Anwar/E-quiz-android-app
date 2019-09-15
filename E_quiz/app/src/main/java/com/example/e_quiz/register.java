package com.example.e_quiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import  com.google.firebase.database.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {

    Button creat;
    TextView log;
    EditText usr,pas,name;
    private  FirebaseAuth auth;
    private  String nam,email,pwd;
    //private FirebaseAnalytics mFirebaseAnalytics;
   private FirebaseFirestore db ;
    ProgressDialog prog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        FirebaseApp.initializeApp(register.this);
        auth = FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();

        creat=(Button)findViewById(R.id.btn_signup);
        usr=findViewById(R.id.email);
        name=findViewById(R.id.usr);
        pas=findViewById(R.id.input_password);
        log=findViewById(R.id.link_login);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Login.class);
                startActivity(i);
            }
        });

        if(getSupportActionBar()!=null)
        {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        creat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createuser();
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Login.class);
                startActivity(i);
                finish();
            }
        });


   }



   protected void firestor()
   {

       Map<String, Object> users = new HashMap<>();
       users.put("first", "Ada");
       users.put("last", "Lovelace");
       users.put("born", 1815);
       users.put("asd","daasd");

       db.collection("Users")
               .document("user").set(users).addOnSuccessListener(new OnSuccessListener < Void > () {
           @Override
           public void onSuccess(Void aVoid) {
               Toast.makeText(register.this, "User Registered",
                       Toast.LENGTH_SHORT).show();
           }
       })
               .addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Toast.makeText(register.this, "ERROR" + e.toString(),
                               Toast.LENGTH_SHORT).show();
                   }
               });


   }




    protected  void createuser()
    {
        creat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                email = usr.getText().toString();
                pwd = pas.getText().toString();
                nam = name.getText().toString();
                if (pwd.length() < 6) {
                    pas.setError("Password must be 6 character long");
                }else {
                    prog=new ProgressDialog(register.this);
                    prog.setMessage("\nplease wait!");
                    prog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    prog.setIndeterminate(true);
                    prog.setProgress(0);
                    prog.show();

                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Admin");
                    auth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(register.this,new OnCompleteListener<AuthResult>() {
                        public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()) {

                                User user=new User(nam,email,pwd);
                                Toast.makeText(getApplicationContext(), "Successfully Register", Toast.LENGTH_LONG).show();
                             prog.dismiss();
                                FirebaseDatabase.getInstance().getReference("Users").child(auth.getInstance().getUid()).setValue(user);
                                startActivity(new Intent(getApplicationContext(),
                                        Login.class));

                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_LONG).show();
                                prog.dismiss();
                            }
                        }

                    });

                }
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
        {
            finish();
    }
        return super.onOptionsItemSelected(item);
    }
}
