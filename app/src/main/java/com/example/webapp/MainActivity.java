package com.example.webapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
        WebView wv;
        ProgressBar pb;
        String url="https://www.youtube.com/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wv=findViewById(R.id.wv);
        pb=findViewById(R.id.progressBar);
        if (isInternetConnected()){
            wv.getSettings().setLoadsImagesAutomatically(true);
            wv.getSettings().setJavaScriptEnabled(true);
            wv.getSettings().setUseWideViewPort(true);
            wv.getSettings().setLoadWithOverviewMode(true);
            wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            wv.setWebChromeClient(new WebChromeClient());
            wv.setWebViewClient(new WebViewClient(){
                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    super.onReceivedError(view, errorCode, description, failingUrl);
                    pb.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    pb.setVisibility(View.VISIBLE);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    pb.setVisibility(View.GONE);
                }

                @Override
                public void onPageCommitVisible(WebView view, String url) {
                    super.onPageCommitVisible(view, url);
                    pb.setVisibility(View.GONE);
                }
            });
            wv.loadUrl(url);
        }else {
            Toast.makeText(this, "Internet", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getAction()==KeyEvent.ACTION_DOWN){
            switch (keyCode){
               case  KeyEvent.KEYCODE_BACK:
                if (wv.canGoBack()){
                    wv.goBack();
                }else {
                    finish();
                }
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    private  boolean isInternetConnected(){
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            return  cm.getActiveNetworkInfo()!=null;
    }
}