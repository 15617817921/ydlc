package com.liu.mypop.net;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.LogRecord;

public class JsonCallBackListener<T> implements CallBackListener {
    private Class<T> responseClass;
    private IJsonDataListener listener;
    private Handler mHandler= new Handler(Looper.getMainLooper()) ;

    public JsonCallBackListener(Class<T> responseClass,IJsonDataListener listener) {
        this.responseClass = responseClass;
        this.listener = listener;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        String response = getContent(inputStream);
        final T clazz= JSON.parseObject(response, responseClass);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                listener.onSuccess(clazz);

            }
        });
    }

    private String getContent(InputStream inputStream) {
        String content = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                Log.e("IOException", e.toString());
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e("IOException", e.toString());
                }
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return content;
    }

    @Override
    public void onFailure() {

    }
}
