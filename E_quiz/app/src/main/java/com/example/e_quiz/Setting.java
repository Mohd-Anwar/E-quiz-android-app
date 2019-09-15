package com.example.e_quiz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class Setting extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public String sub;
    Fragment fr=null;
    FragmentManager frgm;
    FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        savedInstanceState=getIntent().getExtras();
        if(savedInstanceState!=null)
        {
          sub= savedInstanceState.getString("sub");
        }


      //  Toast.makeText(getApplicationContext(),"Admin Subject is "+sub,Toast.LENGTH_LONG).show();
if(getSupportActionBar()!=null){

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
}

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       getMenuInflater().inflate(R.menu.men2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home)
        {
            finish();
        }

        if(id==R.id.setnoq)
        {
            fr=new set_noq();
            frgm=getSupportFragmentManager();
            ft=frgm.beginTransaction();
            ft.replace(R.id.fragmentPlace,fr);
            ft.commit();
        }
        if(id==R.id.addqu)
        {
            fr=new Add_Q();
            frgm=getSupportFragmentManager();
            ft=frgm.beginTransaction();
            ft.replace(R.id.fragmentPlace,fr);
            ft.commit();
        }
        if(id==R.id.dele)
        {
            fr=new Manage_q();
            frgm=getSupportFragmentManager();
            ft=frgm.beginTransaction();
            ft.replace(R.id.fragmentPlace,fr);
            ft.commit();
        }
        if(id==R.id.updatq)
        {
            fr=new Manage_q();
            frgm=getSupportFragmentManager();
            ft=frgm.beginTransaction();
            ft.replace(R.id.fragmentPlace,fr);
            ft.commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        int id = item.getItemId();
        //Fragment fr=null;
        //FragmentManager frgm=getSupportFragmentManager();
        //FragmentTransaction ft=frgm.beginTransaction();

        if (id == R.id.setnoq) {

          //  fr=new Set_time();
           // ft.replace(R.id.fragmentPlace,fr);
            //ft.commit();

        } else if (id == R.id.noq) {
            //fr=new set_noq();
            //ft.replace(R.id.fragmentPlace,fr);
            //ft.commit();
        } else if (id == R.id.add) {

        } else if (id == R.id.update) {

        } else if (id == R.id.delete) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
