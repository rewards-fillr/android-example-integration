---
layout: square
title: Android Example Integration
excerpt: A minimal working example project demonstrating how to integrate with the Fillr Embedded SDK on Android.
---

### Overview

This example project demonstrates a minimal integration with the Fillr Embedded SDK on Android, highlighting the configuration and coding changes necessary for a successful integration with a new or preexisting Android application.  

The code and this project (and highlighted below) can be used as a roadmap when performing your own Fillr integration.


### Prerequisites

Before getting started, please ensure you have the following:

1.  A Fillr API/Secret key pair.  This can be acquired from the [Fillr Developer Portal](https://developer.fillr.com/users/sign_up).
2.  An up-to-date copy of [Android Studio](https://developer.android.com/studio/index.html) with SDK Level 27 (Oreo 8.1) installed.
3.  A simulator [configured in AVD](https://developer.android.com/studio/run/managing-avds.html#createavd) or a native Android device to run the example on.


### Running the Example Project

1.  First check out a copy of the project source-code by running the command(s) from one of the sections below.

    * **Using Subversion:**
        ```bash
        svn co https://github.com/Fillr/android-example-integration.git
        ```
    
    * **Using Git:**
        ```bash
        git clone https://github.com/Fillr/android-example-integration.git
        ```
    
2.  Now open the project in Android Studio (using 'File -> Open...').  

3.  Open the `ExampleWebViewActivity` class (inside of the `com.fillr.example.integration.activity` package) and find the lines near the top of the file that say:

    ```java
    //TODO:  place your Fillr development key and secret values here
    //       you can acquire a key/secret pair at https://developer.fillr.com
    private static final String FILLR_KEY = "YOUR_FILLR_DEVELOPER_KEY";
    private static final String FILLR_SECRET = "YOUR_FILLR_SECRET_KEY";
    ```
    
    Insert ther API/Secret key pair you got from the [Fillr Developer Portal](https://developer.fillr.com/users/sign_up) here and save the file.

4.  Now select 'Run -> Run app' in Android Studio to launch the app on the Android Simulator or your native Android device.  

5.  You'll see a screen that looks like:

    ![Fillr Android Example Screenshot](https://github.com/Fillr/android-example-integration.git/raw/master/images/demo_app_screenshot.png)

    Congrats, the Fillr Example App is now running!


### Code Walkthrough - Setup

To set up your own Android project to use the Fillr Embedded SDK, you'll need to make the following configuration changes to your project:

1.  First update your module's `build.gradle` file to include the Fillr SDK.  Edit the `dependencies` section so that it includes:

    ```gradle
    //Fillr dependencies
    implementation(group: 'com.fillr', name: 'fillrcore', version: '4.5.0', ext: 'aar', classifier: 'fillrEmbeddedRelease') {
        transitive true
    }
    implementation(group: 'com.fillr', name: 'fillr-browser-sdk', version: '3.3.0', ext: 'aar', classifier: 'release')
    implementation(group: 'com.fillr', name: 'fillr-analytics', version: '1.2.0', ext: 'aar', classifier: 'release')
    ```
    
2.  Press the 'Sync Now' option that appears in the upper-right corner of Android Studio and wait for the Sync to complete.  This will download the Fillr SDK and add it to your project's classpath.


### Code Walkthrough - Implementation

Once you've completed the setup steps above you're ready to start integrating Fillr into your application's code!  Follow the steps below for a quick and easy integratrion:

1.  In your app's root activity, make the following changes:

    * Add a `Fillr` instance variable:
        ```java
        private Fillr fillr;  //or 'mFillr' if you like variables that start with 'm'
        ```
    * Instantiate and initialise Fillr:
        ```java
        //Fillr autofill setup
        fillr = Fillr.getInstance();
        fillr.initialise("Your Fillr Key", "Your Fillr Secret", this, Fillr.BROWSER_TYPE.WEB_KIT);
        ```
        
    * Give Fillr a reference to your `WebView`:
        ```java
        //Note that this must be called prior to calling loadUrl() on the WebView.
        //If you have multiple WebView instances, repeat this step for each one.
        fillr.trackWebView(yourWebView);
        ```
    * Handle successful fills from Fillr in `onActivityResult()`:
        ```java
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
            if (requestCode == Fillr.FILLR_REQUEST_CODE && resultCode == RESULT_OK) {
                //the Fillr SDK Activity completed an autofill; pass the result along to poulate the WebView
                fillr.processForm(intent);
            }
        }
        ```
    * Notify Fillr of `onResume()` events:
        ```java
        @Override
        protected void onResume() {
            super.onResume();
            fillr.onResume();
        }
        ```
    * **Optional** - Expose Fillr's Settings UI:
        ```java
        //call this from wherever is appropriate for your application, such as via a `MenuItem` 
        //tap, from within your own Settings UI, etc.
        startActivity(new Intent(this, FEMainActivity.class));
        ```
        
2.  Display a Fillr Toolbar.  There are different ways to accomplish this depending upon your requirements and the desired UX.  For a successful integration, you'll want to complete (at least) one of the following:

    * **Option 1** - Add a `FillrToolbarView` to the layout that includes your `WebView` by including the following in the layout's `xml`:
        ```xml
        <!-- Fillr toolbar, will show/hide automatically as appropriate -->
        <com.fillr.browsersdk.FillrToolbarView
            android:layout_marginBottom="0dp"
            android:id="@+id/fillrtoolbarview"
            android:layout_width="fill_parent"
            android:layout_height="50dp"
            android:layout_alignParentBottom="true"
            android:visibility="invisible"/>
        ```
        
    * **Option 2** - Add a `FillrAutofillButton` to the application's `ActionBar`:
    
        1.  Create a new layout file (e.g. `action_bar_content.xml`) with content like the following:
            ```xml
            <?xml version="1.0" encoding="utf-8"?>
            <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
                android:orientation="horizontal"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:gravity="right">

                <!-- Fillr ActionBar item, will show/hide automatically when managed by Fillr -->
                <com.fillr.browsersdk.FillrAutofillButton
                    android:id="@+id/autofill_button"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_centerHorizontal="true"
                    android:layout_centerVertical="true"
                    android:textColor="@android:color/white"
                    android:text="Autofill"/>
            </LinearLayout>
            ```
            
        2.  In your activity's `onCreate()` method, add the Fillr button to the `ActionBar`:
            ```java
            //here we assume the above layout has been saved as 'action_bar_content.xml', if you chose 
            //a different filename, adjust this line accordingly
            ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.action_bar_content, null);
            
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setCustomView(actionBarLayout);
            //adjust other ActionBar settings to your liking, if needed
            ```
            
        3.  Also in your activity's `onCreate()` method, hand the `FillrAutofillButton` over to Fillr:
            ```java
            //this lets Fillr manage the button; you don't need to worry about anything else now!
            FillrAutofillButton fillrButton = actionBarLayout.findViewById(R.id.autofill_button);
            fillr.setAutofillButton(fillrButton);
            ```
            
    * **Option 3** - Use your own custom components to request an autofill by calling the `triggerFill()` API at the appropriate time:
        ```java
        yourCustomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //here 'yourWebView' is the WebView instance that you want Fillr to fill
                fillr.triggerFill(yourWebView);
            }
        });
        ```


### Further Resources

If you require additional help getting your Fillr integration up and running, please see the [in-depth integration guide](http://fillr.github.io/docs/sdk/browser/android/embedded).