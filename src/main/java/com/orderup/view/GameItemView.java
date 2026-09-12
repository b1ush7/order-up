package com.orderup.view;

import com.orderup.model.GameItem;
import com.orderup.model.Ingredient;
import com.orderup.model.Plate;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.util.List;

/**
 * 物品显示层，按食材类型绘制不同颜色，并单独绘制盘子。
 */
public class GameItemView {
    public void render(GraphicsContext graphics, List<GameItem> items) {
        for (GameItem item : items) {
            if (item instanceof Ingredient ingredient) {
                renderIngredient(graphics, ingredient);
            } else if (item instanceof Plate plate) {
                renderPlate(graphics, plate);
            } else {
                graphics.setFill(Color.SLATEBLUE);
                graphics.fillRect(item.getX(), item.getY(), item.getWidth(), item.getHeight());
            }
        }
    }

    private void renderIngredient(GraphicsContext graphics, Ingredient ingredient) {
        double x = ingredient.getX();
        double y = ingredient.getY();
        double width = ingredient.getWidth();
        double height = ingredient.getHeight();

        graphics.setFill(IngredientPalette.itemColor(ingredient.getType()));
        graphics.fillRoundRect(x, y, width, height, 10, 10);
        graphics.setStroke(Color.web("#1A2024"));
        graphics.setLineWidth(2);
        graphics.strokeRoundRect(x, y, width, height, 10, 10);

        graphics.setFill(IngredientPalette.textColor(ingredient.getType()));
        graphics.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        graphics.setTextAlign(TextAlignment.CENTER);
        graphics.setTextBaseline(VPos.CENTER);
        graphics.fillText(
                IngredientPalette.label(ingredient.getType()),
                x + width / 2,
                y + height / 2
        );
    }

    private void renderPlate(GraphicsContext graphics, Plate plate) {
        graphics.setFill(Color.web("#F2F4F3"));
        graphics.fillOval(plate.getX(), plate.getY(), plate.getWidth(), plate.getHeight());
        graphics.setStroke(Color.web("#82919A"));
        graphics.setLineWidth(3);
        graphics.strokeOval(plate.getX(), plate.getY(), plate.getWidth(), plate.getHeight());
    }
}
