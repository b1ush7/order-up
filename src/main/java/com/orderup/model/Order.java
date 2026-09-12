package com.orderup.model;

import java.util.Objects;

/**
 * 一张正在计时的顾客订单。
 */
public class Order {
    private final String id;
    private final Recipe recipe;
    private double remainingSeconds;
    private OrderStatus status = OrderStatus.ACTIVE;

    public Order(String id, Recipe recipe) {
        this.id = Objects.requireNonNull(id);
        this.recipe = Objects.requireNonNull(recipe);
        this.remainingSeconds = recipe.getTimeLimitSeconds();
    }

    public void update(double deltaSeconds) {
        if (status != OrderStatus.ACTIVE) {
            return;
        }
        remainingSeconds = Math.max(0, remainingSeconds - deltaSeconds);
        if (remainingSeconds == 0) {
            status = OrderStatus.EXPIRED;
        }
    }

    public void complete() {
        if (status == OrderStatus.ACTIVE) {
            status = OrderStatus.COMPLETED;
        }
    }

    public String getId() {
        return id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public double getRemainingSeconds() {
        return remainingSeconds;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
