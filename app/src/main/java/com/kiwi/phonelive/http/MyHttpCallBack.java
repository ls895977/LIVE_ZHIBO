package com.kiwi.phonelive.http;

public interface MyHttpCallBack {
    /**
     * }
     *
     * @param flag    与网络请求的入参flag对应。表述一个网络的请求与回调
     * @param message 服务器返回的内容
     */
    void onHttpSuccess(int flag, String message);

    /**
     * @param flag          与网络请求的入参flag对应。表述一个网络的请求与回调
     * @param error_message 服务器返回的错误信息
     */
    void onHttpFail(int flag, String error_message);
}
