package com.yaopb.nethttpmanager.request;

import android.util.Log;

import com.yaopb.nethttpmanager.listener.CallbackListener;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonHttpRequest implements IHttpRequest {
    private String url;
    private byte[] data;
    private CallbackListener callbackListener;

    public String getUrl() {
        return url;
    }

    public byte[] getData() {
        return data;
    }

    public CallbackListener getCallbackListener() {
        return callbackListener;
    }

    public void setCallbackListener(CallbackListener callbackListener) {
        this.callbackListener = callbackListener;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public void setListener(CallbackListener listener) {
        this.callbackListener = listener;
    }

    private HttpURLConnection httpURLConnection;

    @Override
    public void execute() {
        try {
            //使用该地址创建一个 URL 对象
            Log.d("Url:", getUrl());
            URL url = new URL(getUrl());
            //使用创建的URL对象的openConnection()方法创建一个HttpURLConnection对象
            httpURLConnection = (HttpURLConnection) url.openConnection();
            /**
             * 设置HttpURLConnection对象的参数
             */
            // 设置请求方法为 GET 请求
            httpURLConnection.setRequestMethod("GET");
            //使用输入流
            httpURLConnection.setDoInput(true);
            //GET 方式，不需要使用输出流
            httpURLConnection.setDoOutput(true);
            //设置超时
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(1000);
            //连接
            httpURLConnection.connect();
            OutputStream out = httpURLConnection.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(out);
            bos.write(data);
            bos.flush();
            out.close();
            bos.close();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = httpURLConnection.getInputStream();
                callbackListener.onSuccess(in);
            } else {
                throw new RuntimeException("请求失败xsss");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
