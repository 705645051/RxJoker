package com.free.rxjoker.model;

import com.free.rxjoker.been.GifBeen;
import com.free.rxjoker.been.TextBeen;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by liyaxing on 2016/6/4.
 */
@Host(
        publish = Config.JOKER_HOST
)
public interface JokerApi {

    @GET("/joke/content/text.from")
    Observable<TextBeen.Wrapper> getTextList(@Query("key") String key,
                                             @Query("pagesize") int pagesize) ;

    @GET("/joke/img/text.from")
    Observable<GifBeen.Wrapper> getGifList(@Query("key") String key,
                                           @Query("pagesize") int pagesize) ;

}
