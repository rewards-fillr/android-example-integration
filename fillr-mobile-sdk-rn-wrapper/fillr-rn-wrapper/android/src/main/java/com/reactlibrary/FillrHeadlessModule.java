
package com.reactlibrary;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.fillr.FillrApplication;
import com.fillr.browsersdk.Fillr;
import com.fillr.browsersdk.FillrAuthenticationStore;
import com.fillr.browsersdk.model.FillrBrowserProperties;
import com.fillr.browsersdk.model.FillrMapping;
import com.fillr.browsersdk.model.FillrWebView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;


public class FillrHeadlessModule extends ReactContextBaseJavaModule {

    public static final String TAG = "FillrHeadlessMode";

    public FillrHeadlessModule(ReactApplicationContext reactContext) {
        super(reactContext);
        FillrApplication init = FillrApplication.getInstance();
        init.init(reactContext);
    }

    @Override
    public String getName() {
        return "FillrModule";
    }

    @ReactMethod
    public void initializeFillr() {

        Activity currentActivity = getCurrentActivity();

        if (currentActivity != null) {

            Fillr fillr = Fillr.getInstance();

            FillrBrowserProperties fillrBrowserProperties
                    = new FillrBrowserProperties("General", "General",
                    R.drawable.com_fillr_icon_keyboard_icon_selector, Color.parseColor("#389d00"), FillrAuthenticationStore.WidgetSource.LOCAL);

            String devKey = "your_developer_key";
            String secretKey = "your_secret_key";

            fillr.initialise(devKey, secretKey, currentActivity, Fillr.BROWSER_TYPE.WEB_KIT, fillrBrowserProperties);

            fillr.setFillMode(Fillr.FillMode.HEADLESS);
            fillr.profileDataListener(new Fillr.FillrProfileDataListener() {
                @Override
                public void onFormDetected(FillrWebView webview, FillrMapping fillrMapping) {
                    Log.d(TAG, "form detected: ");
                    List<String> fields = fillrMapping.getFields();
                    HashMap<String, String> profileData = assignMockProfileData(fields);
                    fillrMapping.setProfileData(profileData);
                    Fillr.getInstance().performAutofillOnWebView(webview, fillrMapping);
                }
            });
            Log.d(TAG, "initializeFillr: ");
        }
    }

    @ReactMethod
    public void logMessage(String message) {
        Toast.makeText(getCurrentActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private HashMap<String, String> assignMockProfileData(List<String> fields) {
        HashMap<String, String> profileData = new HashMap<>();
        try {
            JSONObject mockData = buildProfile();
            for (String field : fields) {
                if (mockData.has(field)) {
                    profileData.put(field, mockData.getString(field));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return profileData;
    }

    private JSONObject buildProfile() {
        ReactApplicationContext reactApplicationContext = getReactApplicationContext();
        JSONObject profileData = new JSONObject();
        try {
            Resources res = reactApplicationContext.getResources();
            InputStream in_s = res.openRawResource(R.raw.profile);
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            profileData = new JSONObject(new String(b));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return profileData;
    }


}