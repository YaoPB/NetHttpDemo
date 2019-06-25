package com.yaopb.nethttpmanager.request;

import com.yaopb.nethttpmanager.listener.CallbackListener;

public interface IHttpRequest {
    void setUrl(String url);

    void setData(byte[] data);

    void setListener(CallbackListener listener);

    void execute();
}
