package com.simplite.orm;

import android.app.Application;

public class SimpLiteApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SimpliteContext.setContext(this);
    }
}
