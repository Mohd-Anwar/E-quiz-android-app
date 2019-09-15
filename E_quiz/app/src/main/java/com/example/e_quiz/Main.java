package com.example.e_quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String lang;
    Button start;
    TextView un;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header=navigationView.getHeaderView(0);
        un=(TextView)header.findViewById(R.id.usernam);
                savedInstanceState=getIntent().getExtras();
        if(savedInstanceState!=null){
            un.setText(savedInstanceState.getString("uname"));
        }





        Spinner sp=(Spinner)findViewById(R.id.spinner1);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lang=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        start=findViewById(R.id.strt);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"You Selected "+lang,Toast.LENGTH_LONG).show();
                Intent i=new Intent(getApplicationContext(),test.class);
                i.putExtra("sub",lang);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Bundle b=new Bundle();
        if (id == R.id.books) {

           Intent i=new Intent(getApplicationContext(),Boo.class);
            startActivity(i);
            // Handle the camera action

        } else if (id == R.id.checkr) {
        Intent i=new Intent(getApplicationContext(),result.class);
        startActivity(i);

        } else if (id == R.id.stting) {
            Intent i=new Intent(getApplicationContext(),satting.class);
            startActivity(i);

        } else if (id == R.id.abtus) {
            Intent i=new Intent(getApplicationContext(),aboutus.class);
            startActivity(i);

        } else if(id==R.id.report)
        {
            Intent i=new Intent(getApplicationContext(),reportus.class);
            startActivity(i);

        }
        else if(id==R.id.askq)
        {
            Intent i=new Intent(getApplicationContext(),FAQ.class);
            startActivity(i);

        }

        else if (id == R.id.logot)
        {
            Intent i=new Intent(getApplicationContext(),Login.class);
            startActivity(i);
            Toast.makeText(getApplicationContext(),"Logout Successfully",
                    Toast.LENGTH_SHORT).show();
          }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
