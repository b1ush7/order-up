package com.orderup.model;

import java.util.Objects;

/**
 * 固定在地图上的食材源，记录玩家交互时应生成的食材类型。
 */
public class IngredientSource extends Tile {
    private final IngredientType ingredientType;

    public IngredientSource(int row, int column, IngredientType ingredientType) {
        super(row, column, TileType.INGREDIENT_SOURCE);
        this.ingredientType = Objects.requireNonNull(ingredientType);
    }

    public IngredientType getIngredientType() {
        return ingredientType;
    }
}
