package com.example.htmltoapk;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        WebView myWebView = new WebView(this);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());

        // JavaScript ile Android arasında köprü kuruyoruz
        myWebView.addJavascriptInterface(new WebAppInterface(this), "AndroidBridge");

        myWebView.loadUrl("file:///android_asset/index.html");
        setContentView(myWebView);

        if (getWindow().getInsetsController() != null) {
            WindowInsetsController controller = getWindow().getInsetsController();
            controller.hide(WindowInsets.Type.systemBars());
            controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        }
    }

    // JavaScript'ten çağrılabilecek fonksiyonları içeren sınıf
    public class WebAppInterface {
        AppCompatActivity mContext;

        WebAppInterface(AppCompatActivity c) {
            mContext = c;
        }

        @JavascriptInterface
        public void dosyayaYaz(String dosyaAdi, String veri) {
            try {
                File dosya = new File(mContext.getFilesDir(), dosyaAdi);
                FileOutputStream fos = new FileOutputStream(dosya);
                fos.write(veri.getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @JavascriptInterface
        public String dosyadanOku(String dosyaAdi) {
            File dosya = new File(mContext.getFilesDir(), dosyaAdi);
            if (!dosya.exists()) return "";

            StringBuilder stringBuilder = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(dosya));
                String satir;
                while ((satir = br.readLine()) != null) {
                    stringBuilder.append(satir).append("\n");
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString().trim();
        }
    }
}
