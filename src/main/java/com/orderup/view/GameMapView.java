package com.orderup.view;

import com.orderup.model.GameMap;
import com.orderup.model.Tile;
import com.orderup.model.TileType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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
    }

    private Color fillColor(Tile tile) {
        if (tile.getType() == TileType.INGREDIENT_SOURCE) {
            return Color.DARKORANGE;
        }
        if (tile.getType() == TileType.TABLE) {
            return tile.isInteractable() ? Color.GRAY : Color.BLACK;
        }
        return Color.LIGHTGRAY;
    }
}
