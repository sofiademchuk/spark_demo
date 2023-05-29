package com.demchuk;

import com.demchuk.core.AppResult;
import com.demchuk.core.SparkAppController;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SparkAppConfiguration {
    // -- master
    private String master;

    // --deploy-mode
    private String deployMode;

    // --class
    private String mainClass;

    // --name
    private String name;

    // --jars
    @Builder.Default
    private List<String> jars = new ArrayList<>();

    // --packages
    @Builder.Default
    private List<String> packages = new ArrayList<>();

    // --exclude-packages
    @Builder.Default
    private List<String> excludePackages = new ArrayList<>();

    // --repositories
    @Builder.Default
    private List<String> repositories = new ArrayList<>();

    // --py-files
    @Builder.Default
    private List<String> pyFiles = new ArrayList<>();

    // --files
    @Builder.Default
    private List<String> files = new ArrayList<>();

    // --archives
    @Builder.Default
    private List<String> archives = new ArrayList<>();

    // --conf
    @Builder.Default
    private Map<String, String> conf = new HashMap<>();

    // --properties
    private String properties;

    // --driver-memory
    private String driverMemory;

    // --driver-java-options
    private String driverJavaOptions;

    // --driver-library-path
    private String driverLibraryPath;

    // --driver-class-path
    private String driverClassPath;

    // --executor-memory
    private String executorMemory;

    // --proxy-user
    private String proxyUser;

    // --help
    private boolean help;

    // --verbose
    private boolean verbose;

    // --version
    private boolean version;

    // --remote
    private String remote;

    // --driver-cores
    private Integer driverCores;

    // --supervise
    private boolean supervise;

    // --kill
    private String kill;

    // --status
    private String status;

    // --total-executor-cores
    private Integer totalExecutorCores;

    // --executor-cores
    private Integer executorCores;

    // --num-executors
    private Integer numExecutors;

    // --principal
    private String principal;

    // --keytab
    private String keytab;

    // --queue
    private String queue;

    // application jar
    private String appJar;

    // args
    @Builder.Default
    private List<String> args = new ArrayList<>();

    // test timeouts
    private Long startTimeout;
    private Long executionTimeout;

    public AppResult submit() {
        SparkAppController controller = SparkAppController.create(this);
        return controller.submit();
    }
}

