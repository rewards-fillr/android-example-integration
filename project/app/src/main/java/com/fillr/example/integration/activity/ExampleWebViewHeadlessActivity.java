package com.fillr.example.integration.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fillr.browsersdk.Fillr;
import com.fillr.browsersdk.model.FillrMapping;
import com.fillr.browsersdk.model.FillrWebView;
import com.fillr.example.integration.R;

import java.util.HashMap;
import java.util.List;

/**
 * This activity demonstrates a headless mode integration with a single WebView.
 * However multiple WebViews are supported.
 */
public class ExampleWebViewHeadlessActivity extends AppCompatActivity {

    //TODO:  place your Fillr development key and secret values here
    //       you can acquire a key/secret pair at https://developer.fillr.com
    private static final String FILLR_KEY = "YOUR_FILLR_DEVELOPER_KEY";
    private static final String FILLR_SECRET = "YOUR_FILLR_SECRET_KEY";

    private Fillr fillr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_headless_webview);
        WebView webView = findViewById(R.id.webview);
        webView.getSettings().setSupportZoom(false);

        setupFillr(webView);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                fillrOnPageFinishedListener(view);
            }
        });
        webView.loadUrl("http://www.fillr.com/demo");
    }

    /**
     * This method is used to initialize Fillr instance.
     * <p>
     * Your developer keys can be obtained from
     *
     * @param webView the WebView which you want autofill to work with.
     * @see <a href="https://developer.fillr.com">Developer Center</a>
     */
    private void setupFillr(WebView webView) {
        //For step 1 look in the com.fillr.FillrApplication
        fillr = Fillr.getInstance();
        //Step 2. Initialize Fillr with the necessary keys.
        fillr.initialise(FILLR_KEY, FILLR_SECRET, this, Fillr.BROWSER_TYPE.WEB_KIT);
        //Step 3. Set the FillMode. We support two different modes headless and headful.
        fillr.setFillMode(Fillr.FillMode.HEADLESS);
        //Step 4. Set the data provider. This is called when,
        //  - The form in question mutates
        //  - When the page finishes loading.
        fillr.profileDataListener(profileDataListener);

        //Step 5. Track the WebView - This can be called as many times as needed. The WebViews are stored as weak references.
        fillr.trackWebView(webView);
    }

    /**
     * Since Headless mode needs to provide it's own data, we use a listener.
     */
    private Fillr.FillrProfileDataListener profileDataListener = new Fillr.FillrProfileDataListener() {

        /**
         * When a form is detected with fields that can be populated,
         * @param webView a reference to the webvie
         * @param mapping
         */
        @Override
        public void onFormDetected(FillrWebView webView, FillrMapping mapping) {
            //get the form fields
            List<String> fields = mapping.getFields();
            if (webView != null && webView.getWebViewObject() instanceof WebView) {
                //we can get a reference to the underlying tracked WebView.
                WebView trackedWebViewObject = (WebView) webView.getWebViewObject();
            }

            //matches the form fields to a profile element
            HashMap<String, String> profileData = assignMockProfileData(fields);

            mapping.setProfileData(profileData);
            if (fillr != null) {
                fillr.performAutofillOnWebView(webView, mapping);
            }
        }
    };

    /**
     * @param webView the WebView attached to the
     * {@link android.webkit.WebViewClient#onPageFinished(WebView, String)} onPageFinished} method.
     */
    private void fillrOnPageFinishedListener(WebView webView) {
        fillr.onPageFinished(webView);
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
        if (fillr != null) {
            fillr.onResume();
        }
    }

    public static HashMap<String, String> assignMockProfileData(List<String> fields) {
        HashMap<String, String> profileData = new HashMap<>();
        HashMap<String, String> mockData = buildProfile();

        for (String field : fields) {
            //field is the namespace which needs to be mapped and sent back.
            //for example "PersonalDetails.FirstName" refers to a persons first name. Therefore data needs to be assigned to this namespace.
            if (mockData.containsKey(field)) {
                profileData.put(field, mockData.get(field));
            }
        }
        return profileData;
    }

    private static HashMap<String, String> buildProfile() {

        HashMap<String, String> profileData = new HashMap<>();
        profileData.put("PersonalDetails.Honorific", "Mr.");
        profileData.put("PersonalDetails.FirstName", "John");
        profileData.put("PersonalDetails.LastName", "Wick");

        profileData.put("Passwords.Password.Password", "password");

        profileData.put("PersonalDetails.Gender", "Male");

        profileData.put("PersonalDetails.BirthDate.Day", "01");
        profileData.put("PersonalDetails.BirthDate.Month", "11");
        profileData.put("PersonalDetails.BirthDate.Year", "1990");

        profileData.put("ContactDetails.CellPhones.CellPhone.Number", "0416414225");
        profileData.put("ContactDetails.Emails.Email.Address", "test@gmail.com");
        profileData.put("CreditCards.CreditCard.NameOnCard", "John Wick");

        profileData.put("CreditCards.CreditCard.Number", "411111111111111111");
        profileData.put("CreditCards.CreditCard.Type", "VISA");
        profileData.put("CreditCards.CreditCard.Expiry.Month", "19");
        profileData.put("CreditCards.CreditCard.Expiry.Year", "2020");
        profileData.put("CreditCards.CreditCard.CCV", "123");

        profileData.put("AddressDetails.HomeAddress.AddressLine1", "245 Chapel Street");
        profileData.put("AddressDetails.HomeAddress.AddressLine2", "Level 6");

        profileData.put("AddressDetails.HomeAddress.Suburb", "Prahran");
        profileData.put("AddressDetails.HomeAddress.PostalCode", "3181");
        profileData.put("AddressDetails.HomeAddress.AdministrativeArea", "Glen Iris");

        profileData.put("AddressDetails.BillingAddress.AddressLine1", "119 Atkinson Street");
        profileData.put("AddressDetails.BillingAddress.AddressLine2", "Unit 18");
        profileData.put("AddressDetails.BillingAddress.Suburb", "Oakleigh");
        profileData.put("AddressDetails.BillingAddress.PostalCode", "3166");
        profileData.put("AddressDetails.BillingAddress.AdministrativeArea", "Monash");

        profileData.put("AddressDetails.PostalAddress.AddressLine1", "501 Blackburn Road");
        profileData.put("AddressDetails.PostalAddress.AddressLine2", "This is addy 2");
        profileData.put("AddressDetails.PostalAddress.PostalCode", "3149");
        profileData.put("AddressDetails.PostalAddress.Suburb", "Mount Waverley");
        profileData.put("AddressDetails.PostalAddress.AdministrativeArea", "Monash City");

        return profileData;
    }

}
