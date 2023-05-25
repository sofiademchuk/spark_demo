package org.example.cli;

import lombok.SneakyThrows;

import java.util.List;

public class CliCommandExecutor {
    @SneakyThrows
    public static Process execute(List<String> command) {
        return new ProcessBuilder().command(command)
                .redirectErrorStream(true)
                .start();
    }
}
