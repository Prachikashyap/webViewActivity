package com.example.prachi.webviewactivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.lang.Object;
import java.util.jar.Attributes;

import static com.example.prachi.webviewactivity.SharedPref.Email;
import static com.example.prachi.webviewactivity.SharedPref.Name;
import static com.example.prachi.webviewactivity.SharedPref.mypreference;

public class MainActivity extends AppCompatActivity {
    static SharedPreferences prefs;
    static SharedPreferences.Editor editor;
    String usr;
    String pwd;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getPreferences(MODE_PRIVATE);
        editor = getPreferences(MODE_PRIVATE).edit();

        webView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webView.addJavascriptInterface(new WebAppInterface(this), "AndroidInterface"); // To call methods in Android from using js in the html, AndroidInterface.showToast, AndroidInterface.getAndroidVersion etc

        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());

        usr= prefs.getString("usr", "testdoc@agatsa.com");
        pwd= prefs.getString("pwd", "test123");
        if(!usr.isEmpty()){
            webView.loadUrl("https://sanketlifep.azurewebsites.net/doctor-login.html");
            //webView.loadUrl("javascript:myJavaScriptFunc(" + usr + "," + pwd + ")");
        }else
        webView.loadUrl("https://sanketlifep.azurewebsites.net/doctor-login.html");


        //webView.setWebChromeClient(new MyWebChromeClient());
    }
    public static void saveValueToSharedPref(String usr, String pwd){
        editor.putString("usr", usr);
        editor.putString("pwd", pwd);
        editor.apply();
    }
    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            //Calling a javascript function in html page
            //view.loadUrl("javascript:alert(showVersion('called by Android'))");
            //Is the url the login-page?

            if (url.equals("https://sanketlifep.azurewebsites.net/doctor-login.html")) {

                webView.loadUrl("javascript:myJavaScriptFunc('" + usr + "','" + pwd + "')");
                //load javascript to set the values to input fields
              /*  SharedPreferences prefs = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();

                if (usr == null || pwd == null) {
                    //we  have no values - leave input fields blank
                    return;
                }
                view.loadUrl("javascript:fillValues(" + usr + "," + pwd + ");");

                if (!prefs.getString("usr","").isEmpty()
                        && !prefs.getString("pwd", "").isEmpty()){
                    view.loadUrl("javascript:myJavaScriptFunc(" + usr + "," + pwd + ")");
                    Toast.makeText(MainActivity.this, "sent" + usr ,Toast.LENGTH_SHORT).show();
                }*/
            }
        }
    }
    public class JavaScriptInterface {
        /**
         * this should be triggered when user and pwd is correct, maybe after
         * successful login
         */
        public void saveValues(String usr, String pwd ) {
            if (usr == null || pwd == null) {
                return;
            }
            //save the values in SharedPrefs
            SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
            editor.putString("usr", usr);
            editor.putString("pwd", pwd);
            editor.apply();
        }
       /* private class MyWebChromeClient extends WebChromeClient {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Log.d("LogTag", message);
                result.confirm();
                return true;
            }
        }*/
    }
}