package com.zncm.easysc.modules;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zncm.easysc.R;

public class BikeWebView extends BaseHomeActivity {
    WebView mWebView = null;
    String word = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        loadPage();
        super.onCreate(savedInstanceState);
    }

    private void loadPage() {
        word = getIntent().getExtras().getString("word");
        setContentView(R.layout.zi_webview);
        getSupportActionBar().setTitle("百度百科-" + word);
        mWebView = ((WebView) findViewById(R.id.zi_webview_wv01));
        mWebView.loadUrl("http://wapbaike.baidu.com/search?word=" + word);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setSupportZoom(false);
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.getSettings().setJavaScriptEnabled(true);
                view.loadUrl(url);
                view.requestFocus();
                return true;
            }
        });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
