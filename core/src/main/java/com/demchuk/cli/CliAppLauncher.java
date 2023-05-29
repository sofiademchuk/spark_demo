package com.demchuk.cli;

import com.demchuk.ConfigProvider;
import com.demchuk.core.AppLauncher;
import com.demchuk.core.FailedLaunchException;
import lombok.extern.slf4j.Slf4j;
import com.demchuk.SparkAppConfiguration;
import com.demchuk.SparkConfigurationConverter;
import com.demchuk.core.LaunchResult;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class CliAppLauncher implements AppLauncher {

    private SparkAppConfiguration configuration;
    private final SparkConfigurationConverter converter;
    private final ConfigProvider configProvider = ConfigProvider.get();
    private Process launchProcess;

    public CliAppLauncher() {
        this.converter = new SparkConfigurationConverter();
    }

    @Override
    public CompletableFuture<LaunchResult> launch(SparkAppConfiguration configuration) throws FailedLaunchException {
        this.configuration = configuration;
        String submitArguments = converter.convert(configuration);
        String submitScript = configProvider.sparkHome() + "/bin/spark-submit";
        String submitCommand = submitScript + submitArguments;
        log.debug("Launch command: " + submitCommand);
        this.launchProcess = CliCommandExecutor.execute(submitCommand);
        return this.launchProcess.onExit().thenApply(proc -> {
            logOutput(proc);
            LaunchResult result = new LaunchResult();
            result.setStatus(proc.exitValue());
            return result;
        });
    }

    @Override
    public void terminate() {
        if (launchProcess != null && launchProcess.isAlive()) {
            launchProcess.destroyForcibly();
        }
    }

    private void logOutput(Process p) {
        log.info("App " + this.configuration.getName() + " exit code: " + p.exitValue());
        log.debug("App " + this.configuration.getName() + " std output");
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        reader.lines().forEach(log::debug);
    }
}
