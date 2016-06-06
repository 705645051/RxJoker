package com.free.rxjoker.been;


import java.util.List;

/**
 * Created by liyaxing on 2016/6/4.
 */
public class TextBeen {
    /*
    "content":"刚考完数学，我跟同桌讨论起来。\r\n我说：“这次考试最后两个大题，和上次考试的那个问答题都有一个共同点。”\r\n同桌想想说：“是不是都先设一个未知数。”\r\n我说：“不是，共同点就是我都不会！”????","hashId":"b7a73ee621f2553450c067c21eee9cfc","unixtime":1465002831,"updatetime":"2016-06-04 09:13:51"
     */
    public String content ;
    public String hashId ;
    public String unixtime ;
    public String updatetime ;

    public static class Wrapper extends JokerWrapper{
        public Result result ;
        public class Result{
            public List<TextBeen> data ;
        }
    }


}
