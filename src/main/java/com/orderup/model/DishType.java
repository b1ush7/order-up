package com.orderup.model;

public enum DishType {
    SASHIMI("生鱼片"),
    ROLL("饭团");

    private final String displayName;

    DishType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
