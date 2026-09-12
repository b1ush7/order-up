package com.orderup.view;

import com.orderup.model.InteractionArea;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * 绘制玩家面前的交互检测区域。
 */
public class InteractionAreaView {
    public void render(GraphicsContext graphics, InteractionArea area) {
        graphics.setFill(Color.GREEN);
        graphics.fillRect(
                area.getX(),
                area.getY(),
                InteractionArea.WIDTH,
                InteractionArea.HEIGHT
        );
    }
}
