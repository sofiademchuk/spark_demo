package org.example.core;

import org.example.ConfigProvider;
import org.example.SparkAppConfiguration;

public class AppConfigDefaulter implements AppConfigMutator {

    private ConfigProvider provider = new ConfigProvider();

    @Override
    public SparkAppConfiguration mutate(SparkAppConfiguration original) {
        SparkAppConfiguration.SparkAppConfigurationBuilder builder = original.toBuilder();
        if (original.getMaster() == null) {
            builder.master(provider.sparkMaster());
        }
        return builder.build();
    }
}
