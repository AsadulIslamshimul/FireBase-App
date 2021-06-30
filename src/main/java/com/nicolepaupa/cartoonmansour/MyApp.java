package com.nicolepaupa.cartoonmansour;

import android.app.Application;

import com.facebook.ads.AudienceNetworkAds;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp

public final class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the Audience Network SDK
        AudienceNetworkAds.initialize(this);
    }
}
