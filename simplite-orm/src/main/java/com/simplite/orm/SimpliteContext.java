package com.simplite.orm;

import android.content.Context;

public class SimpliteContext {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        SimpliteContext.context = context;
    }
}
