package com.liu.mypop.net;

import android.support.annotation.NonNull;
import android.util.TimeUtils;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class HttpTask<T> implements Runnable, Delayed {
    private IHttpRequest mIHttpRequest;

    public HttpTask(String url, T requestData, IHttpRequest httpRequest, CallBackListener listener) {
        mIHttpRequest = httpRequest;
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
            mIHttpRequest.exexute();
        } catch (Exception e) {
            ThreadPoolManger.getInstance().addDelayTask(this);
        }
    }

    private long delayTime;
    private int retryConut;

    public long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = System.currentTimeMillis() + delayTime;
    }

    public int getRetryConut() {
        return retryConut;
    }

    public void setRetryConut(int retryConut) {
        this.retryConut = retryConut;
    }

    @Override
    public long getDelay(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(this.delayTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(@NonNull Delayed delayed) {
        return 0;
    }
}
