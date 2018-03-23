package com.fillr.example.integration.activity;

import android.app.Application;

import com.fillr.FillrApplication;


public class FillrTestApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Step 1 - Initialize Fillr Application
        FillrApplication app = FillrApplication.getInstance(this);
        app.init();
    }
}
