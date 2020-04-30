package com.fillr.example.integration.activity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;

import androidx.appcompat.app.AppCompatActivity;

import com.fillr.browsersdk.Fillr;
import com.fillr.browsersdk.FillrConfig;
import com.fillr.browsersdk.model.FillrBrowserProperties;
import com.fillr.browsersdk.model.FillrCartInformationExtraction;
import com.fillr.browsersdk.model.FillrMapping;
import com.fillr.browsersdk.model.FillrWebView;
import com.fillr.browsersdk.model.FillrWebViewClient;
import com.fillr.browsersdk.model.FillrWidgetAuth;
import com.fillr.example.integration.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;

/**
 * This activity demonstrates a headless mode integration with a single WebView.
 * However multiple WebViews are supported.
 */
public class ExampleWebViewHeadlessActivity extends AppCompatActivity {

    //TODO:  place your Fillr development key and secret values here
    //       you can acquire a key/secret pair at product@fillr.com
    private static final String FILLR_KEY = "YOUR_DEVELOPER_KEY";
    private static final String FILLR_SECRET = "YOUR_FILLR_SECRET_KEY";
    private static final String FILLR_CART_SCRAPER_USERNAME = "YOUR_CART_SCRAPER_USERNAME";
    private static final String FILLR_CART_SCRAPER_PASSWORD = "YOUR_CART_SCRAPER_PASSWORD";

    private Fillr fillr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_headless_webview);
        WebView webView = findViewById(R.id.webview);
        webView.getSettings().setSupportZoom(false);

        setupFillr(webView);

        webView.setWebViewClient(new FillrWebViewClient());

        //Optional config if Affiliates are enabled,
        //webView.setWebViewClient(new AffiliateWebViewClient());

        //if cart scraping has been enabled - cart scraper credentials have to be set in the init method
        Fillr.getInstance().setCartInformationExtractionEnabled(true);
        Fillr.getInstance().setCartInformationExtractionListener(new FillrCartInformationExtraction.FillrCartInformationExtractionListener() {
            @Override
            public void onCartDetected(Object webView, FillrCartInformationExtraction.FillrCartInformation cartInfo) {
                try {
                    JSONObject cart = new JSONObject(cartInfo.json);

                    JSONArray productList = cart.getJSONArray("product_list");
                    double cartTotal = cart.getDouble("cart_total");

                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < productList.length(); i++) {
                        builder.append(productList.get(i).toString()).append("\n");
                    }
                    builder.append("Total ").append(cartTotal).append("\n");
                    builder.append("page_url: ").append(cart.getString("page_url")).append("\n");
                    builder.append("version: ").append(cart.getString("version"));
                    android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(ExampleWebViewHeadlessActivity.this).create();
                    alertDialog.setTitle("Cart Detected " + productList.length());
                    alertDialog.setMessage(builder.toString());
                    alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        webView.loadUrl("https://www.fillr.com/test");
    }


    private class AffiliateWebViewClient extends FillrWebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            boolean isIntegratedMerchant = false; //... check webView.url against zip merchants list
            if (!isIntegratedMerchant) {
                Fillr.getInstance().processAffiliateForURL(url, view);
            }
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            boolean isIntegratedMerchant = false; //... check webView.url against zip merchants list
            if (!isIntegratedMerchant) {
                Fillr.getInstance().processAffiliateForRequest(request, view);
            }
            return super.shouldInterceptRequest(view, request);
        }

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

        //Step 2. Initialize Fillr with the correct config.
        FillrWidgetAuth optionalCartScraperWidgetAuth = new FillrWidgetAuth(FILLR_CART_SCRAPER_USERNAME, FILLR_CART_SCRAPER_PASSWORD);
        FillrConfig config = new FillrConfig(FILLR_KEY, FILLR_SECRET, optionalCartScraperWidgetAuth);

        FillrBrowserProperties properties = new FillrBrowserProperties("BrowserName", "BrowserName");
        fillr.initialise(config, this, Fillr.BROWSER_TYPE.WEB_KIT, properties);

        //Step 3. Set the FillMode. We support two different modes headless and headful.
        fillr.setFillMode(Fillr.FillMode.HEADLESS);
        //Step 4. Set the data provider. This is called when,
        //  - The form in question mutates
        //  - When the page finishes loading.
        fillr.profileDataListener(profileDataListener);

        //Step 5 - Setup Affiliate Url Redirection (optional feature)
        config.setAffiliateProvider(FillrConfig.AFFILIATE_PROVIDER_VIGLINK, "Affiliate API Key Provided by Fillr");


        //Step 6. Track the WebView - This can be called as many times as needed. The WebViews are stored as weak references.
        fillr.trackWebView(webView, FillrWebView.OPTIONS_TLS_PROXY);
        //Optional config track method
        //fillr.trackWebView(webView,  FillrWebView.OPTIONS_NONE);
    }

    /**
     * Since Headless mode needs to provide it's own data, we use a listener.
     */
    Fillr.FillrProfileDataListener profileDataListener = new Fillr.FillrProfileDataListener() {

        /**
         * When a form is detected with fields that can be populated,
         * @param webView a reference to the webvie
         * @param mapping mapping request metadata
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

    public static HashMap<String, String> assignMockProfileData(List<String> fields) {
        HashMap<String, String> profileData = new HashMap<>();
        HashMap<String, String> mockData = ExampleWebViewHeadlessActivity.fillrFormNamespaceToProfileMapping();

        for (String field : fields) {
            //field is the namespace which needs to be mapped and sent back.
            //for example "PersonalDetails.FirstName" refers to a persons first name. Therefore data needs to be assigned to this namespace.
            if (mockData.containsKey(field)) {
                profileData.put(field, mockData.get(field));
            }
        }
        return profileData;
    }

    private static HashMap<String, String> fillrFormNamespaceToProfileMapping() {

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
