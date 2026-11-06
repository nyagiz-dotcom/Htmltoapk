package com.example.htmltoapk;

import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Kenardan kenara (Edge-to-Edge) görünümü etkinleştirir
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        WebView myWebView = new WebView(this);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl("file:///android_asset/index.html");

        setContentView(myWebView);

        // Üst durum çubuğunu ve alt gezinme butonlarını gizler
        if (getWindow().getInsetsController() != null) {
            WindowInsetsController controller = getWindow().getInsetsController();
            controller.hide(WindowInsets.Type.systemBars());
            controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        }
    }
}
