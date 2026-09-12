package com.orderup.model;

public record InteractionResult(boolean success, String message) {
    public static InteractionResult ok(String message) {
        return new InteractionResult(true, message);
    }

    public static InteractionResult failed(String message) {
        return new InteractionResult(false, message);
    }
}
