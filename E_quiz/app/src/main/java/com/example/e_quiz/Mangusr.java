package com.example.e_quiz;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Mangusr extends AppCompatActivity {

    String sub;
    User user;
    ListView lv;
    private DatabaseReference mDatabase;
    static int key;
    ArrayList<String> listItems;
    ArrayList<String> listKeys ;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mangusr);
        setTitle("Manage User");
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
       lv=(ListView)findViewById(R.id.mangusr);
        listItems = new ArrayList<String>();
        listKeys= new ArrayList<String>();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        listItems.clear();
        adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.list,
                listItems);
        lv.setAdapter(adapter);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        addChildEventListener();
        }//end of onCreate

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home)
        {finish();}
        return super.onOptionsItemSelected(item);
    }



    private void addChildEventListener()
    {
        ChildEventListener childListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getQuestions(dataSnapshot.getKey());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                int index = listKeys.indexOf(key);

                if (index != -1) {
                    listItems.remove(index);
                    listKeys.remove(index);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mDatabase.getDatabase().getReference("Users").addChildEventListener(childListener);
    }

    public void getQuestions(String str)
    {
        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(str);
        // mDatabase = FirebaseDatabase.getInstance().getReference(sub);
        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                user=snapshot.getValue(User.class);
                listItems.add("Name: "+user.getName()+"\nEmail: "+user.getEmail());
                adapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
            }
        });

    }
}
