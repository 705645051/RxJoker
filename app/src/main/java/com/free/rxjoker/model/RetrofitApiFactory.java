package com.free.rxjoker.model;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by liyaxing on 2016/5/23.
 */
public class RetrofitApiFactory {

    private static final int TIME_OUT = 30;

    private static final ConcurrentHashMap<Class,Object> cacheMap
            = new ConcurrentHashMap<Class,Object>() ;

    private static OkHttpClient okHttpClient = null ;

    private static Gson gson = new GsonBuilder()
            .create() ;

    static{
        /*
         * 初始化OkHttpClient
         */
        okHttpClient =
                new OkHttpClient.Builder()
                        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                        .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                        .addInterceptor(LogInterceptor.INSTANCE)
                        .build();

    }

    public static <T> T createApi(Class<T> clz){

        Object cacheObj = cacheMap.get(clz) ;
        if(cacheObj != null){
            return (T) cacheObj;
        }

        String baseUrl = "" ;
        Host host = clz.getAnnotation(Host.class) ;
        if(host == null){
            throw new IllegalArgumentException("请在" +clz.getSimpleName()+"接口上添加host配置") ;
        }
        if(host.isDebug()){
            baseUrl = host.test() ;
        }

        if(TextUtils.isEmpty(baseUrl)){
            baseUrl = host.publish() ;
        }
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        T t = retrofit.create(clz) ;
        cacheMap.put(clz,t) ;
        return t ;
    }

    public static void clearCacheMap(){
        cacheMap.clear();
    }

}
