package org.example;

import lombok.Data;


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
    private List<String> jars;

    // --packages
    private List<String> packages;

    // --exclude-packages
    private List<String> excludePackages;

    // --repositories
    private List<String> repositories;

    // --py-files
    private List<String> pyFiles;

    // --files
    private List<String> files;

    // --archives
    private List<String> archives;

    // --conf
    private Map<String,String> conf;

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
    private int totalExecutorCores;

    // --executor-cores
    private int executorCores;

    // --num-executors
    private int numExecutors;

    // --principal
    private String principal;

    // --keytab
    private String keytab;

    // --queue
    private String queue;

    // application jar
    private String appJar;

    // args
    private List<String> args;
}

