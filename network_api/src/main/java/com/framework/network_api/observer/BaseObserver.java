package com.framework.network_api.observer;

import android.util.Log;

import com.common.frame.model.SuperBaseModel;
import com.framework.network_api.errorhandler.ExceptionHandle;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;


/**
 * @author lst
 * @Description: BaseObserver.java(类描述)
 * @date 2021/6/21
 */
public abstract class BaseObserver<T> implements Observer<T> {
    private SuperBaseModel baseModel;

    public BaseObserver() {
    }

    public BaseObserver(SuperBaseModel baseModel) {
        this.baseModel = baseModel;
    }

    @Override
    public void onError(Throwable e) {
        Log.e("onError", e.getMessage());
        if (e instanceof ExceptionHandle.ResponseThrowable) {
            onError((ExceptionHandle.ResponseThrowable) e);
        } else {
            onError(new ExceptionHandle.ResponseThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
    }

    @Override
    public void onSubscribe(@NotNull Disposable d) {
        if (baseModel != null) {
            baseModel.addDisposable(d);
        }
    }


    @Override
    public void onComplete() {
    }


    public abstract void onError(ExceptionHandle.ResponseThrowable e);

}
