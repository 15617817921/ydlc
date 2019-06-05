package com.liu.mypop.net;

import java.io.InputStream;

public interface CallBackListener {
    void onSuccess(InputStream inputStream);
    void onFailure( );
}
