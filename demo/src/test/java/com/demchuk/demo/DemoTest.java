package com.demchuk.demo;

import org.assertj.core.api.Assertions;
import com.demchuk.SparkAppConfiguration;
import com.demchuk.core.AppResult;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DemoTest {

    @Test
    public void sparkPiTest() {
        SparkAppConfiguration application = SparkAppConfiguration.builder()
                .name("SparkPiTest")
                .mainClass("com.demchuk.examples.SparkPiExample")
                .appJar("/absolute/path/to/SparkTestClient.jar")
                .args(List.of("100"))
                .build();

        AppResult result = application.submit();
        Assertions.assertThat(result.getResult()).contains("3.14");
    }

}
