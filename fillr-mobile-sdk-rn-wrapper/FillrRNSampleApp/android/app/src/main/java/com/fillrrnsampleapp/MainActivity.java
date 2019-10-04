package com.fillrrnsampleapp;

import com.facebook.react.ReactActivity;
import com.fillr.browsersdk.Fillr;

public class MainActivity extends ReactActivity {

    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     */
    @Override
    protected String getMainComponentName() {
        return "FillrRNSampleApp";
    }

    @Override
    protected void onPause() {
        super.onPause();
        Fillr.getInstance().onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Fillr.getInstance().onResume();
    }

}
