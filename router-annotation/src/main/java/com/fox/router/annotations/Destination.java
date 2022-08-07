package com.fox.router.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author Silver-Fox
 * @Date 2022/8/6 23:44
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Destination {
    //当前页面的url 不能为空
    String url();

    //页面描述
    String description();
}
