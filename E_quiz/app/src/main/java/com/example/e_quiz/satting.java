package com.example.e_quiz;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;


public class satting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satting);
        setTitle("Settings");
        if(getSupportActionBar()!=null)
        {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        final TabHost host = (TabHost) findViewById(R.id.tabhost);
        host.setup();
        host.setBackgroundColor(Color.parseColor("#444444"));

        //Tab 1

        TabHost.TabSpec spec = host.newTabSpec("Tab 1");
        spec.setContent(R.id.tab1);

        spec.setIndicator("Set Time");
        host.addTab(spec);


        //Tab 2
        spec = host.newTabSpec("Tab 2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("No of Question");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Tab 3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Update");
        host.addTab(spec);

        //tab4

        spec = host.newTabSpec("Tab 3");
        spec.setContent(R.id.tab4);
        spec.setIndicator("Add New");
        host.addTab(spec);


        int tab = host.getCurrentTab();
        for (int i = 0; i < host.getTabWidget().getChildCount(); i++) {
            // When tab is not selected
            host.getTabWidget().getChildAt(host.getCurrentTab()).setBackgroundColor(Color.parseColor("#FDD030"));
            TextView tv = (TextView) host.getTabWidget().getChildAt(tab).findViewById(android.R.id.title);
            tv.setTextColor(Color.BLACK);
        }

        host.setOnTabChangedListener(new TabHost.OnTabChangeListener(){
            @Override
            public void onTabChanged(String tabId) {
                int tab = host.getCurrentTab();
                for (int i = 0; i < host.getTabWidget().getChildCount(); i++) {
                    // When tab is not selected
                    host.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#444444"));
                    TextView tv = (TextView) host.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                    tv.setTextColor(Color.WHITE);
                }
                // When tab is selected
                host.getTabWidget().getChildAt(host.getCurrentTab()).setBackgroundColor(Color.parseColor("#FDD030"));
                TextView tv = (TextView) host.getTabWidget().getChildAt(tab).findViewById(android.R.id.title);
                tv.setTextColor(Color.BLACK);
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
