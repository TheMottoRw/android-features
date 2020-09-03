package com.example.a17rp01001;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViews extends AppCompatActivity {
 WebView webId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webId = findViewById(R.id.webid);
        webId.loadUrl("https://www.google.com");
        webId.getSettings().setJavaScriptEnabled(true);
        webId.setWebViewClient(new WebViewClient());
    }
}
