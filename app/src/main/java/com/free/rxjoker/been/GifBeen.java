package com.free.rxjoker.been;

import java.util.List;

/**
 * Created by liyaxing on 2016/6/5.
 */
public class GifBeen {

    public String content ;
    public String hashId ;
    public String unixtime ;
    public String updatetime ;
    public String url ;

    public static class Wrapper extends JokerWrapper{
        public Result result ;
        public class Result{
            public List<GifBeen> data ;
        }
    }

}
