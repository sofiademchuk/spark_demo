package com.demchuk.core;

import com.demchuk.ConfigProvider;
import lombok.extern.slf4j.Slf4j;
import com.demchuk.SparkAppConfiguration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RedisAppSyncHelper implements AppSyncHelper, AppConfigMutator {

    private static final String SPARK_CONF_REDIS_HOST_OPTION = "spark.test.sync.redis.host";
    private static final String SPARK_CONF_REDIS_PORT_OPTION = "spark.test.sync.redis.port";
    private static final String APP_IS_RUNNING_KEY = "running";
    private static final String APP_RESULT_KEY = "result";

    private static ConfigProvider configProvider = ConfigProvider.get();
    private static JedisPool jedisPooled = initJedisPool();

    private final String appId;
    private final Jedis redisClient;

    private static JedisPool initJedisPool() {
        String redisHost = configProvider.redisHost();
        Integer redisPort = configProvider.redisPort();

        return new JedisPool(redisHost, redisPort);
    }

    public RedisAppSyncHelper(final String appId) {
        this.appId = appId;
        this.redisClient = jedisPooled.getResource();
    }

    @Override
    public void init() {
        Map<String,String> data = new HashMap<>();
        data.put("initialized", "true");
        redisClient.hset(appId, data);
        log.debug("Initialized redis app sync helper for appId: " + appId);
    }

    @Override
    public void cleanup() {
        redisClient.del(appId);
        redisClient.close();
        log.debug("Cleaned redis items for appId: " + appId);
    }

    @Override
    public boolean isRunning() {
        Map<String,String> data = redisClient.hgetAll(appId);
        return Boolean.parseBoolean(data.getOrDefault(APP_IS_RUNNING_KEY, "false"));
    }

    @Override
    public boolean isResultAvailable() {
        Map<String,String> data = redisClient.hgetAll(appId);
        return data.containsKey(APP_RESULT_KEY);
    }

    @Override
    public String getResultString() {
        Map<String,String> data = redisClient.hgetAll(appId);
        return data.get(APP_RESULT_KEY);
    }

    @Override
    public SparkAppConfiguration mutate(SparkAppConfiguration original) {
        Map<String,String> newConfig = new HashMap<>(original.getConf());
        newConfig.put(SPARK_CONF_REDIS_HOST_OPTION, configProvider.redisHost());
        newConfig.put(SPARK_CONF_REDIS_PORT_OPTION, String.valueOf(configProvider.redisPort()));
        return original.toBuilder()
                .conf(newConfig)
                .build();
    }
}
