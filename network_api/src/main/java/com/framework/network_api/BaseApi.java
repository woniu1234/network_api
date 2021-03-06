package com.framework.network_api;

import com.framework.network_api.interceptor.RequestInterceptor;
import com.framework.network_api.interceptor.ResponseInterceptor;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseApi {

    protected Retrofit retrofit;
    protected static INetworkRequestInfo networkRequestInfo;
    private static RequestInterceptor sHttpsRequestInterceptor;
    private static ResponseInterceptor responseInterceptor;

    public abstract String getBaseUrl();

    public abstract ObservableTransformer getErrorFormer();

    protected BaseApi() {
        retrofit = new Retrofit
                .Builder()
                .client(getOkHttpClient())
                .baseUrl(getBaseUrl())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    protected BaseApi(String baseUrl) {
        retrofit = new Retrofit
                .Builder()
                .client(getOkHttpClient())
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static void setNetworkRequestInfo(INetworkRequestInfo requestInfo) {
        networkRequestInfo = requestInfo;
        sHttpsRequestInterceptor = new RequestInterceptor(requestInfo);
        responseInterceptor = new ResponseInterceptor();
    }

    public OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS);

        /*??????????????????????????????????????????*/
        okHttpClient.addInterceptor(sHttpsRequestInterceptor);
        //????????????
        okHttpClient.addInterceptor(responseInterceptor);
        setLoggingLevel(okHttpClient);
        OkHttpClient httpClient = okHttpClient.build();
        httpClient.dispatcher().setMaxRequestsPerHost(20);
        return httpClient;
    }

    private void setLoggingLevel(OkHttpClient.Builder builder) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        //BODY????????????,NONE???????????????
        logging.setLevel(networkRequestInfo.isDebug() ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        builder.addInterceptor(logging);
    }

    /**
     * ?????????????????????????????????????????????????????????
     */
    protected void apiSubscribe(Observable observable, Observer observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(getErrorFormer())
                .subscribe(observer);
    }

    /**
     * ????????????????????????????????????,??????????????????
     */
    protected void apiSubscribeTx(Observable observable, Observer observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .compose(getErrorFormer())
                .subscribe(observer);
    }
}
