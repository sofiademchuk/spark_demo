package org.example;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

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
}
