package com.demchuk.core;

import com.demchuk.ConfigProvider;
import com.demchuk.SparkAppConfiguration;

public class AppConfigDefaulter implements AppConfigMutator {

    private ConfigProvider provider = ConfigProvider.get();

    // todo: review and continue implementation
    @Override
    public SparkAppConfiguration mutate(SparkAppConfiguration original) {
        SparkAppConfiguration.SparkAppConfigurationBuilder builder = original.toBuilder();
        if (original.getMaster() == null) {
            builder.master(provider.sparkMaster());
        }
        return builder.build();
    }
}
