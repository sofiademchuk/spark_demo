package org.example;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.LinkPermission;
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

    public static void main(String[] args) {
        ConfigProvider provider = new ConfigProvider();
        String master = provider.sparkMaster();
        log.info("Master is " + master);
    }
}
