package com.example.e_quiz;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class set_noq extends Fragment implements View.OnClickListener{

    String[] list;
    String sub;
    String itm;
    Setting set=new Setting();
    private DatabaseReference mDatabase,atabase;
    ProgressDialog prog;
   Button change,settime,setnoq,changnoq;
   TextView txttime,txtnoq;
   EditText etxttime,etxtnoq;
    long time,noq,nq;
    Data data=new Data();
    public set_noq() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        Bundle b= getActivity().getIntent().getExtras();
      sub=b.getString("sub");
      //nq=data.getcount(sub);
      //  Toast.makeText(getContext(),"Qount"+nq,Toast.LENGTH_LONG).show();
        prog=new ProgressDialog(getActivity());
        prog.setMessage("Please Wait!!");
        prog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        prog.setIndeterminate(true);
        prog.setProgress(0);
        prog.show();
        get();

        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.frg_set_noq, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("");

        txttime=(TextView)view.findViewById(R.id.tvtime);
        etxttime=(EditText)view.findViewById(R.id.Etxttime);
        change=(Button)view.findViewById(R.id.chnge);change.setOnClickListener(this);
        settime=(Button)view.findViewById(R.id.settime);settime.setOnClickListener(this);

        changnoq=(Button)view.findViewById(R.id.changnoq);changnoq.setOnClickListener(this);
        txtnoq=(TextView)view.findViewById(R.id.tvnoq);
        etxtnoq=(EditText)view.findViewById(R.id.etxtnoq);
        setnoq=(Button)view.findViewById(R.id.setnoq);setnoq.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        InputMethodManager imm= (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        switch (v.getId()) {

            case R.id.chnge:

                if(txttime.getVisibility()==View.VISIBLE)
                {
                    setnoq.setVisibility(View.INVISIBLE);
                    changnoq.setVisibility(View.VISIBLE);
                    change.setVisibility(View.INVISIBLE);
                    settime.setVisibility(View.VISIBLE);
                    txttime.setVisibility(View.INVISIBLE);
                    etxttime.setVisibility(View.VISIBLE);

                    imm.showSoftInput(etxttime, InputMethodManager.SHOW_IMPLICIT);
                }
                else {
                    txttime.setVisibility(View.VISIBLE);
                    etxttime.setVisibility(View.INVISIBLE);
                }
                break;
            case  R.id.settime:
                String t=etxttime.getText().toString();
                if(t.isEmpty())
                {
                    Toast.makeText(getActivity(),"Can not be Empty",Toast.LENGTH_LONG).show();
                    settime.setVisibility(View.INVISIBLE);
                    change.setVisibility(View.VISIBLE);
                    etxttime.setVisibility(View.INVISIBLE);
                    txttime.setVisibility(View.VISIBLE);

                }

                else{updateTime();
                    settime.setVisibility(View.INVISIBLE);
                    change.setVisibility(View.VISIBLE);
                    etxttime.setVisibility(View.INVISIBLE);
                    txttime.setVisibility(View.VISIBLE);}

                break;
            case R.id.changnoq:


                if(txtnoq.getVisibility()==View.VISIBLE)
                {
                    //settime.setVisibility(View.INVISIBLE);
                    //change.setVisibility(View.VISIBLE);
                    changnoq.setVisibility(View.INVISIBLE);
                    setnoq.setVisibility(View.VISIBLE);
                    txtnoq.setVisibility(View.INVISIBLE);
                    etxtnoq.setVisibility(View.VISIBLE);
                    imm.showSoftInput(etxtnoq, InputMethodManager.SHOW_IMPLICIT);
                }
                else {
                    txttime.setVisibility(View.VISIBLE);
                    etxttime.setVisibility(View.INVISIBLE);
                }
                break;

            case R.id.setnoq:
                String no=etxtnoq.getText().toString();
                if(no.isEmpty())
                {
                    Toast.makeText(getActivity(),"Can not be Empty",Toast.LENGTH_LONG).show();
                    setnoq.setVisibility(View.INVISIBLE);
                    changnoq.setVisibility(View.VISIBLE);
                    etxtnoq.setVisibility(View.INVISIBLE);
                    txtnoq.setVisibility(View.VISIBLE);
                }
                else if(Integer.parseInt(no) > nq)
                {

                   etxtnoq.setError("There is only "+nq+" Questions Available ");
                }
                else
                    {
                updatenoq();
                setnoq.setVisibility(View.INVISIBLE);
                changnoq.setVisibility(View.VISIBLE);
                etxtnoq.setVisibility(View.INVISIBLE);
                txtnoq.setVisibility(View.VISIBLE);}
                break;

        }
    }

    public  void get()
    {
        mDatabase = FirebaseDatabase.getInstance().getReference("Setting").child(sub);
        //   DatabaseReference data = mDatabase.child("Users").child(auth.getCurrentUser().getUid()).child("name");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for(DataSnapshot data:snapshot.getChildren())
                {
                    prog.dismiss();
                    time=Long.parseLong(snapshot.child("time").getValue().toString());
                    txttime.setText("Time : "+(time/60000)+" min");
                    noq=Long.parseLong(snapshot.child("noq").getValue().toString());
                    txtnoq.setText("Count: "+noq);
                }
        getcout();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(),"sdsadsad",Toast.LENGTH_LONG).show();
            }
        });
    }

    void getcout()
    {
        atabase = FirebaseDatabase.getInstance().getReference(sub);
        atabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nq=dataSnapshot.getChildrenCount();
                etxtnoq.setHint("Enter Value less than "+(nq+1));
                //Toast.makeText(getActivity(),"noq "+nq,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void updateTime()
    {
        mDatabase = FirebaseDatabase.getInstance().getReference("Setting").child(sub);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                time=Long.parseLong(etxttime.getText().toString());
                dataSnapshot.getRef().child("time").setValue(time*60000);
               // prog.dismiss();
                Toast.makeText(getActivity(),"Time Updated",Toast.LENGTH_LONG).show();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
               Toast.makeText(getActivity(),"Error",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void updatenoq()
    {
        mDatabase = FirebaseDatabase.getInstance().getReference("Setting").child(sub);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                noq=Long.parseLong(etxtnoq.getText().toString());
                dataSnapshot.getRef().child("noq").setValue(noq);
                // prog.dismiss();
                Toast.makeText(getActivity(),"NOQ Updated",Toast.LENGTH_LONG).show();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(),"Error",Toast.LENGTH_LONG).show();
            }
        });
    }


}


