package com.example.framwork.widget;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.framwork.R;


/**
 * Created by wjw on 2016/8/9.
 */
public class CustomWebView extends WebView {
    private ProgressBar progressBar;
    private Context context;
    private WebViewLoadStateListener loadStateListener;

    public interface WebViewLoadStateListener {
        void loadState(boolean state);
    }

    public void setLoadStateListener(WebViewLoadStateListener loadStateListener) {
        this.loadStateListener = loadStateListener;
    }

    public CustomWebView(Context context) {
        super(context);
        initView(context);
    }

    public CustomWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        // 初始化进度条
        progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setProgressDrawable(ContextCompat.getDrawable(context, R.drawable.progress_bar));
        // 设置进度条风格
        progressBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 5, 0, 0));
        addView(progressBar);
        setWebChromeClient(new MyWebChromeClient());
        setWebViewClient(new mWebviewclient());
        getSettings().setDomStorageEnabled(true);
        getSettings().setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        getSettings().setBlockNetworkImage(false);//解决图片不显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
    }

    public class mWebviewclient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("http:") || url.startsWith("https:")) {
                return false;
            }
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(intent);
            } catch (Exception e) {
            }
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view,
                                       SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
        }

    }

    public class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            progressBar.setProgress(newProgress);
            if (newProgress == 100) {
                if (loadStateListener != null) {
                    loadStateListener.loadState(true);
                }
                progressBar.setVisibility(View.GONE);
            } else {
                if (loadStateListener != null)
                    loadStateListener.loadState(false);
                progressBar.setVisibility(View.VISIBLE);
            }
            super.onProgressChanged(view, newProgress);
        }

    }

}
