package com.astapley.buster;

import android.app.Application;

import timber.log.Timber;
import timber.log.Timber.DebugTree;

public class BusterApplication extends Application {

    @Override public void onCreate() {
        super.onCreate();

        Timber.plant(new DebugTree());
    }
}
