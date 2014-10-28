package com.foxinmy.weixin4j.mp.mapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.foxinmy.weixin4j.type.EventType;
import com.foxinmy.weixin4j.type.MessageType;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
/**
 * 标注
 * @className Action
 * @author jy
 * @date 2014年10月12日
 * @since JDK 1.7
 * @see
 */
public @interface Action {

	MessageType msgType();

	EventType[] eventType() default {};
}
