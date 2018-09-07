package com.android.bish.walmart;

import android.app.Activity;
import android.app.Application;

import com.android.bish.walmart.dagger.ContextModule;
import com.android.bish.walmart.dagger.DaggerWalmartComponent;
import com.android.bish.walmart.dagger.WalmartComponent;

import timber.log.Timber;

public class WalmartApplication extends Application {

    private WalmartComponent walmartApplicationComponent;

    public static WalmartApplication get(Activity activity) {
        return (WalmartApplication) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());

        walmartApplicationComponent = DaggerWalmartComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    public WalmartComponent getWalmartApplicationComponent() {
        return walmartApplicationComponent;
    }

}
