package com.example.prachi.webviewactivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by prachi on 2/5/2018.
 */

public class WebAppInterface {



    protected MainActivity parentActivity;
    protected WebView webView;

    public WebAppInterface(MainActivity _activity, WebView _webView) {
        parentActivity = _activity;
        webView = _webView;
    }

    Context mContext;

    // Instantiate the interface and set the context
    WebAppInterface(Context c) {
        mContext = c;
    }


    // Show a toast from the web page


    @JavascriptInterface
    public void saveValues(String usr, String pwd) {
        Toast.makeText(mContext, "entered successfully" + usr, Toast.LENGTH_SHORT).show();
     //   MainActivity.saveValueToSharedPref(usr, pwd);
    }

    @JavascriptInterface
    public void myJavaScriptFun(String usr, String pwd) {
        Log.e("myJavaScriptFunc", "called");
        Toast.makeText(mContext, "values sent" + usr + " " + pwd, Toast.LENGTH_SHORT).show();
    }

}
