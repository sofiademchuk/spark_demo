package com.demchuk.cli;

import lombok.SneakyThrows;

import java.util.List;

public class CliCommandExecutor {
    @SneakyThrows
    public static Process execute(String command) {
        return new ProcessBuilder().command(command.split(" "))
                .redirectErrorStream(true)
                .start();
    }
}
