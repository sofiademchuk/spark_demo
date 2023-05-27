package org.example;

import org.example.SparkAppConfiguration;
import org.example.SparkConfigurationConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ConfigurationTests {
    @Test
    void testMinConfiguration() {
        SparkAppConfiguration configuration = new SparkAppConfiguration();
        configuration.setMaster("lkwfj");
        configuration.setDeployMode("cluster");
        configuration.setMainClass("org.example.MySparkApp");
        configuration.setAppJar("c:/users/sonic/ideaprojects/App.jar");

        SparkConfigurationConverter converter = new SparkConfigurationConverter();
        String convertedRequest = converter.convert(configuration);

        Assertions.assertTrue(convertedRequest.contains("--master lkwfj"));
        Assertions.assertTrue(convertedRequest.contains("--deploy-mode cluster"));
        Assertions.assertTrue(convertedRequest.contains("--class org.example.SparkTestApp"));
        Assertions.assertTrue(convertedRequest.contains("c:/users/sonic/ideaprojects/App.jar"));
    }

    @Test
    void testSetConfs() {
        SparkAppConfiguration configuration = new SparkAppConfiguration();
        configuration.getConf().put("key1", "value1");
        configuration.getConf().put("key2", "value2");

        SparkConfigurationConverter converter = new SparkConfigurationConverter();
        String convertedRequest = converter.convert(configuration);

        Assertions.assertTrue(convertedRequest.contains("--conf key1=value1"));
        Assertions.assertTrue(convertedRequest.contains("--conf key2=value2"));
    }

    @Test
    void testSetArgs() {
        SparkAppConfiguration configuration = new SparkAppConfiguration();
        configuration.getArgs().add("1000");
        SparkConfigurationConverter converter = new SparkConfigurationConverter();
        String convertedRequest = converter.convert(configuration);

        Assertions.assertTrue(convertedRequest.contains("1000"));
    }
}





