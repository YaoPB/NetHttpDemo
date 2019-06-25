package com.yaopb.networkimplemtationdemo;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSON;
import com.yaopb.nethttpmanager.listener.IJsonTransforListener;
import com.yaopb.nethttpmanager.NetHttp;
import com.yaopb.nethttpmanager.model.ResponseClass;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * yaopengbin
 * demo
 */
public class MainActivity extends AppCompatActivity {

    private String url = "http://172.23.56.235:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String[] permission = new String[]{Manifest.permission.INTERNET};

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permission, 1);
        } else {
            findViewById(R.id.request).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NetHttp.sendJsonRequest(null, url, ResponseClass.class, new IJsonTransforListener() {
                        @Override
                        public void onSuccuess(Object o) {
                            Log.e("--->", JSON.toJSONString(o));
                        }
                    });
                }
            });
        }


    }
}
