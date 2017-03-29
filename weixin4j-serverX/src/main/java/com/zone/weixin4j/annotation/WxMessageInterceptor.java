package com.zone.weixin4j.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Created by Yz on 2017/3/13.
 * WxMessageInterceptor
 * WxMessageInterceptor  注解声明拦截消息内容的类
 */
@Target({java.lang.annotation.ElementType.TYPE})
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface WxMessageInterceptor {
}
