package com.free.rxjoker.model;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by liyaxing on 2016/6/5.
 */
public class LogInterceptor implements Interceptor {

    public static LogInterceptor INSTANCE = new LogInterceptor() ;

    private LogInterceptor(){}

    @Override
    public Response intercept(Chain chain) throws IOException {
        String requestUrl = chain.request().url().url().toString() ;
        Log.d("LogInterceptor","[intercept] requestUrl :" + requestUrl) ;
        return chain.proceed(chain.request());
    }
}
