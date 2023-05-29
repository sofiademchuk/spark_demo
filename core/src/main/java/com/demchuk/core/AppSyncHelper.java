package com.demchuk.core;

public interface AppSyncHelper {

    void init();

    void cleanup();
    boolean isRunning();
    boolean isResultAvailable();

    String getResultString();
}
