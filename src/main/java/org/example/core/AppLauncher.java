package org.example.core;

import org.example.SparkAppConfiguration;

import java.util.concurrent.CompletableFuture;

public interface AppLauncher {
    CompletableFuture<LaunchResult> launch(SparkAppConfiguration configuration) throws FailedLaunchException;

    void terminate();
}
