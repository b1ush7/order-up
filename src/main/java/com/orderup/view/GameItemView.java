package com.orderup.view;

import com.orderup.model.GameItem;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

/**
 * 物品显示层，负责将地图中的物品绘制成蓝色方块。
 */
public class GameItemView {
    public void render(GraphicsContext graphics, List<GameItem> items) {
        graphics.setFill(Color.BLUE);
        for (GameItem item : items) {
            graphics.fillRect(
                    item.getX(),
                    item.getY(),
                    item.getWidth(),
                    item.getHeight()
            );
        }
    }
}
