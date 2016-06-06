package com.free.rxjoker.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by liyaxing on 2016/5/23.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Host {

    String publish() ;

    String test() default "";

    boolean isDebug() default false ;
}
