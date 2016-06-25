package com.free.rxjoker.model;


import android.util.Log;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by liyaxing on 2016/5/30.
 */
public class StringResponseConverterFactory extends Converter.Factory  {

    private static StringResponseConverterFactory INSTANCE = new StringResponseConverterFactory() ;

    private StringResponseConverter converter  = new StringResponseConverter() ;

    private StringResponseConverterFactory(){}

    public static StringResponseConverterFactory create(){return  INSTANCE;}

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        if(type != null && type == String.class) {
            return converter;
        } else {
            return null;
        }
    }

    private class StringResponseConverter implements Converter<ResponseBody, String>{
        @Override
        public String convert(ResponseBody value) throws IOException {
            String sValue = value.string();
            Log.d(MainInterceptor.TAG,"[intercept] response : " + sValue) ;
            return sValue;
        }
    }
}
