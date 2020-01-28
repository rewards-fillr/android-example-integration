package com.fillr.example.integration.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.fillr.browsersdk.Fillr;
import com.fillr.browsersdk.FillrConfig;
import com.fillr.browsersdk.model.FillrWebView;
import com.fillr.browsersdk.model.FillrBrowserProperties;
import com.fillr.browsersdk.model.FillrWebViewClient;
import com.fillr.browsersdk.model.FillrWidgetAuth;
import com.fillr.embedded.profile.FEMainActivity;
import com.fillr.example.integration.R;

/**
 * This example Activity contains a web-view which loads a demonstration form
 * packaged with the app.
 * <p>
 * Tapping on a field in the form will bring up a toolbar option to 'Autofill
 * this form', which when tapped will launch Fillr and autofill the form.
 */
public class ExampleWebViewHeadfulActivity extends AppCompatActivity {
    //TODO:  place your Fillr development key and secret values here
    //       you can acquire a key/secret pair at product@fillr.com
    private static final String FILLR_KEY = "YOUR_DEVELOPER_KEY";
    private static final String FILLR_SECRET = "YOUR_FILLR_SECRET_KEY";
    private static final String FILLR_CART_SCRAPER_USERNAME = "YOUR_CART_SCRAPER_USERNAME";
    private static final String FILLR_CART_SCRAPER_PASSWORD = "YOUR_CART_SCRAPER_PASSWORD";

    private WebView webView;
    private Fillr fillr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_headful_webview);
        webView = findViewById(R.id.webview);
        webView.getSettings().setSupportZoom(false);
        webView.setWebViewClient(new FillrWebViewClient());

        //Fillr autofill setup
        fillr = Fillr.getInstance();

        //used to enable the cart scraper module
        FillrWidgetAuth optionalConfig = new FillrWidgetAuth(FILLR_CART_SCRAPER_USERNAME, FILLR_CART_SCRAPER_PASSWORD);

        FillrConfig config = new FillrConfig(FILLR_KEY, FILLR_SECRET, optionalConfig);
        FillrBrowserProperties properties = new FillrBrowserProperties("BrowserName", "BrowserName");
        fillr.initialise(config, this, Fillr.BROWSER_TYPE.WEB_KIT, properties);

        //note that this must be called prior to the loadUrl() call, below
        fillr.trackWebView(webView, FillrWebView.OPTIONS_TLS_PROXY);

        //uncomment this block to place an 'Autofill' ActionBar item that launches Fillr when tapped
        /*ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.action_bar_content, null);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(actionBarLayout);

        FillrAutofillButton fillrButton = actionBarLayout.findViewById(R.id.autofill_button);

        //option 1 - let Fillr manage the ActionBar button
        fillr.setAutofillButton(fillrButton);

        //option 2 - manage the ActionBar button ourselves
        //fillrButton.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        fillr.triggerFill(webView);
        //    }
        //});*/
        webView.loadUrl("file:///android_asset/sample_form.html");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (fillr != null) {
            fillr.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (fillr != null) {
            fillr.onPause();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == Fillr.FILLR_REQUEST_CODE && resultCode == RESULT_OK) {
            fillr.processForm(intent);
        }
    }

    /**
     * @param webView the WebView attached to the
     *                {@link android.webkit.WebViewClient#onPageFinished(WebView, String)} onPageFinished} method.
     */
    private void fillrOnPageFinishedListener(WebView webView) {
        fillr.onPageFinished(webView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle taps on the action bar items
        switch (item.getItemId()) {
            case R.id.about_app:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.about)
                        .setMessage(R.string.fillr_about_app)
                        .setPositiveButton(R.string.ok, null).show();
                return true;
            case R.id.fillr_settings:
                //this will launch the Fillr settings activity
                startActivity(new Intent(this, FEMainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
