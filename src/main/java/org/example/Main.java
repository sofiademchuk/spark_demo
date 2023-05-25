package org.example;

import java.util.HashMap;
import java.util.Map;

public final class Main {
    public static void main(String[] args) {
        SparkAppConfiguration configuration = new SparkAppConfiguration();
        configuration.setMaster("yarn");
        Map<String, String> conf = new HashMap<>();
        conf.put("spark.myconfig.one", "one");
        conf.put("spark.myconfig.two", "two");
        configuration.setConf(conf);

        SparkConfigurationConverter converter = new SparkConfigurationConverter();
        String submitArguments = converter.convert(configuration);

        System.out.println(submitArguments);
    }


}