package org.example.core;

public class TestAppTimeoutException extends RuntimeException {

    public TestAppTimeoutException() {
        super();
    }

    public TestAppTimeoutException(Exception cause) {
        super(cause);
    }
}
