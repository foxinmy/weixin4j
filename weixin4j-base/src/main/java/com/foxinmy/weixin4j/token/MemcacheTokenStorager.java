package com.foxinmy.weixin4j.token;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.foxinmy.weixin4j.model.Token;
import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

/**
 * 用Memcache保存Token信息(推荐使用)
 *
 * @className MemcacheTokenStorager
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年5月11日
 * @since JDK 1.6
 * @see
 */
public class MemcacheTokenStorager implements TokenStorager {

	private final MemCachedClient mc;

	public MemcacheTokenStorager(MemcachePoolConfig poolConfig) {
		mc = new MemCachedClient();
		poolConfig.initSocketIO();
	}

	@Override
	public Token lookup(String cacheKey) {
		return (Token) mc.get(cacheKey);
	}

	@Override
	public void caching(String cacheKey, Token token) {
		if (token.getExpiresIn() > 0) {
			mc.set(cacheKey, token,
					new Date(token.getCreateTime() + token.getExpiresIn()
							* 1000 - CUTMS));
		} else {
			mc.set(cacheKey, token);
		}
	}

	@Override
	public Token evict(String cacheKey) {
		Token token = lookup(cacheKey);
		mc.delete(cacheKey);
		return token;
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	public static class MemcachePoolConfig {
		public final static String HOST = "localhost";
		public final static int PORT = 11211;
		public final static int WEIGHT = 1;

		public static int minConn = 5;
		public static int initConn = 5;
		public static int maxConn = 100;
		public static int maxIdle = 300000;
		public static long maxBusyTime = 30000L;
		public static int socketTO = 3000;
		public static int socketConnectTO = 3000;
		public static boolean failover = true;
		public static boolean failback = true;
		public static boolean nagle = false;
		public static boolean aliveCheck = false;
		public static final int consistentHash = 3;
		public static final int mainSleep = 30000;

		private static SockIOPool pool;
		static {
			pool = SockIOPool.getInstance();
			pool.setFailback(failback);
			pool.setFailover(failover);
			pool.setMaxBusyTime(maxBusyTime);
			pool.setMaxConn(maxConn);
			pool.setMaxIdle(maxIdle);
			pool.setMinConn(minConn);
			pool.setNagle(nagle);
			pool.setSocketConnectTO(socketConnectTO);
			pool.setSocketTO(socketTO);
			pool.setAliveCheck(aliveCheck);
			pool.setHashingAlg(consistentHash);
			pool.setInitConn(initConn);
			pool.setMaintSleep(maxBusyTime);
		}

		private List<MemcacheConfig> configs;
		private String[] servers;
		private Integer[] weights;

		/**
		 * {localhost:11211,1}
		 */
		public MemcachePoolConfig() {
			this(HOST, PORT, WEIGHT);
		}

		/**
		 * {host:11211,1}
		 *
		 * @param host
		 *            主机
		 */
		public MemcachePoolConfig(String host) {
			this(host, PORT);
		}

		/**
		 * {host:port,1}
		 *
		 * @param host
		 *            主机
		 * @param port
		 *            端口
		 */
		public MemcachePoolConfig(String host, int port) {
			this(host, port, WEIGHT);
		}

		/**
		 * {host:port,weight}
		 *
		 * @param host
		 *            主机
		 * @param port
		 *            端口
		 * @param weight
		 *            权重
		 */
		public MemcachePoolConfig(String host, int port, int weight) {
			configs = new ArrayList<MemcacheConfig>();
			configs.add(new MemcacheConfig(host, port, weight));
		}

		public MemcachePoolConfig addServer(String host) {
			return addServer(host, PORT, WEIGHT);
		}

		public MemcachePoolConfig addServer(String host, int port) {
			return addServer(host, port, WEIGHT);
		}

		public MemcachePoolConfig addServer(String host, int port, int weight) {
			configs.add(new MemcacheConfig(host, port, weight));
			return this;
		}

		private void initConfig() {
			if (servers == null || weights == null) {
				servers = new String[configs.size()];
				weights = new Integer[configs.size()];
				for (int i = 0; i < configs.size(); i++) {
					servers[i] = configs.get(i).getServer();
					weights[i] = configs.get(i).getWeight();
				}
			}
		}

		private void initSocketIO() {
			pool.setServers(getServers());
			pool.setWeights(getWeights());
			if (pool.isInitialized()) {
				pool.shutDown();
			}
			pool.initialize();
		}

		public String[] getServers() {
			initConfig();
			return servers;
		}

		public Integer[] getWeights() {
			initConfig();
			return weights;
		}

		@Override
		public String toString() {
			return "MemcachePoolConfig [" + configs + "]";
		}

		private static class MemcacheConfig {
			private String host;
			private int port;
			private int weight;

			public MemcacheConfig(String host, int port, int weight) {
				this.host = host;
				this.port = port;
				this.weight = weight;
			}

			public String getServer() {
				return String.format("%s:%d", host, port);
			}

			public int getWeight() {
				return weight;
			}

			@Override
			public String toString() {
				return String.format("{%s:%d,%d}", host, port, weight);
			}
		}
	}
}
