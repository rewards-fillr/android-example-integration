package com.fillr.example.integration.activity;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.fillr.FillrApplication;


public class FillrTestApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Step 1 - Initialize Fillr Application
        FillrApplication app = FillrApplication.getInstance();
        app.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
