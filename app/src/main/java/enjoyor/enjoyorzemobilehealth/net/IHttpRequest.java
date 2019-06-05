package com.liu.mypop.net;

public interface IHttpRequest {
    void setUrl(String url);

    void setData(byte[] data);
    void setListener(CallBackListener  listener);
    void exexute();
}
