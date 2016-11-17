package com.foxinmy.weixin4j.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * @title 反射工具类
 * @description 提供对类,字段的反射调用
 * @author jinyu(foxinmy@gmail.com) , 2012-10-26
 */
public class ReflectionUtil {

	/**
	 * 获取包包名
	 * 
	 * @param obj
	 * @return
	 */
	public static String getPackageName(Object obj) {
		return obj.getClass().getPackage().getName();
	}

	/**
	 * 获取字段的泛型参数类型
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Class<?> getFieldGenericType(Object obj, String fieldName) {
		Field field = getAccessibleField(obj, fieldName);
		Type type = field.getGenericType();
		if (type instanceof ParameterizedType) {
			return (Class<?>) ((ParameterizedType) type)
					.getActualTypeArguments()[0];
		}
		return null;
	}

	/**
	 * 调用方法
	 * 
	 * @param object
	 *            对象
	 * 
	 * @param propertyName
	 *            属性名称
	 */
	public static Object invokeMethod(Object object, String propertyName) {
		try {
			Method getterMethod = object.getClass().getMethod(propertyName);
			return getterMethod.invoke(object);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Object invokeMethod(Object object, String propertyName,
			Object... args) {
		try {
			Method getterMethod = object.getClass().getMethod(propertyName);
			return getterMethod.invoke(object, args);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 调用Getter方法
	 * 
	 * @param object
	 *            对象
	 * 
	 * @param propertyName
	 *            属性名称
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Object invokeGetterMethod(Object object, String propertyName)
			throws Exception {
		String getterMethodName = null;
		Method getterMethod = null;
		String propertyNa = null;
		if (propertyName.contains(".")) {
			propertyNa = StringUtil.substringBefore(propertyName, ".");
			getterMethodName = "get" + StringUtil.capitalize(propertyNa);
			getterMethod = object.getClass().getMethod(getterMethodName);
			return invokeGetterMethod(getterMethod.invoke(object),
					StringUtil.substringAfter(propertyName, "."));
		} else {
			getterMethodName = "get" + StringUtil.capitalize(propertyName);
			getterMethod = object.getClass().getMethod(getterMethodName);
			return getterMethod.invoke(object);
		}
	}

	/**
	 * 调用Setter方法
	 * 
	 * @param object
	 *            对象
	 * 
	 * @param propertyName
	 *            属性名称
	 * 
	 * @param propertyValue
	 *            属性值
	 */
	public static void invokeSetterMethod(Object object, String propertyName,
			Object propertyValue) {
		Class<?> setterMethodClass = propertyValue.getClass();
		invokeSetterMethod(object, propertyName, propertyValue,
				setterMethodClass);
	}

	/**
	 * 调用Setter方法
	 * 
	 * @param object
	 *            对象
	 * 
	 * @param propertyName
	 *            属性名称
	 * 
	 * @param propertyValue
	 *            属性值
	 * 
	 * @param setterMethodClass
	 *            参数类型
	 */
	public static void invokeSetterMethod(Object object, String propertyName,
			Object propertyValue, Class<?> setterMethodClass) {
		String setterMethodName = "set" + StringUtil.capitalize(propertyName);
		try {
			Method setterMethod = object.getClass().getMethod(setterMethodName,
					setterMethodClass);
			setterMethod.invoke(object, propertyValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取对象属性值,无视private/protected/getter
	 * 
	 * @param object
	 *            对象
	 * 
	 * @param fieldName
	 *            属性名称
	 */
	public static Object getFieldValue(Object object, String fieldName) {
		Field field = getAccessibleField(object, fieldName);
		if (field == null) {
			throw new IllegalArgumentException("Could not find field "
					+ fieldName);
		}
		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {

		}
		return result;
	}

	/**
	 * 设置对象属性值,无视private/protected/setter
	 * 
	 * @param object
	 *            对象
	 * 
	 * @param fieldName
	 *            属性名称
	 */
	public static void setFieldValue(Object object, String fieldName,
			Object value) {
		Field field = getAccessibleField(object, fieldName);
		if (field == null) {
			throw new IllegalArgumentException("Could not find field "
					+ fieldName);
		}
		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {

		}
	}

	// 获取字段的类型
	public static String getFieldType(Object object, String fieldName) {
		Field field = getAccessibleField(object, fieldName);
		return field.getType().getSimpleName();
	}

	public static Field getAccessibleField(final Object object,
			final String fieldName) {
		for (Class<?> superClass = object.getClass(); superClass != Object.class;) {
			try {
				Field field = superClass.getDeclaredField(fieldName);
				field.setAccessible(true);
				return field;
			} catch (NoSuchFieldException e) {
				return null;
			}
		}
		return null;
	}

	public static Set<Field> getAllField(Class<?> clazz) {
		Set<Field> fieldSet = new HashSet<Field>();
		while (clazz != Object.class) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				int modifier = field.getModifiers();
				if (Modifier.isFinal(modifier) || Modifier.isStatic(modifier)) {
					continue;
				}
				fieldSet.add(field);
			}
			clazz = clazz.getSuperclass();
		}
		return fieldSet;
	}
}
