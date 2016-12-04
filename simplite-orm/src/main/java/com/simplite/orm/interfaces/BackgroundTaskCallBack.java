package com.simplite.orm.interfaces;

import java.util.List;

public interface BackgroundTaskCallBack {
    void onSuccess(String result, List<Object> data);

    void onError(String error);
}
