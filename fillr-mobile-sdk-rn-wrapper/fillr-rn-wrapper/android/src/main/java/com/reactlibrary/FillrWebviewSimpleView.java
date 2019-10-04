package com.reactlibrary;

import android.util.Log;
import android.webkit.WebView;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.fillr.browsersdk.Fillr;
import com.fillr.browsersdk.model.FillrWebViewClient;

public class FillrWebviewSimpleView extends SimpleViewManager<WebView> {

    public static final String REACT_CLASS = "FillrWebView";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected WebView createViewInstance(ThemedReactContext reactContext) {
        WebView webView = new WebView(reactContext);
        Fillr.getInstance().trackWebView(webView);
        webView.setWebViewClient(new FillrWebViewClient());
        Log.d(FillrHeadlessModule.TAG, "fillr Tracked: ");
        return webView;
    }

    @ReactProp(name = "url")
    public void loadUrl(WebView webView, String urlPath) {
        webView.loadUrl(urlPath);
    }

}
