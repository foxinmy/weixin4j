package com.foxinmy.weixin4j.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.model.Consts;

/**
 * 对class的获取
 * 
 * @className ClassUtil
 * @author jy
 * @date 2014年10月31日
 * @since JDK 1.7
 * @see
 */
public class ClassUtil {

	/**
	 * 获取某个包下所有的class信息
	 * 
	 * @param _package 包对象
	 * @return
	 */
	public static Set<Class<?>> getClasses(Package _package) {
		String packageName = _package.getName();
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
				;
			}
		}
		return null;
	}

	/**
	 * 实例化目录下所有的class对象
	 * 
	 * @param dir 文件目录
	 * @param packageName 包的全限类名
	 * @return
	 */
	private static Set<Class<?>> findClassesByFile(File dir, String packageName) {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File file, String name) {
				return file.isDirectory() || file.getName().endsWith(".class");
			}
		});
		for (File file : files) {
			if (file.isDirectory()) {
				classes.addAll(findClassesByFile(file,
						packageName + "." + file.getName()));
			} else {
				try {
					Class<?> clazz = Class.forName(packageName + "."
							+ file.getName().replace(".class", ""));
					if (clazz.isInterface()) {
						continue;
					}
					classes.add(clazz);
				} catch (ClassNotFoundException e) {
					;
				}
			}
		}
		return classes;
	}

	/**
	 * 实例化jar包下所有的class对象
	 * 
	 * @param jar jar包对象
	 * @param packageName 包的全限类名
	 * @return
	 */
	private static Set<Class<?>> findClassesByJar(JarFile jar,
			String packageName) {
		Set<Class<?>> classes = new HashSet<Class<?>>();
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
				Class<?> clazz = Class.forName(entryName.replaceAll("/", ".")
						.replace(".class", ""));
				if (clazz.isInterface()) {
					continue;
				}
				classes.add(clazz);
			} catch (ClassNotFoundException e) {
				;
			}
		}
		return classes;
	}

	public static void main(String[] args) {
		Package _package = JSON.class.getPackage();
		System.out.println(getClasses(_package));
	}
}
