package com.example.e_quiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class UserResult extends AppCompatActivity {

    TextView crct,wrng,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userresult);
        setTitle("Your Result");
        crct=(TextView)findViewById(R.id.crrct);
        wrng=(TextView)findViewById(R.id.wrong);
        back=(TextView)findViewById(R.id.back);
        savedInstanceState=getIntent().getExtras();
        if(savedInstanceState!=null)
        {
            crct.setText("Correct : "+savedInstanceState.getString("Correct"));
            wrng.setText("Wrong : "+savedInstanceState.getString("Wrong"));

        }
        else{
            Toast.makeText(getApplicationContext(),"not getting",Toast.LENGTH_LONG).show();}

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(),Main.class));
                }
            });
    }



    @Override
    public void onBackPressed() {
        //Toast.makeText(getApplicationContext(),"Nothing Should Happen",Toast.LENGTH_LONG).show();
        //backButtonHandler();
        //super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),Main.class));
    }
}
