package com.fillr.example.integration.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.webkit.WebView;
import com.fillr.browsersdk.Fillr;
import com.fillr.browsersdk.model.FillrMapping;
import com.fillr.browsersdk.model.FillrWebView;
import com.fillr.example.integration.R;
import java.util.HashMap;
import java.util.List;

public class ExampleWebViewHeadlessActivity extends AppCompatActivity {

    //TODO:  place your Fillr development key and secret values here
    //       you can acquire a key/secret pair at https://developer.fillr.com
    private static final String FILLR_KEY = "YOUR_FILLR_DEVELOPER_KEY";
    private static final String FILLR_SECRET = "YOUR_FILLR_SECRET_KEY";

    private Fillr fillr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_headful_webview);
        WebView webView = findViewById(R.id.webview);
        webView.getSettings().setSupportZoom(false);
        setupFillr(webView);
        webView.loadUrl("file:///android_asset/sample_form.html");
    }

    /**
     * This method is used to initialize Fillr instance.
     * <p>
     * Your developer keys can be obtained from
     *
     * @param webView the WebView which you want autofill to work with.
     *
     * @see <a href="https://developer.fillr.com">Developer Center</a>
     */
    private void setupFillr(WebView webView) {
        fillr = Fillr.getInstance();
        fillr.initialise(FILLR_KEY, FILLR_SECRET, this, Fillr.BROWSER_TYPE.WEB_KIT);
        fillr.trackWebView(webView);
        fillr.setFillMode(Fillr.FillMode.HEADLESS);


        fillr.profileDataListener(new Fillr.FillrProfileDataListener() {
            @Override
            public void onFormDetected(FillrWebView webview, FillrMapping mapping) {
                //get the form fields
                List<String> fields = mapping.getFields();
                //matches the form fields to a profile element
                HashMap<String, String> profileData = MockDataHelper.assignMockProfileData(fields);

                mapping.setProfileData(profileData);
                Fillr.getInstance().performAutofillOnWebView(webview, mapping);
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        fillrOnResume();
    }

    /**
     * Optional configuration. Used to identify when the hosting activity resumes.
     */
    private void fillrOnResume() {
        fillr.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }


}
