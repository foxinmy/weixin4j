package com.foxinmy.weixin4j.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 对class的获取
 * 
 * @className ClassUtil
 * @author jy
 * @date 2014年10月31日
 * @since JDK 1.7
 * @see
 */
public final class ClassUtil {

	/**
	 * 获取某个包下所有的class信息
	 * 
	 * @param packageName
	 *            包名
	 * @return
	 */
	public static List<Class<?>> getClasses(String packageName)
			throws RuntimeException {
		String packageFileName = packageName.replace(".", File.separator);
		URL fullPath = Thread.currentThread().getContextClassLoader()
				.getResource(packageFileName);
		String protocol = fullPath.getProtocol();
		if (protocol.equals(Consts.PROTOCOL_FILE)) {
			File dir = new File(fullPath.getPath());
			return findClassesByFile(dir, packageName);
		} else if (protocol.equals(Consts.PROTOCOL_JAR)) {
			try {
				return findClassesByJar(
						((JarURLConnection) fullPath.openConnection())
								.getJarFile(),
						packageFileName);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return null;
	}

	/**
	 * 扫描目录下所有的class对象
	 * 
	 * @param dir
	 *            文件目录
	 * @param packageName
	 *            包的全限类名
	 * @return
	 */
	private static List<Class<?>> findClassesByFile(File dir, String packageName) {
		List<Class<?>> classes = new LinkedList<Class<?>>();
		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File file, String name) {
				return file.isDirectory() || file.getName().endsWith(".class");
			}
		});
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					classes.addAll(findClassesByFile(file, packageName + "."
							+ file.getName()));
				} else {
					try {
						classes.add(Class.forName(packageName + "."
								+ file.getName().replace(".class", "")));
					} catch (ClassNotFoundException e) {
						;
					}
				}
			}
		}
		return classes;
	}

	/**
	 * 扫描jar包下所有的class对象
	 * 
	 * @param jar
	 *            jar包对象
	 * @param packageName
	 *            包的全限类名
	 * @return
	 */
	private static List<Class<?>> findClassesByJar(JarFile jar,
			String packageName) {
		List<Class<?>> classes = new LinkedList<Class<?>>();
		Enumeration<JarEntry> jarEntries = jar.entries();
		while (jarEntries.hasMoreElements()) {
			JarEntry jarEntry = jarEntries.nextElement();
			if (jarEntry.isDirectory()) {
				continue;
			}
			String entryName = jarEntry.getName();
			if (!entryName.startsWith(packageName)) {
				continue;
			}
			if (!entryName.endsWith(".class")) {
				continue;
			}
			try {
				classes.add(Class.forName(entryName.replaceAll("/", ".")
						.replace(".class", "")));
			} catch (ClassNotFoundException e) {
				;
			}
		}
		return classes;
	}

	public static Object deepClone(Object obj) throws RuntimeException {
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			bis = new ByteArrayInputStream(bos.toByteArray());
			ois = new ObjectInputStream(bis);
			return ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (bos != null) {
					bos.close();
				}
				if (oos != null) {
					oos.close();
				}
				if (bis != null) {
					bis.close();
				}
				if (ois != null) {
					ois.close();
				}
			} catch (IOException e) {
				;// ignore
			}
		}
	}

	/**
	 * 获得泛型类型
	 * 
	 * @param object
	 * @return
	 */
	public static Class<?> getGenericType(Object object) {
		Class<?> clazz = null;
		Type type = object.getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			ParameterizedType ptype = ((ParameterizedType) type);
			Type[] args = ptype.getActualTypeArguments();
			clazz = (Class<?>) args[0];
		}
		return clazz;
	}

	public static void main(String[] args) {
		System.err
				.println(getClasses(com.foxinmy.weixin4j.handler.WeixinMessageHandler.class
						.getPackage().getName()));
	}
}
