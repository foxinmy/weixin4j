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

	public FileCacheStorager(String cachePath) {
		this.tmpdir = new File(String.format("%s%sweixin4j_token_temp",
				cachePath, SEPARATOR));
		this.tmpdir.mkdirs();
	}

	@Override
	public T lookup(String cacheKey) {
		File cacheFile = new File(String.format("%s%s%s",
				tmpdir.getAbsolutePath(), SEPARATOR, cacheKey));
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
	public void caching(String cacheKey, T cache) {
		try {
			SerializationUtils.serialize(
					cache,
					new FileOutputStream(new File(String.format("%s%s%s",
							tmpdir.getAbsolutePath(), SEPARATOR, cacheKey))));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public T evict(String cacheKey) {
		T cache = null;
		File cacheFile = new File(String.format("%s%s%s",
				tmpdir.getAbsolutePath(), SEPARATOR, cacheKey));
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
