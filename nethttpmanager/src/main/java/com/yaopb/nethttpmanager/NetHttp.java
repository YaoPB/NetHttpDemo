package com.yaopb.nethttpmanager;

import com.yaopb.nethttpmanager.listener.CallbackListener;
import com.yaopb.nethttpmanager.listener.IJsonTransforListener;
import com.yaopb.nethttpmanager.listener.JsonCallBackListener;
import com.yaopb.nethttpmanager.request.HttpTask;
import com.yaopb.nethttpmanager.request.IHttpRequest;
import com.yaopb.nethttpmanager.request.JsonHttpRequest;
import com.yaopb.nethttpmanager.request.ThreadPoolManager;

public class NetHttp<T, M> {
    public static <T, M> void sendJsonRequest(T requestData, String url, Class<M> resoponse, IJsonTransforListener listener) {
        IHttpRequest httpRequest = new JsonHttpRequest();
        CallbackListener callbackListener = new JsonCallBackListener<>(resoponse, listener);
        HttpTask ht = new HttpTask(url, requestData, httpRequest, callbackListener);
        try {
            ThreadPoolManager.getInstance().addTask(ht);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
