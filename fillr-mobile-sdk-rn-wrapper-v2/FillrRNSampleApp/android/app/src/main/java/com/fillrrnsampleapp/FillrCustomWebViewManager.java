package com.fillrrnsampleapp;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.WebView;

import androidx.annotation.UiThread;

import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.fillr.browsersdk.Fillr;
import com.fillr.browsersdk.model.FillrWebViewClient;

public class FillrCustomWebViewManager extends SimpleViewManager<WebView> {

    public static final String REACT_CLASS = "FillrCustomWebView";

    static WebView webView;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected WebView createViewInstance(ThemedReactContext reactContext) {
        webView = new WebView(reactContext);
        Fillr.getInstance().trackWebView(webView);
        webView.setWebViewClient(new FillrWebViewClient());
        Log.d(FillrHeadlessModule.TAG, "fillr Tracked: ");
        return webView;
    }

    @ReactProp(name = "url")
    public void loadUrl(WebView webView, String urlPath) {
        webView.loadUrl(urlPath);
    }

    @ReactMethod
    @UiThread
    public void triggerFill() {
        if (webView != null) {
            Handler mainHandler = new Handler(Looper.getMainLooper());
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Fillr.getInstance().triggerFill(webView);
                }
            };
            mainHandler.post(runnable);
        }
    }
}
