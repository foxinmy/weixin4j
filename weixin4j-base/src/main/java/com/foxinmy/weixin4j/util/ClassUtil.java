package com.foxinmy.weixin4j.util;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class ClassUtil {

	public static Set<Class<?>> getClasses(Package _package) {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		String subPath = _package.getName().replace(".", File.separator);
		URL fullPath = classLoader.getResource(subPath);
		File dir = new File(fullPath.getPath());
		return findClasses(dir, _package.getName());
	}

	private static Set<Class<?>> findClasses(File dir, String packageName) {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File file, String name) {
				return file.isDirectory() || file.getName().endsWith(".class");
			}
		});
		for (File file : files) {
			if (file.isDirectory()) {
				classes.addAll(findClasses(file,
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
}
