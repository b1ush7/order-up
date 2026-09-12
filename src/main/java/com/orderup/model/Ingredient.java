package com.orderup.model;

/**
 * 可加工的食材。
 */
public class Ingredient extends GameItem {
    private final IngredientType type;
    private IngredientStatus status = IngredientStatus.RAW;

    public Ingredient(IngredientType type, double x, double y) {
        super(x, y);
        this.type = type;
    }

    public Ingredient(IngredientType type,IngredientStatus status){
        super(0,0);
        this.type=type;
        this.status=status;
    }

    public IngredientType getType() {
        return type;
    }

    public IngredientStatus getStatus() {
        return status;
    }

    public void setStatus(IngredientStatus status) {
        this.status = status;
    }
}
