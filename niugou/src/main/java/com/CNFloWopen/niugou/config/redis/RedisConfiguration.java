package com.CNFloWopen.niugou.config.redis;

import com.CNFloWopen.niugou.cache.JedisPoolWriper;
import com.CNFloWopen.niugou.cache.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;

/**
 * spring-redis的配置
 */
@Configuration
public class RedisConfiguration {
    @Value("${redis.hostname}")
    private String hostname;
    @Value("${redis.port}")
    private int port;
    @Value("${redis.pool.maxActive}")
    private int maxTotal;
    @Value("${redis.pool.maxIdle}")
    private int maxIdle;
    @Value("${redis.pool.maxWait}")
    private long maxWaitMillis;
    @Value("${redis.pool.testOnBorrow}")
    private boolean testOnBorrow;

    @Autowired
    private JedisPoolConfig jedisPoolConfig;
    @Autowired
    private JedisPoolWriper jedisWritePool;
    @Autowired
    private JedisUtil jedisUtil;


    /**
     * 创建redis连接池的设置
     */
    @Bean(name = "jedisPoolConfig")
    public JedisPoolConfig createJedisPoolConfig()
    {
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//            分配实例
            jedisPoolConfig.setMaxTotal(maxTotal);
//            连接池最多空闲
            jedisPoolConfig.setMaxIdle(maxIdle);
//            最大等待时间
            jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
//             检查连接的有效性
            jedisPoolConfig.setTestOnBorrow(testOnBorrow);
            return jedisPoolConfig;
    }

    /**
     * 创建redis的连接池并做相关的配置
     * @return
     */
    @Bean(name = "jedisWritePool")
    public JedisPoolWriper createJedisPoolWriper()
    {
        JedisPoolWriper jedisPoolWriper = new JedisPoolWriper(jedisPoolConfig,hostname,port);
        return jedisPoolWriper;
    }
    /**
     * 创建redis的工具类，封装好redis连接以进行相关的操作
     */
    @Bean(name = "jedisUtil")
    public JedisUtil createJedisUtil()
    {
        JedisUtil jedisUtil = new JedisUtil();
        jedisUtil.setJedisPool(jedisWritePool);
        return jedisUtil;
    }


    /**
     * redis的key操作
     */
    @Bean(name = "jedisKeys")
    public JedisUtil.Keys createJedisKeys()
    {
        JedisUtil.Keys jedisKeys = jedisUtil.new Keys();
        return jedisKeys;
    }
    /**
     * redis的strings操作
     */
    @Bean(name = "jedisStrings")
    public JedisUtil.Strings createJedisStrings()
    {
        JedisUtil.Strings jedisStrings = jedisUtil.new Strings();
        return jedisStrings;
    }
}
