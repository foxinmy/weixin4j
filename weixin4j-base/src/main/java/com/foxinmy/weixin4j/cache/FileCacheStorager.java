package com.foxinmy.weixin4j.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.foxinmy.weixin4j.util.SerializationUtils;

/**
 * 用File保存缓存对象
 *
 * @className FileCacheStorager
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年5月27日
 * @since JDK 1.6
 * @see
 */
public class FileCacheStorager<T extends Cacheable> implements CacheStorager<T> {

	private final File tmpdir;
	private final String SEPARATOR = File.separator;

	/**
	 * 默认缓存路径：java.io.tmpdir
	 */
	public FileCacheStorager() {
		this(System.getProperty("java.io.tmpdir"));
	}

	/**
	 * 
	 * @param path
	 *            缓存文件报错
	 */
	public FileCacheStorager(String path) {
		this.tmpdir = new File(String.format("%s%s%s", path, SEPARATOR, ALLKEY));
		this.tmpdir.mkdirs();
	}

	@Override
	public T lookup(String key) {
		File cacheFile = new File(String.format("%s%s%s",
				tmpdir.getAbsolutePath(), SEPARATOR, key));
		try {
			if (cacheFile.exists()) {
				T cache = SerializationUtils.deserialize(new FileInputStream(
						cacheFile));
				if (cache.getCreateTime() < 0) {
					return cache;
				}
				if ((cache.getCreateTime() + cache.getExpires() - CUTMS) > System
						.currentTimeMillis()) {
					return cache;
				}
			}
			return null;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void caching(String key, T cache) {
		try {
			SerializationUtils.serialize(
					cache,
					new FileOutputStream(new File(String.format("%s%s%s",
							tmpdir.getAbsolutePath(), SEPARATOR, key))));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public T evict(String key) {
		T cache = null;
		File cacheFile = new File(String.format("%s%s%s",
				tmpdir.getAbsolutePath(), SEPARATOR, key));
		try {
			if (cacheFile.exists()) {
				cache = SerializationUtils.deserialize(new FileInputStream(
						cacheFile));
				cacheFile.delete();
			}
		} catch (IOException e) {
			; // ingore
		}
		return cache;
	}

	@Override
	public void clear() {
		for (File cache : tmpdir.listFiles()) {
			cache.delete();
		}
	}
}
