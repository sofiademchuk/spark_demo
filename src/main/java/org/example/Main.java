package org.example;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public final class Main {
    public static void main(String[] args) {
        ConfigProvider provider = new ConfigProvider();
        String master = provider.sparkMaster();
        log.info("Master is " + master);
    }

}