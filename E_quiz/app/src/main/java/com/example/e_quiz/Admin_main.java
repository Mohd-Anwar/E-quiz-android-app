package com.example.e_quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Admin_main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    LinearLayout l;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        l=findViewById(R.id.managusr);
        l.setClickable(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getApplicationContext(),"LogOut Successfully!!", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getApplicationContext(),Mangusr.class);
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
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.admin_main, menu);
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

        if (id == R.id.logout) {
            Intent i=new Intent(getApplicationContext(),Login.class);
            startActivity(i);
            Toast.makeText(getApplicationContext(),"LogOut Successfully!!", Toast.LENGTH_SHORT).show();
            // Handle the camera action
        } else if (id == R.id.C) {

            Intent i=new Intent(getApplicationContext(),Setting.class);
            i.putExtra("sub","C");
            startActivity(i);

        } else if (id == R.id.cpp) {

            Intent i=new Intent(getApplicationContext(),Setting.class);
            i.putExtra("sub","C++");
            startActivity(i);

        } else if (id == R.id.Java) {
            Intent i=new Intent(getApplicationContext(),Setting.class);
            i.putExtra("sub","Java");
            startActivity(i);
        } else if (id == R.id.Dotnet) {
            Intent i=new Intent(getApplicationContext(),Setting.class);
            i.putExtra("sub","Dotnet");
            startActivity(i);
        } else if (id == R.id.Android) {
            Intent i=new Intent(getApplicationContext(),Setting.class);
            i.putExtra("sub","Android");
            startActivity(i);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
