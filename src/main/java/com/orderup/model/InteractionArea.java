package com.orderup.model;

/**
 * 玩家面前用于检测物品和工作台的交互区域。
 */
public class InteractionArea {
    public static final double WIDTH = 40;
    public static final double HEIGHT = 40;

    private double x;
    private double y;

    public void updateFrom(Player player) {
        switch (player.getFacingDirection()) {
            case UP -> setPosition(
                    player.getX() + (Player.WIDTH - WIDTH) / 2,
                    player.getY() - HEIGHT
            );
            case DOWN -> setPosition(
                    player.getX() + (Player.WIDTH - WIDTH) / 2,
                    player.getY() + Player.HEIGHT
            );
            case LEFT -> setPosition(
                    player.getX() - WIDTH,
                    player.getY() + (Player.HEIGHT - HEIGHT) / 2
            );
            case RIGHT -> setPosition(
                    player.getX() + Player.WIDTH,
                    player.getY() + (Player.HEIGHT - HEIGHT) / 2
            );
        }
    }

    public boolean intersects(double targetX, double targetY, double targetWidth, double targetHeight) {
        return x < targetX + targetWidth
                && x + WIDTH > targetX
                && y < targetY + targetHeight
                && y + HEIGHT > targetY;
    }

    private void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}