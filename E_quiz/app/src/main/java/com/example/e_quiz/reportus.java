package com.example.e_quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class reportus extends AppCompatActivity {


    EditText editText;
    ImageButton btn;
    User user=new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportus);
        setTitle("Report Us");
        if(getSupportActionBar()!=null)
        {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        btn=(ImageButton)findViewById(R.id.sndreport);
        editText=(EditText)findViewById(R.id.txtreport);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=editText.getText().toString();
                user=new User(msg, FirebaseAuth.getInstance().getUid());
                FirebaseDatabase.getInstance().getReference("Reports").push().setValue(user);
            }
        });

    }
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
