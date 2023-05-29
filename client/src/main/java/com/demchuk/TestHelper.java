package com.demchuk;

import org.apache.spark.SparkConf;

public abstract class TestHelper {

    private static final String SPARK_CONF_IDENTITY_OPTION = "spark.test.id";
    private static final String SPARK_CONF_SYNC_TYPE_OPTION = "spark.test.sync.type";

    protected final SparkConf sparkConf;
    protected final String appId;

    public static TestHelper create(final SparkConf sparkConf) {
        String syncType = sparkConf.get(SPARK_CONF_SYNC_TYPE_OPTION);
        switch (syncType) {
            case "redis":
                return new RedisClientSyncHelper(sparkConf);
            default:
                throw new RuntimeException("Unsupported sync type: " + syncType);
        }
    }

    TestHelper(final SparkConf sparkConf) {
        this.sparkConf = sparkConf;
        this.appId = sparkConf.get(SPARK_CONF_IDENTITY_OPTION);
    }

    public abstract void notifyAppStarted();
    public abstract void writeResult(final String result);
}
