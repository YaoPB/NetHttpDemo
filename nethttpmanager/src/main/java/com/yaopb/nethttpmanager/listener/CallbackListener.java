package com.yaopb.nethttpmanager.listener;

import java.io.InputStream;

public interface CallbackListener {
    void onSuccess(InputStream inputStream);

    void onFailed();
}
