package com.demchuk.core;

import com.demchuk.SparkAppConfiguration;

public interface AppConfigMutator {
    SparkAppConfiguration mutate(SparkAppConfiguration original);
}
