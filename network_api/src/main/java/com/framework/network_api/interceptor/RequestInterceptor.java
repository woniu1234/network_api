package com.framework.network_api.interceptor;

import android.text.TextUtils;


import com.framework.network_api.INetworkRequestInfo;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author lst
 * @Description: RequestInterceptor.java(类描述)
 * @date 2021/6/21
 */
public class RequestInterceptor implements Interceptor {
    private final INetworkRequestInfo mNetworkRequestInfo;

    public RequestInterceptor(INetworkRequestInfo networkRequestInfo) {
        this.mNetworkRequestInfo = networkRequestInfo;
    }

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request()
                .newBuilder();
        if (mNetworkRequestInfo != null && mNetworkRequestInfo.getRequestHeaderMap() != null) {
            for (String key : mNetworkRequestInfo.getRequestHeaderMap().keySet()) {
                if (!TextUtils.isEmpty(mNetworkRequestInfo.getRequestHeaderMap().get(key))) {
                    try {
                        builder.addHeader(key, mNetworkRequestInfo.getRequestHeaderMap().get(key));
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return chain.proceed(builder.build());
    }
}