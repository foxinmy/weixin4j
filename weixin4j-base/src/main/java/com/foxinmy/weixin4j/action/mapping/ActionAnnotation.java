package com.foxinmy.weixin4j.action.mapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.foxinmy.weixin4j.type.EventType;
import com.foxinmy.weixin4j.type.MessageType;

/**
 * 标注Action类来处理消息请求
 * @className Action
 * @author jy
 * @date 2014年10月12日
 * @since JDK 1.7
 * @see
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionAnnotation {

	MessageType msgType();

	EventType[] eventType() default {};
}
