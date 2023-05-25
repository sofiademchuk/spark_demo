package org.example;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
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
    private List<String> jars = new ArrayList<>();

    // --packages
    private List<String> packages = new ArrayList<>();

    // --exclude-packages
    private List<String> excludePackages = new ArrayList<>();

    // --repositories
    private List<String> repositories = new ArrayList<>();

    // --py-files
    private List<String> pyFiles = new ArrayList<>();

    // --files
    private List<String> files = new ArrayList<>();

    // --archives
    private List<String> archives = new ArrayList<>();

    // --conf
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
    private int driverCores;

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
    private List<String> args = new ArrayList<>();
}

