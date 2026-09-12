package com.orderup.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 保存盘内食材并识别当前菜品；订单提交和计分由 Service 负责。
 */
public class Plate extends GameItem {
    private final List<Ingredient> contents = new ArrayList<>();

    public Plate() {
    }

    public Plate(double x, double y) {
        super(x, y);
    }

    public boolean addIngredient(Ingredient ingredient) {
        if (ingredient == null || ingredient.getStatus() == IngredientStatus.RAW) {
            return false;
        }
        return contents.add(ingredient);
    }

    public DishType getDishType() {
        if (contents.size() == 1 && count(IngredientType.FISH, IngredientStatus.CUT) == 1) {
            return DishType.SASHIMI;
        }
        if (contents.size() == 2
                && count(IngredientType.RICE, IngredientStatus.COOKED) == 1
                && count(IngredientType.KELP, IngredientStatus.RAW) == 1) {
            return DishType.ROLL;
        }
        return null;
    }

    public boolean matches(Recipe recipe) {
        return recipe != null && getDishType() == recipe.getDishType();
    }

    public boolean isEmpty() {
        return contents.isEmpty();
    }

    public void clear() {
        contents.clear();
    }

    public List<Ingredient> getContents() {
        return Collections.unmodifiableList(contents);
    }

    private long count(IngredientType type, IngredientStatus status) {
        return contents.stream()
                .filter(ingredient -> ingredient.getType() == type && ingredient.getStatus() == status)
                .count();
    }
}
