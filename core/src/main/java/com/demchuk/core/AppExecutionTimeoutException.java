package com.demchuk.core;

public class AppExecutionTimeoutException extends TestAppTimeoutException {
    public AppExecutionTimeoutException(TestAppTimeoutException cause) {
        super(cause);
    }
}
