package com.example.e_quiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.*;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    String username;
    Button login;
    EditText u, p;
    TextView signup;
    private FirebaseAuth auth;
    String usr,pwd;
    List<String> data;
    private DatabaseReference mDatabase;
    ProgressDialog prog;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        u = (EditText) findViewById(R.id.usr);
        p = (EditText) findViewById(R.id.pwd);
        login = findViewById(R.id.btn_login);
        signup = findViewById(R.id.link_signup);
        FirebaseApp.initializeApp(Login.this);
        auth=FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             usr = u.getText().toString();
                pwd = p.getText().toString();
                if (usr.isEmpty())
                {
                    u.setError("Please Enter Email");
                }
                else if(pwd.isEmpty())
                {
                 p.setError("Please Enter Password");
                }
                else if(usr.equals("Admin") && pwd.equals("Admin"))
                {
                    Intent i = new Intent(getApplicationContext(), Admin_main.class);
                    startActivity(i);
                }
                else
                    {
                login();
                }
                /*if (usr.equals("Anwar") && pwd.equals("123")) {
                    Intent i = new Intent(getApplicationContext(), Main.class);
                    startActivity(i);
                } else if (usr.equals("Admin") && pwd.equals("123")) {

                    Intent i = new Intent(getApplicationContext(), Admin_main.class);
                    startActivity(i);
                }
                //  Intent i=new Intent(getApplicationContext(),Main.class);
                // startActivity(i);
            }*/
            }
        });

        //Signup Button

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), register.class);
                startActivity(i);
            }
        });

//        login();

    }


       public void Login()
            {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    // Name, email address etc
                    String name = user.getDisplayName();
                }
            }



    void getdata()
    {
       // mDatabase = FirebaseDatabase.getInstance().getReference("Users").;
// above statement point to base tree
        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(auth.getCurrentUser().getUid()).child("name");
     //   DatabaseReference data = mDatabase.child("Users").child(auth.getCurrentUser().getUid()).child("name");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

              username=snapshot.getValue(String.class);
                //Toast.makeText(getApplicationContext(),"user : "+username,Toast.LENGTH_LONG).show();
                Intent i=new Intent(getApplicationContext(),Main.class);
                i.putExtra("uname",username);
                startActivity(i);
               //prints "Do you have data? You'll love Firebase."
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"sdsadsad",Toast.LENGTH_LONG).show();
            }
        });


    }

    private void login()
    {
        prog=new ProgressDialog(this);
        prog.setMessage("Signing in\nPlease wait!");
        prog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        prog.setIndeterminate(true);
        prog.setProgress(0);
        prog.show();
        auth.signInWithEmailAndPassword(usr, pwd)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"Invalid", Toast.LENGTH_LONG).show();
                            prog.dismiss();
                        }
                         else {
                           // FirebaseUser user = auth.getCurrentUser();
                          //  txtUsername.setText("Welcome, " + user.getDisplayName() + "\n" + user.getEmail());
                            //Intent intent = new Intent(getApplicationContext(), Main.class);
                            //startActivity(intent);
                            //finish();
                            getdata();
                            prog.dismiss();
                        }
                    }
                });
    }

}
