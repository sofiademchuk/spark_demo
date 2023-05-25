package org.example.core;

import org.example.SparkAppConfiguration;

public interface AppConfigMutator {
    SparkAppConfiguration mutate(SparkAppConfiguration original);
}
