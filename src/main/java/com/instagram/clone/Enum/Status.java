package com.instagram.clone.Enum;

public enum Status {

    ACTIVE("active"),
    IN_ACTIVE("inActive"),
    DELETED("deleted");

    private final String name;
    Status(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
