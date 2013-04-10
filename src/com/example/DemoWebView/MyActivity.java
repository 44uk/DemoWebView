package com.example.DemoWebView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MyActivity extends Activity {
    protected WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        webView = (WebView) findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        webView.setWebViewClient(new MyWebViewClient());

        webView.loadUrl("https://raw.github.com/yukku0423/DemoWebView/master/doc/index.html");

        Button btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("javascript:callFromClientApp('Hi! call from Android App.')");
            }
        });
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals("www.yahoo.co.jp")) {
                // This is my web site, so do not override; let my WebView load the page
                // もし自分のサイトだったらオーバーライドせず WebView で開く
                return false;
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            // 自分のサイトドメイン外だったらURLを扱うアクティビティに渡す。
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }
}