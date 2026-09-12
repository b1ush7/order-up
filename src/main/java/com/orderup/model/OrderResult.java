package com.orderup.model;

public record OrderResult(boolean success, boolean expired, String message, int scoreDelta) {
    public static OrderResult success(String message, int scoreDelta) {
        return new OrderResult(true, false, message, scoreDelta);
    }

    public static OrderResult failed(String message) {
        return new OrderResult(false, false, message, 0);
    }

    public static OrderResult expired(String message, int scoreDelta) {
        return new OrderResult(false, true, message, scoreDelta);
    }
}
