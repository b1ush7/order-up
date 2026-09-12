package com.orderup.view;

import com.orderup.model.IngredientType;
import javafx.scene.paint.Color;

/**
 * 食材和食材库共用的颜色、文字标识，保证同一种食材显示一致。
 */
final class IngredientPalette {
    private IngredientPalette() {
    }

    static Color itemColor(IngredientType type) {
        return switch (type) {
            case FISH -> Color.web("#E85D5D");
            case RICE -> Color.web("#F3DFA2");
            case KELP -> Color.web("#3F8F5B");
        };
    }

    static Color sourceColor(IngredientType type) {
        return switch (type) {
            case FISH -> Color.web("#8F3338");
            case RICE -> Color.web("#B99B5B");
            case KELP -> Color.web("#245D3A");
        };
    }

    static Color textColor(IngredientType type) {
        return type == IngredientType.RICE ? Color.web("#3B3020") : Color.WHITE;
    }

    static String label(IngredientType type) {
        return switch (type) {
            case FISH -> "鱼";
            case RICE -> "米";
            case KELP -> "苔";
        };
    }
}
