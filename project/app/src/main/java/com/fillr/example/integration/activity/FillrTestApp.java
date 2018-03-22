package com.fillr.example.integration.activity;

import android.app.Application;

import com.fillr.FillrApplication;


public class FillrTestApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        FillrApplication init = FillrApplication.getInstance(this);
        init.init();
    }

}
