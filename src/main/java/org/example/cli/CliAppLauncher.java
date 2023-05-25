package org.example.cli;

import lombok.extern.slf4j.Slf4j;
import org.example.ConfigProvider;
import org.example.SparkAppConfiguration;
import org.example.SparkConfigurationConverter;
import org.example.core.AppLauncher;
import org.example.core.FailedLaunchException;
import org.example.core.LaunchResult;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class CliAppLauncher implements AppLauncher {

    private SparkAppConfiguration configuration;
    private final SparkConfigurationConverter converter;
    private final ConfigProvider configProvider = new ConfigProvider();
    private Process launchProcess;

    public CliAppLauncher() {
        this.converter = new SparkConfigurationConverter();
    }

    @Override
    public CompletableFuture<LaunchResult> launch(SparkAppConfiguration configuration) throws FailedLaunchException {
        this.configuration = configuration;
        String submitArguments = converter.convert(configuration);
        String submitScript = configProvider.sparkHome() + "/bin/spark-submit";
        List<String> submitCommand = Arrays.asList(submitScript, submitArguments);
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
