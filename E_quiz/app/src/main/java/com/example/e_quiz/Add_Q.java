package com.example.e_quiz;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;


public class Add_Q extends Fragment {

    String sub,strq,stra,strb,strc,strd,strans="";
    Button btn;
    EditText q,a,b,c,d,ans;
    Spinner spinner;
    String[] arr={"Select Correct Option","A","B","C","D"};
    ArrayAdapter<String> adapter1;
    public Add_Q() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)

    {
        Bundle b= getActivity().getIntent().getExtras();
        sub=b.getString("sub");
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frg_add__q, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        spinner=(Spinner)view.findViewById(R.id.spinner1);
        adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,arr)
        {
            @Override
            public boolean isEnabled(int position)
            {
                if(position == 0)
                { return false;
                }
                else
                { return true; } }};

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

               if(position!=0)
               {
                   strans=parent.getItemAtPosition(position).toString();
                  // Toast.makeText(getActivity(),"Selected "+strans,Toast.LENGTH_LONG).show();
                   }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });



        spinner.setAdapter(adapter1);
        q= (EditText)view.findViewById(R.id.q);
        a=(EditText)view.findViewById(R.id.a);
        b=(EditText)view.findViewById(R.id.b);
        c=(EditText)view.findViewById(R.id.c);
        d=(EditText)view.findViewById(R.id.d);
      //  ans=(EditText)view.findViewById(R.id.ans);
        btn=(Button)view.findViewById(R.id.btnadd);
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                strq=q.getText().toString();
                stra=a.getText().toString();
                strb=b.getText().toString();
                strc=c.getText().toString();
                strd=d.getText().toString();
                    if(!strans.equals(""))
                    {
                        Toast.makeText(getActivity(),"item selected "+strq,Toast.LENGTH_LONG).show();

                    // strans=ans.getText().toString();
              User user=new User(strq,stra,strb,strc,strd,strans);
                FirebaseDatabase.getInstance().getReference(sub).push().setValue(user);
                Toast.makeText(getActivity(),"Added",Toast.LENGTH_LONG).show();
                clr();
                    }
                    else {
                        Toast.makeText(getActivity(),"please select answer "+strq,Toast.LENGTH_LONG).show();
                    }
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    void clr()
    {
        strans="";
        q.setText(null);
        a.setText(null);
        b.setText(null);
        c.setText(null);
        d.setText(null);
        spinner.setAdapter(adapter1);
    }
}
