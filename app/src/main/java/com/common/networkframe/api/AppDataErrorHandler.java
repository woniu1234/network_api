package com.common.networkframe.api;


import android.text.TextUtils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;

/**
 * @author lst
 * @Description: AppDataErrorHandler.java(应用数据的错误会抛RuntimeException ；)
 * @date 2021/6/21
 */
public class AppDataErrorHandler implements Function<BaseResponse, BaseResponse> {

    @Override
    public BaseResponse apply(BaseResponse response) {
        //response中code码不会0 出现错误
        if (response != null && response.getCode() != 0) {
            Observable.just(response.getCode()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Integer>() {
                @Override
                public void accept(Integer code) throws Throwable {

                }
            });
            throw new RuntimeException(response.getCode() + "#" + (!TextUtils.isEmpty(response.getErrorMsg()) ? response.getErrorMsg() : "请求错误"));
        }
        return response;
    }
}