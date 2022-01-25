package com.framework.network_api.interceptor;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author lst
 * @Description: ResponseInterceptor.java(类描述)
 * @date 2021/6/22
 */
public class ResponseInterceptor implements Interceptor {

    public ResponseInterceptor() {
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String rawJson = response.body() == null ? "" : response.body().string();
        return response.newBuilder().body(ResponseBody.Companion.create(rawJson, response.body().contentType())).build();
    }
}
