package com.simplite.orm.interfaces;

import android.content.Context;

public interface SimpLiteConfiguration {
    void beforeOnCreate(Context context);
    void afterOnCreate(Context context);
    void beforeOnUpgrade(Context context);
    void afterOnUpgrade(Context context);
}
