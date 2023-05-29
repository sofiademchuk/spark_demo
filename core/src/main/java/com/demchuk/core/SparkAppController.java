package com.demchuk.core;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import com.demchuk.ConfigProvider;
import com.demchuk.SparkAppConfiguration;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

@Slf4j
public class SparkAppController {

    private static ConfigProvider configProvider = ConfigProvider.get();

    private SparkAppConfiguration configuration;
    private String identifier;

    private List<AppConfigMutator> configMutators = new ArrayList<>();
    private List<AppConfigValidator> configValidators = new ArrayList<>();
    private AppLauncher launcher;
    private AppSyncHelper syncHelper;

    private Duration appStartTimeout;
    private Duration appExecutionTimeout;

    public static SparkAppController create(SparkAppConfiguration userConfig) {
        SparkAppController controller = new SparkAppController();
        String identifier = UUID.randomUUID().toString();
        // copy user config
        controller.configuration = userConfig.toBuilder().build();
        controller.identifier = identifier;

        controller.configMutators.add(new AppConfigDefaulter());
        controller.configMutators.add(new AppConfigSyncPreparer(identifier));

        // add validators
        // todo: implement validator class (must implement AppConfigValidator interface); add validator to validators list
        // RequiredPropertiesPresenceValidator validator = new RequiredPropertiesPresenceValidator();
        // controller.configValidators.add(validator);

        // init launcher
        controller.launcher = AppLauncher.create();

        // init sync helper
        RedisAppSyncHelper redisSyncHelper = new RedisAppSyncHelper(identifier);
        controller.syncHelper = redisSyncHelper;
        controller.configMutators.add(redisSyncHelper);

        // init timeouts
        if (userConfig.getStartTimeout() != null) {
            controller.appStartTimeout = Duration.of(userConfig.getStartTimeout(), ChronoUnit.MILLIS);
        } else {
            controller.appStartTimeout = Duration.of(configProvider.defaultAppStartTimeoutMs(), ChronoUnit.MILLIS);
        }

        if (userConfig.getExecutionTimeout() != null) {
            controller.appExecutionTimeout = Duration.of(userConfig.getExecutionTimeout(), ChronoUnit.MILLIS);
        } else {
            controller.appExecutionTimeout = Duration.of(configProvider.defaultAppExecutionTimeout(), ChronoUnit.MILLIS);
        }

        log.info("Created controller for app " + userConfig.getName() + " with id " + controller.identifier);
        return controller;
    }

    private SparkAppController() {}

    public AppResult submit() {
        // update configuration: set defaults
        // update configuration: set test identifiers
        for (AppConfigMutator mutator : configMutators) {
            configuration = mutator.mutate(configuration);
        }
        // validate configuration
        configValidators.forEach(validator -> validator.validate(this.configuration));
        // launch application
        syncHelper.init();
        launcher.launch(configuration);

        // wait for app to start or timeout
        try {
            log.info("Application " + identifier + " launched, waiting for start");
            waitFor(() -> syncHelper.isRunning(), appStartTimeout);
        } catch (TestAppTimeoutException e) {
            log.error("App " + identifier + " failed to start during " + appStartTimeout.getSeconds() + " seconds");
            cleanup();
            throw new LaunchTimeoutException(e);
        }
        // wait for app to complete or timeout
        try {
            log.info("Application " + identifier + " started, waiting for completion");
            waitFor(() -> syncHelper.isResultAvailable(), appExecutionTimeout);
        } catch (TestAppTimeoutException e) {
            log.error("App " + identifier + " failed to complete during " + appExecutionTimeout.getSeconds() + " seconds");
            cleanup();
            throw new AppExecutionTimeoutException(e);
        }
        // read result
        String result = syncHelper.getResultString();
        // cleanup
        cleanup();
        // return result
        return new AppResult(result);
    }

    private void cleanup() {
        launcher.terminate();
        syncHelper.cleanup();
    }

    @SneakyThrows
    private void waitFor(Callable<Boolean> action, Duration timeout) throws TestAppTimeoutException {
        long deadline = System.currentTimeMillis() + timeout.toMillis();
        Exception exception = null;
        while (System.currentTimeMillis() < deadline) {
            try {
                if (Boolean.TRUE.equals(action.call())) {
                    return;
                } else {
                    Thread.sleep(500L);
                }
            } catch (InterruptedException e) {
                throw e;
            } catch (Exception e) {
                log.trace("Exception while waiting: " + e);
                exception = e;
            }
        }

        if (exception != null) {
            throw new TestAppTimeoutException(exception);
        } else {
            throw new TestAppTimeoutException();
        }
    }
}
