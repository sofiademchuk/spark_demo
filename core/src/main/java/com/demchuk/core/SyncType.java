package com.demchuk.core;

public enum SyncType {
    NOOP("noop"),
    REDIS("redis");
    private final String type;

    SyncType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
