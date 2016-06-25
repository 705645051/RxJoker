package com.free.rxjoker.model;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by liyaxing on 2016/6/5.
 */
public class MainInterceptor implements Interceptor {

    public static final String TAG = "LogInterceptor" ;

    public static MainInterceptor INSTANCE = new MainInterceptor() ;

    private MainInterceptor(){}

    @Override
    public Response intercept(Chain chain) throws IOException {
        String requestUrl = chain.request().url().url().toString() ;
        Log.d("LogInterceptor","[intercept] requestUrl :" + requestUrl) ;
        Response response = null;
        try {
            response = chain.proceed(chain.request());
            if(!response.isSuccessful()){
                RetrofitApiFactory.initOkHttpClient();
            }
        } catch (IOException e) {
            RetrofitApiFactory.initOkHttpClient();
            throw new IOException(e) ;
        }
        return response;
    }
}
