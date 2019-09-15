package com.example.e_quiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class books extends AppCompatActivity {

    ProgressBar progressBar;
    WebView webView;
    String url;
    private ProgressDialog prog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.books);
        setTitle("Books");

        savedInstanceState=getIntent().getExtras();
        if(savedInstanceState!=null){
            url=savedInstanceState.getString("link");
            }
        if(getSupportActionBar()!=null)
        {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        prog=new ProgressDialog(this);
        prog.setMessage("Please wait!\nLoading...");
        prog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        prog.setIndeterminate(true);
        prog.setProgress(0);
        prog.show();

        webView=(WebView)findViewById(R.id.webv);
        webView.getSettings().setJavaScriptEnabled(true);
        // String filepath="https://www.tutorialspoint.com/java/java_tutorial.pdf";
        webView.loadUrl("http://docs.google.com/gview?embedded=true&url="+url);
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.zoomIn();
                webView.getSettings().setBuiltInZoomControls(true);
                webView.getSettings().setDisplayZoomControls(false );
                prog.dismiss();
                super.onPageFinished(view, url);
            }
        });
   //     new DownloadJason().execute();
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
