package org.example;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class SparkConfigurationConverter {
    public String convert(SparkAppConfiguration configuration) {

        StringBuilder builder = new StringBuilder();

        // --class
        String mainClassString = configuration.getMainClass();
        builder.append("--class " + mainClassString + " ");

        // --master
        String masterString = configuration.getMaster();
        builder.append("--master " + masterString + " ");

        // --deploy-mode
        String deployModeString = configuration.getDeployMode();
        if (deployModeString != null && !deployModeString.isEmpty()) {
            builder.append("--deploy-mode " + deployModeString + " ");
        }
        // --supervise
        String isSupervise = Boolean.toString(configuration.isSupervise());
        if (isSupervise.equals("true")) {
            builder.append("--supervise ");
        }

        // --name
        String name = configuration.getName();
        if (name != null && !name.isEmpty()) {
            builder.append("--name " + name + " ");
        }

        // --conf, --properties
        Map<String, String> conf = configuration.getConf();
        if (!conf.isEmpty()) {
            String confString = conf.entrySet()
                    .stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.joining("--conf "));
            builder.append("--conf " + confString + " ");
        }

        String properties = configuration.getProperties();
        if (properties != null && !properties.isEmpty()) {
            builder.append("--properties " + properties + " ");
        }

        // --proxy-user
        String proxy = configuration.getProxyUser();
        if (proxy != null && !proxy.isEmpty()) {
            builder.append("--proxy-user " + properties + " ");
        }

        // --packages
        List<String> packages = configuration.getPackages();
        if (!packages.isEmpty()) {
            String packagesString = String.join(",", packages);
            builder.append("--packages " + packagesString + " ");
        }

        //--exclude-packages
        List<String> excludePackages = configuration.getExcludePackages();
        if (!excludePackages.isEmpty()) {
            String excludePackagesString = String.join(",", excludePackages);
            builder.append("--exclude-packages " + excludePackagesString + " ");
        }

        // driver parameters
        String driverMemoryString = configuration.getDriverMemory();
        if (driverMemoryString != null && !driverMemoryString.isEmpty()) {
            builder.append("--driver-memory " + driverMemoryString + " ");
        }

        String driverJvOptString = configuration.getDriverJavaOptions();
        if (driverJvOptString != null && !driverJvOptString.isEmpty()) {
            builder.append("--driver-java-options " + driverJvOptString + " ");
        }

        String driverLbPathString = configuration.getDriverLibraryPath();
        if (driverLbPathString != null && !driverLbPathString.isEmpty()) {
            builder.append("--driver-library-path " + driverJvOptString + " ");
        }

        String driverClassPathString = configuration.getDriverClassPath();
        if (driverClassPathString != null && !driverClassPathString.isEmpty()) {
            builder.append("--driver-class-path " + driverClassPathString + " ");
        }

        String driverCores = String.valueOf(configuration.getDriverCores());
        if (driverCores != null && !driverCores.isEmpty()) {
            builder.append("--driver-cores " + driverCores + " ");
        }

        // executor parameters
        String executorMemString = configuration.getExecutorMemory();
        if (executorMemString != null && !executorMemString.isEmpty()) {
            builder.append("--executor-memory " + executorMemString + " ");
        }

        String numExecutorsString = String.valueOf(configuration.getNumExecutors());
        if (numExecutorsString != null && !numExecutorsString.isEmpty()) {
            builder.append("--num-executors " + numExecutorsString + " ");
        }

        String totalExecutorCoresString = String.valueOf(configuration.getTotalExecutorCores());
        if (totalExecutorCoresString != null && !totalExecutorCoresString.isEmpty()) {
            builder.append("--total-executor-cores " + totalExecutorCoresString + " ");
        }

        String executorCoresString = String.valueOf(configuration.getExecutorCores());
        if (executorCoresString != null && !executorCoresString.isEmpty()) {
            builder.append("--executor-cores " + executorCoresString + " ");
        }

        // --jars, --repositories, --py-files, ---archives
        List<String> jars = configuration.getJars();
        if (!jars.isEmpty()) {
            String jarsString = String.join(",", jars);
            builder.append("--jars " + jarsString + " ");
        }

        List<String> repositories = configuration.getRepositories();
        if (!repositories.isEmpty()) {
            String repString = String.join(",", repositories);
            builder.append("--repositories " + repString + " ");
        }

        List<String> pyFiles = configuration.getPyFiles();
        if (!pyFiles.isEmpty()) {
            String pyFilesString = String.join(",", pyFiles);
            builder.append("--py-files " + pyFilesString + " ");
        }

        List<String> files = configuration.getFiles();
        if (!files.isEmpty()) {
            String filesString = String.join(",", files);
            builder.append("--files " + filesString + " ");
        }

        List<String> archives = configuration.getArchives();
        if (!archives.isEmpty()) {
            String archivesString = String.join(",", archives);
            builder.append("--archives " + archivesString + " ");
        }

        // --kill, --status
        String kill = configuration.getKill();
        if (kill != null && !kill.isEmpty()) {
            builder.append("--kill " + kill + " ");
        }

        String status = configuration.getStatus();
        if (status != null && !status.isEmpty()) {
            builder.append("--status " + status + " ");
        }

        // --principal, --keytab
        String principal = configuration.getPrincipal();
        if (principal != null && !principal.isEmpty()) {
            builder.append("--principal " + principal + " ");
        }

        String keytab = configuration.getKeytab();
        if (keytab != null && !keytab.isEmpty()) {
            builder.append("--keytab " + keytab + " ");
        }

        // --help, verbose, version
        String isHelp = Boolean.toString(configuration.isHelp());
        if (isHelp.equals("true")) {
            builder.append("--help ");
        }

        String isVerbose = Boolean.toString(configuration.isVerbose());
        if (isVerbose.equals("true")) {
            builder.append("--verbose");
        }

        String isVersion = Boolean.toString(configuration.isVersion());
        if (isVersion.equals("true")) {
            builder.append("--version ");
        }

        // --remote
        String remote = configuration.getRemote();
        if (remote != null && !remote.isEmpty()) {
            builder.append("--remote " + remote + " ");
        }

        // --queue
        String queue = configuration.getQueue();
        if (queue != null && !queue.isEmpty()) {
            builder.append("--queue " + queue + " ");
        }

        // <app-jar>, <args>
        String appJar = configuration.getAppJar();
        builder.append(appJar + " ");

        List<String> args = configuration.getArgs();
        String argsString = String.join(" ", args);
        builder.append(argsString + " ");

        log.info(builder.toString());
        return String.valueOf(builder);
    }

}


