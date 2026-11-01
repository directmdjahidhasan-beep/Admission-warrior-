package com.example.htmlrunner;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);

        requestAppPermissions();

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setEnableSmoothTransition(true);
        webView.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                request.grant(request.getResources());
            }
        });

        loadSampleHtml();
    }

    private void requestAppPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        }
    }

    private void loadSampleHtml() {
        String sampleHtml = "<!DOCTYPE html>" +
                "<html><head><meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "<style>" +
                "body { font-family: Arial; text-align: center; background: #121212; color: white; padding: 20px; }" +
                "#clock { font-size: 32px; color: #00ffcc; font-weight: bold; margin: 20px 0; }" +
                ".box { width: 80px; height: 80px; background: red; margin: 20px auto; position: relative; animation: move 2s infinite alternate linear; }" +
                "@keyframes move { 0% { transform: translateX(-100px) rotate(0deg); } 100% { transform: translateX(100px) rotate(360deg); } }" +
                "button { padding: 10px 20px; font-size: 16px; background: #00ffcc; border: none; border-radius: 5px; cursor: pointer; }" +
                "</style></head><body>" +
                "<h2>HTML Pro Runner Loaded!</h2>" +
                "<div id='clock'>00:00:00</div>" +
                "<div class='box'></div>" +
                "<button onclick='saveData()'>Save Local Storage</button>" +
                "<p id='saved'></p>" +
                "<script>" +
                "function updateTime() {" +
                "  const now = new Date();" +
                "  document.getElementById('clock').innerText = now.toLocaleTimeString() + '.' + String(now.getMilliseconds()).padStart(3, '0');" +
                "}" +
                "setInterval(updateTime, 10);" +
                "function saveData() {" +
                "  localStorage.setItem('testData', 'Saved at: ' + new Date().toLocaleTimeString());" +
                "  document.getElementById('saved').innerText = localStorage.getItem('testData');" +
                "}" +
                "document.getElementById('saved').innerText = localStorage.getItem('testData') || 'No Data Saved Yet';" +
                "</script></body></html>";

        webView.loadDataWithBaseURL("https://localhost", sampleHtml, "text/html", "UTF-8", null);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
    }
