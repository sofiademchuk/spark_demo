package org.example;

import com.google.common.base.Joiner;

import java.util.Map;
import  java.util.List;
import lombok.extern.slf4j.Slf4j;


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
        builder.append("--deploy-mode " + deployModeString + " ");
        // --supervise
        String isSupervise = Boolean.toString(configuration.isSupervise());
        if (isSupervise.equals("true")) {
            builder.append("--supervise ");
        }

        // --name
        String name = configuration.getName();
        builder.append("--name " + name + " ");

        // --conf, --properties
        Map<String,String> conf = configuration.getConf();
        String confString = Joiner.on(", ").withKeyValueSeparator("=").join(conf);
        builder.append("--conf " + confString + " ");

        String properties = configuration.getProperties();
        builder.append("--properties " + properties + " ");

        // --proxy-user
        String proxy = configuration.getProxyUser();
        builder.append("--proxy-user " + properties + " ");

        // --packages
        List<String> packages = configuration.getPackages();
        String packagesString = String.join(",", packages);
        builder.append("--packages " + packagesString + " ");
        //--exclude-packages
        List<String> excludePackages = configuration.getExcludePackages();
        String excludePackagesString = String.join(",", excludePackages);
        builder.append("--exclude-packages " + excludePackagesString + " ");

            // driver parameters
            String driverMemoryString = configuration.getDriverMemory();
            builder.append("--driver-memory " + driverMemoryString + " ");

            String driverJvOptString = configuration.getDriverJavaOptions();
            builder.append("--driver-java-options " + driverJvOptString + " ");

            String driverLbPathString = configuration.getDriverLibraryPath();
            builder.append("--driver-library-path " + driverJvOptString + " ");

            String driverClassPathString = configuration.getDriverClassPath();
            builder.append("--driver-class-path " + driverClassPathString + " ");

            String driverCores = String.valueOf(configuration.getDriverCores());
            builder.append("--driver-cores " + driverCores + " ");
            // executor parameters
            String executorMemString = configuration.getExecutorMemory();
            builder.append("--executor-memory " + executorMemString + " ");

            String numExecutorsString = String.valueOf(configuration.getNumExecutors());
            builder.append("--num-executors " + numExecutorsString + " ");

            String totalExecutorCoresString = String.valueOf(configuration.getTotalExecutorCores());
            builder.append("--total-executor-cores " + totalExecutorCoresString + " ");

            String executorCoresString = String.valueOf(configuration.getExecutorCores());
            builder.append("--executor-cores " + executorCoresString + " ");

            // --jars, --repositories, --py-files, ---archives
            List<String> jars = configuration.getJars();
            String jarsString = String.join(",",jars);
            builder.append("--jars " + jarsString + " ");

            List<String> repositories = configuration.getRepositories();
            String repString = String.join(",", repositories);
            builder.append("--repositories " + repString + " ");

            List<String> pyFiles = configuration.getPyFiles();
            String pyFilesString = String.join(",", pyFiles);
            builder.append("--py-files " + pyFilesString + " ");

            List<String> files = configuration.getFiles();
            String filesString = String.join(",", files);
            builder.append("--files " + filesString + " ");

            List<String> archives = configuration.getArchives();
            String archivesString = String.join(",", archives);
            builder.append("--archives " + archivesString + " ");

            // --kill, --status
            String kill = configuration.getKill();
            builder.append("--kill " + kill + " ");

            String status = configuration.getStatus();
            builder.append("--status " + status + " ");

            // --principal, --keytab
            String principal = configuration.getPrincipal();
            builder.append("--principal " + principal + " ");

            String keytab = configuration.getKeytab();
            builder.append("--keytab " + keytab + " ");

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
            builder.append("--remote " + remote + " ");

            // --queue
            String queue = configuration.getQueue();
            builder.append("--queue " + queue + " ");

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


