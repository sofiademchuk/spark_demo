package com.demchuk.core;

import com.demchuk.ConfigProvider;
import com.demchuk.SparkAppConfiguration;
import com.demchuk.cli.CliAppLauncher;

import java.util.concurrent.CompletableFuture;

public interface AppLauncher {
    CompletableFuture<LaunchResult> launch(SparkAppConfiguration configuration) throws FailedLaunchException;

    void terminate();

    static AppLauncher create() {
        ConfigProvider provider = ConfigProvider.get();
        String launcherType = provider.launcherType();
        switch (launcherType) {
            case "cli":
                return new CliAppLauncher();
            default:
                throw new RuntimeException("Unsupported launcher type " + launcherType);
        }
    }
}
