package com.demchuk;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class SparkConfigurationConverter {
    public String convert(SparkAppConfiguration configuration) {

        StringBuilder builder = new StringBuilder();

        // --class
        String mainClassString = configuration.getMainClass();
        builder.append(" --class " + mainClassString);

        // --master
        String masterString = configuration.getMaster();
        builder.append(" --master " + masterString);

        // --deploy-mode
        String deployModeString = configuration.getDeployMode();
        if (deployModeString != null && !deployModeString.isEmpty()) {
            builder.append(" --deploy-mode " + deployModeString);
        }
        // --supervise
        if (configuration.isSupervise()) {
            builder.append(" --supervise");
        }

        // --name
        String name = configuration.getName();
        if (name != null && !name.isEmpty()) {
            builder.append(" --name \"" + name + "\"");
        }

        // --conf, --properties
        Map<String, String> conf = configuration.getConf();
        if (!conf.isEmpty()) {
            conf.entrySet()
                    .stream()
                    .map(entry -> " --conf " + entry.getKey() + "=" + entry.getValue())
                    .forEach(builder::append);
        }

        String properties = configuration.getProperties();
        if (properties != null && !properties.isEmpty()) {
            builder.append(" --properties " + properties);
        }

        // --proxy-user
        String proxy = configuration.getProxyUser();
        if (proxy != null && !proxy.isEmpty()) {
            builder.append(" --proxy-user " + proxy);
        }

        // --packages
        List<String> packages = configuration.getPackages();
        if (!packages.isEmpty()) {
            String packagesString = String.join(",", packages);
            builder.append(" --packages " + packagesString);
        }

        //--exclude-packages
        List<String> excludePackages = configuration.getExcludePackages();
        if (!excludePackages.isEmpty()) {
            String excludePackagesString = String.join(",", excludePackages);
            builder.append(" --exclude-packages " + excludePackagesString);
        }

        // driver parameters
        String driverMemoryString = configuration.getDriverMemory();
        if (driverMemoryString != null && !driverMemoryString.isEmpty()) {
            builder.append(" --driver-memory " + driverMemoryString);
        }

        String driverJvOptString = configuration.getDriverJavaOptions();
        if (driverJvOptString != null && !driverJvOptString.isEmpty()) {
            builder.append(" --driver-java-options \"" + driverJvOptString + "\"");
        }

        String driverLbPathString = configuration.getDriverLibraryPath();
        if (driverLbPathString != null && !driverLbPathString.isEmpty()) {
            builder.append(" --driver-library-path " + driverJvOptString);
        }

        String driverClassPathString = configuration.getDriverClassPath();
        if (driverClassPathString != null && !driverClassPathString.isEmpty()) {
            builder.append(" --driver-class-path " + driverClassPathString);
        }

        Integer driverCores = configuration.getDriverCores();
        if (driverCores != null) {
            builder.append(" --driver-cores " + driverCores);
        }

        // executor parameters
        String executorMemString = configuration.getExecutorMemory();
        if (executorMemString != null && !executorMemString.isEmpty()) {
            builder.append(" --executor-memory " + executorMemString);
        }

        Integer numExecutors = configuration.getNumExecutors();
        if (numExecutors != null) {
            builder.append(" --num-executors " + numExecutors);
        }

        Integer totalExecutorCoresString = configuration.getTotalExecutorCores();
        if (totalExecutorCoresString != null) {
            builder.append(" --total-executor-cores " + totalExecutorCoresString);
        }

        Integer executorCores = configuration.getExecutorCores();
        if (executorCores != null) {
            builder.append(" --executor-cores " + executorCores);
        }

        // --jars, --repositories, --py-files, ---archives
        List<String> jars = configuration.getJars();
        if (!jars.isEmpty()) {
            String jarsString = String.join(",", jars);
            builder.append(" --jars " + jarsString);
        }

        List<String> repositories = configuration.getRepositories();
        if (!repositories.isEmpty()) {
            String repString = String.join(",", repositories);
            builder.append(" --repositories " + repString);
        }

        List<String> pyFiles = configuration.getPyFiles();
        if (!pyFiles.isEmpty()) {
            String pyFilesString = String.join(",", pyFiles);
            builder.append(" --py-files " + pyFilesString);
        }

        List<String> files = configuration.getFiles();
        if (!files.isEmpty()) {
            String filesString = String.join(",", files);
            builder.append(" --files " + filesString);
        }

        List<String> archives = configuration.getArchives();
        if (!archives.isEmpty()) {
            String archivesString = String.join(",", archives);
            builder.append(" --archives " + archivesString);
        }

        // --kill, --status
        String kill = configuration.getKill();
        if (kill != null && !kill.isEmpty()) {
            builder.append(" --kill " + kill);
        }

        String status = configuration.getStatus();
        if (status != null && !status.isEmpty()) {
            builder.append(" --status " + status);
        }

        // --principal, --keytab
        String principal = configuration.getPrincipal();
        if (principal != null && !principal.isEmpty()) {
            builder.append(" --principal " + principal);
        }

        String keytab = configuration.getKeytab();
        if (keytab != null && !keytab.isEmpty()) {
            builder.append(" --keytab " + keytab);
        }

        // --help, verbose, version
        if (configuration.isHelp()) {
            builder.append("--help ");
        }

        if (configuration.isVerbose()) {
            builder.append("--verbose");
        }

        if (configuration.isVersion()) {
            builder.append("--version ");
        }
        // --remote
        String remote = configuration.getRemote();
        if (remote != null && !remote.isEmpty()) {
            builder.append(" --remote " + remote);
        }

        // --queue
        String queue = configuration.getQueue();
        if (queue != null && !queue.isEmpty()) {
            builder.append(" --queue " + queue);
        }

        // <app-jar>, <args>
        String appJar = configuration.getAppJar();
        builder.append(" " + appJar);

        List<String> args = configuration.getArgs();
        String argsString = String.join(" ", args);
        builder.append(" " + argsString);

        String result = builder.toString();
        log.debug(result);
        return result;
    }

}
