package com.orderup.view;

import com.orderup.model.GameMap;
import com.orderup.model.IngredientSource;
import com.orderup.model.Tile;
import com.orderup.model.TileType;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

/**
 * 绘制地图格子。
 */
public class GameMapView {
    public void render(GraphicsContext graphics, GameMap map) {
        for (Tile[] row : map.getTiles()) {
            for (Tile tile : row) {
                renderTile(graphics, tile);
            }
        }
    }

    private void renderTile(GraphicsContext graphics, Tile tile) {
        graphics.setFill(fillColor(tile));
        graphics.fillRect(tile.getX(), tile.getY(), tile.getSize(), tile.getSize());

        graphics.setStroke(tile.isInteractable() ? Color.GRAY : Color.BLACK);
        graphics.setLineWidth(1);
        graphics.strokeRect(tile.getX(), tile.getY(), tile.getSize(), tile.getSize());

        if (tile instanceof IngredientSource source) {
            renderSourceLabel(graphics, source);
        }
    }

    private Color fillColor(Tile tile) {
        if (tile instanceof IngredientSource source) {
            return IngredientPalette.sourceColor(source.getIngredientType());
        }
        if (tile.getType() == TileType.TABLE) {
            return tile.isInteractable() ? Color.GRAY : Color.BLACK;
        }
        return Color.LIGHTGRAY;
    }

    private void renderSourceLabel(GraphicsContext graphics, IngredientSource source) {
        graphics.setFill(IngredientPalette.textColor(source.getIngredientType()));
        graphics.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        graphics.setTextAlign(TextAlignment.CENTER);
        graphics.setTextBaseline(VPos.CENTER);
        graphics.fillText(
                IngredientPalette.label(source.getIngredientType()),
                source.getX() + source.getSize() / 2,
                source.getY() + source.getSize() / 2
        );
    }
}
