package org.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.spark.SparkConf;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Slf4j
public class RedisClientSyncHelper extends TestHelper {

    private static final String SPARK_CONF_REDIS_HOST_OPTION = "spark.test.sync.redis.host";
    private static final String SPARK_CONF_REDIS_PORT_OPTION = "spark.test.sync.redis.port";
    private static final String APP_IS_RUNNING_KEY = "running";
    private static final String APP_RESULT_KEY = "result";

    private final JedisPool jedisPool;

    RedisClientSyncHelper(SparkConf sparkConf) {
        super(sparkConf);
        String redisHost = sparkConf.get(SPARK_CONF_REDIS_HOST_OPTION);
        String redisPort = sparkConf.get(SPARK_CONF_REDIS_PORT_OPTION);

        log.debug("Test redis server location: " + redisHost + ":" + redisPort);
        this.jedisPool = new JedisPool(redisHost, Integer.parseInt(redisPort));
    }

    @Override
    public void notifyAppStarted() {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.hset(appId, APP_IS_RUNNING_KEY, "true");
        }
    }

    @Override
    public void writeResult(String result) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.hset(appId, APP_RESULT_KEY, result);
        }
    }
}
