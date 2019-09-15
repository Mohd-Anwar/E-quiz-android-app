package com.example.e_quiz;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Boo extends AppCompatActivity {


    private ExpandableListView expandableListView;

    private ExpandableListViewAdapter expandableListViewAdapter;

    private List<String> listDataGroup;

    private HashMap<String, List<String>> listDataChild;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;

    List<String> lease_offer;
    List<List<String>> url;
    String link="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boo);
        if(getSupportActionBar()!=null)
        {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        // initializing the objects
        new DownloadJason().execute();
        initListeners();
        }


    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

        private void initViews() {

            expandableListView = findViewById(R.id.expandableListView);

        }

        /**
         * method to initialize the listeners
         */
        private void initListeners() {

            // ExpandableListView on child click listener
            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                @Override
                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {

                    Intent i=new Intent(getApplicationContext(),books.class);
                    i.putExtra("link",url.get(groupPosition).get(childPosition));
                    startActivity(i);
                    /*Toast.makeText(getApplicationContext(), listDataGroup.get(groupPosition)
                            + " : " +url.get(groupPosition).get(childPosition), Toast.LENGTH_SHORT)
                            .show();*/
                    return false;
                }
            });

            // ExpandableListView Group expanded listener
      /*  expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) { Toast.makeText(getApplicationContext(),
                        listDataGroup.get(groupPosition) + " " + getString(R.string.text_collapsed),
                        Toast.LENGTH_SHORT).show();
            }
        });*/

            // ExpandableListView Group collapsed listener
      /*  expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataGroup.get(groupPosition) + " " + getString(R.string.text_collapsed),
                        Toast.LENGTH_SHORT).show();

            }
        });
*/
        }

        /**
         * method to initialize the objects
         */
        private void initObjects() {

            // initializing the list of groups
            listDataGroup = new ArrayList<>();

            // initializing the list of child
            listDataChild = new HashMap<>();

            // initializing the adapter object
            //expandableListViewAdapter = new ExpandableListViewAdapter(this, listDataGroup, listDataChild);

            // setting list adapter
            //expandableListView.setAdapter(expandableListViewAdapter);

        }

    public String readJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }



    private class DownloadJason extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            initViews();
            initObjects();
            super.onPreExecute();

//            Showing Progress dialog
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // TODO Auto-generated method stub



//            For Header title Arraylist
               // listDataHeader = new ArrayList<String>();
//                Hashmap for child data key = header title and value = Arraylist (child data)
                //listDataChild = new HashMap<String, List<String>>();
            url=new ArrayList<List<String>>();
                try {
                    JSONObject jsonstr = new JSONObject(readJSONFromAsset());
                    JSONArray jarray = jsonstr.getJSONArray("Subject");
                        int j;
                    for (int hk = 0; hk < jarray.length(); hk++) {
                        JSONObject d = jarray.getJSONObject(hk);

                        // Adding Header data
                        listDataGroup.add(d.getString("name"));

                        // Adding child data for lease offer
                        lease_offer = new ArrayList<String>();
                            url.add(new ArrayList<String>());
                        JSONArray book=d.getJSONArray("books");
                        for( j=0;j<book.length();j++) {

                            JSONObject bn = book.getJSONObject(j);
                            lease_offer.add(bn.getString("book"));
                            url.get(hk).add(j,bn.getString("url"));

                        }
                        listDataChild.put(listDataGroup.get(hk), lease_offer);

                        // Header into Child data
                       // listDataChild.put();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }



            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub



            expandableListViewAdapter = new ExpandableListViewAdapter(getApplicationContext(), listDataGroup, listDataChild);

            // setting list adapter
            expandableListView.setAdapter(expandableListViewAdapter);


            // initializing the listeners
            super.onPostExecute(result);
            // preparing list data
            //initListData();
        }
    }


}

