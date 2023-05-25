package org.example.core;

public class LaunchTimeoutException extends TestAppTimeoutException {
    public LaunchTimeoutException(TestAppTimeoutException cause) {
        super(cause);
    }
}
