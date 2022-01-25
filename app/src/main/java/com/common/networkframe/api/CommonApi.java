package com.common.networkframe.api;

import com.framework.network_api.BaseApi;
import com.framework.network_api.errorhandler.HttpErrorHandler;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;

public class CommonApi extends BaseApi {

    @Override
    public String getBaseUrl() {
        return "https://mobile.cn-healthcare.com/";
    }

    @Override
    public ObservableTransformer getErrorFormer() {
        return new ErrorTransformer();
    }

    /**
     * 处理错误的变换
     * 网络请求的错误处理，其中网络错误分为两类：
     * 1、http请求相关的错误，例如：404，403，socket timeout等等；HttpErrorHandler
     * 2、http请求正常，但是返回的应用数据里提示发生了异常，表明服务器已经接收到了来自客户端的请求，但是由于
     * 某些原因，服务器没有正常处理完请求，可能是缺少参数，或者其他原因；AppDataErrorHandler
     */
    private static class ErrorTransformer implements ObservableTransformer {

        @Override
        public @NonNull ObservableSource apply(@NonNull Observable upstream) {
            return upstream
                    .map(new AppDataErrorHandler())/*返回的数据统一错误处理*/
                    .onErrorResumeNext(new HttpErrorHandler());/*Http 错误处理**/
        }
    }

}
