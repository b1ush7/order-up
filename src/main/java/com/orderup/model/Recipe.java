package com.orderup.model;

import java.util.Objects;
import java.util.Set;

/**
 * 不可变菜谱配置。
 */
public class Recipe {
    private final DishType dishType;
    private final int baseScore;
    private final double timeLimitSeconds;
    private final Set<Ingredient> contents;

    public Recipe(DishType dishType, int baseScore, double timeLimitSeconds, Set<Ingredient> contents) {
        this.dishType = Objects.requireNonNull(dishType);
        this.baseScore = baseScore;
        this.timeLimitSeconds = timeLimitSeconds;
        this.contents = contents;
    }

    public DishType getDishType() {
        return dishType;
    }

    public String getDishName() {
        return dishType.getDisplayName();
    }

    public int getBaseScore() {
        return baseScore;
    }

    public double getTimeLimitSeconds() {
        return timeLimitSeconds;
    }

    public Set<Ingredient> getContents() {
        return contents;
    }
}
