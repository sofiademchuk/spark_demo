package com.demchuk.core;

import com.demchuk.ConfigProvider;
import com.demchuk.SparkAppConfiguration;

import java.util.HashMap;
import java.util.Map;

public class AppConfigSyncPreparer implements AppConfigMutator {

    private static final String SPARK_CONF_IDENTITY_OPTION = "spark.test.id";
    private static final String SPARK_CONF_SYNC_TYPE_OPTION = "spark.test.sync.type";
    private static final ConfigProvider CONFIG_PROVIDER = ConfigProvider.get();
    private final String identifier;
    private final SyncType syncType = CONFIG_PROVIDER.syncType();

    public AppConfigSyncPreparer(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public SparkAppConfiguration mutate(SparkAppConfiguration original) {
        Map<String, String> newConfig = new HashMap<>(original.getConf());
        newConfig.put(SPARK_CONF_IDENTITY_OPTION, this.identifier);
        newConfig.put(SPARK_CONF_SYNC_TYPE_OPTION, this.syncType.getType());
        return original.toBuilder()
                .conf(newConfig)
                .build();
    }
}
