package org.example;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.core.SyncType;

import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class ConfigProvider {
    private Properties properties;

    @SneakyThrows
    public ConfigProvider() {
        this.properties = new Properties();
        InputStream stream = ConfigProvider.class.getClassLoader().getResourceAsStream("application.properties");
        properties.load(stream);
        properties.entrySet().stream().forEach(keyValue -> log.debug(keyValue.getKey() + "=" + keyValue.getValue()));
    }

    public String sparkMaster() {
        return properties.getProperty("spark.master");
    }

    public SyncType syncType() {
        String type = properties.getProperty("test.sync.type", "noop");
        return SyncType.valueOf(type);
    }

    public String sparkHome() {
        return properties.getProperty("spark.home");
    }

    public String launcherType() {
        return properties.getProperty("test.launcher.type", "cli");
    }

    public String redisHost() {
        return properties.getProperty("test.sync.redis.host", "localhost");
    }

    public Integer redisPort() {
        String strPort = properties.getProperty("test.sync.redis.port", "6379");
        return Integer.parseInt(strPort);
    }

    public Long defaultAppStartTimeoutMs() {
        String strStartTimeout = properties.getProperty("test.timeouts.start", "30000");
        return Long.parseLong(strStartTimeout);
    }

    public Long defaultAppExecutionTimeout() {
        String strExecutionTimeout = properties.getProperty("test.timeouts.execution", "30000");
        return Long.parseLong(strExecutionTimeout);
    }

    public static void main(String[] args) {
        ConfigProvider provider = new ConfigProvider();
        String master = provider.sparkMaster();
        log.info("Master is " + master);
    }
}
