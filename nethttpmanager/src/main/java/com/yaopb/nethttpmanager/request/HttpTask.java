package com.yaopb.nethttpmanager.request;

import com.alibaba.fastjson.JSON;
import com.yaopb.nethttpmanager.listener.CallbackListener;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class HttpTask<T> implements Runnable, Delayed {

    public IHttpRequest httpRequest;

    public HttpTask(String url, T requestData, IHttpRequest httpRequest, CallbackListener listener) {
        this.httpRequest = httpRequest;
        httpRequest.setUrl(url);
        httpRequest.setListener(listener);
        String content = JSON.toJSONString(requestData);
        try {
            httpRequest.setData(content.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            this.httpRequest.execute();
        } catch (Exception ex) {
            ThreadPoolManager.getInstance().addDelayTask(this);
        }
    }

    public IHttpRequest getHttpRequest() {
        return httpRequest;
    }

    public void setHttpRequest(IHttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = System.currentTimeMillis() + delayTime;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    private long delayTime;
    private int retryCount;

    @Override
    public long getDelay(TimeUnit timeUnit) {
        return timeUnit.convert(this.delayTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed delayed) {
        return 0;
    }
}
