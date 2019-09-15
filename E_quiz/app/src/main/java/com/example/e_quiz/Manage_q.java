package com.example.e_quiz;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Manage_q extends Fragment {

    String sub;
    User user;
    ListView lv;
    Button delete;
    private DatabaseReference mDatabase,keys;
    String arruid[];
     int key;
    ArrayList<String> q;
         int qn=1;
    ArrayList<String> listKeys ;
    ArrayAdapter<String> adapter;
    RadioButton rb;
    public Manage_q()
    {

    }

    @Override
    public void onCreate(@NonNull Bundle savedInstanceState) {
        Bundle b= getActivity().getIntent().getExtras();
        sub=b.getString("sub");

        //Toast.makeText(getActivity(),"dubject "+sub,Toast.LENGTH_LONG).show();
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_manage_questions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        lv=(ListView)view.findViewById(R.id.listv);
        delete=(Button)view.findViewById(R.id.deleteq);
        q = new ArrayList<String>();
        listKeys= new ArrayList<String>();

        mDatabase=FirebaseDatabase.getInstance().getReference();
        keys = FirebaseDatabase.getInstance().getReference();
      // whatever = new CustomListAdapter(getActivity(), q, a, b);
        adapter = new ArrayAdapter<String>( getActivity(), android.R.layout.simple_list_item_single_choice, q){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                /// Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

                // Set the text size 25 dip for ListView each item
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,25);

                // Return the view
                return view;
            }
        };
        lv.setAdapter(adapter);
       lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
           //     Toast.makeText(getActivity(),""+listKeys.get(position),Toast.LENGTH_LONG).show();
                key=position;
                delete.setEnabled(true);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                lv.setItemChecked(key, false);
              FirebaseDatabase.getInstance().getReference(sub).child(listKeys.get(key)).removeValue();
              //  Toast.makeText(getActivity(),""+listKeys.get(key),Toast.LENGTH_LONG).show();
                delete.setEnabled(false);
            }
        });


        addChildEventListener();

        super.onViewCreated(view, savedInstanceState);
    }


    private void addChildEventListener()
    {keys = FirebaseDatabase.getInstance().getReference(sub);
        // mDatabase = FirebaseDatabase.getInstance().getReference(sub);
        keys.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                listKeys.add(dataSnapshot.getKey());
                getQuestions(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot)
            {
             //   listKeys.clear();
                //q.clear();
                String key = dataSnapshot.getKey();
                int index = listKeys.indexOf(key);

                if (index != -1) {
                    q.remove(index);
                    listKeys.remove(index);
                    adapter.notifyDataSetChanged();
                }
                //addChildEventListener();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getQuestions(String str)
    {
        mDatabase = FirebaseDatabase.getInstance().getReference(sub).child(str);
        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {try {


                user = snapshot.getValue(User.class);
                //  qno.setText(""+(i+1));
                q.add((qn++) + ". " + user.getQ() + "\n Option A: " +
                        user.getA() + "\n Option B: " +
                        user.getB() + "\n Option C: " +
                        user.getC() + "\n Option D: " +
                        user.getD()
                );

                adapter.notifyDataSetChanged();
            }
            catch (Exception r){}
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(getActivity(),"Failed",Toast.LENGTH_LONG).show();
            }
        });

    }


}


