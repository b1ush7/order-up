package com.orderup.model;

/**
 * 能够出现在地图上并被玩家拿取的物品。
 */
public abstract class GameItem {
    private double x;
    private double y;
    private final double width = 40;
    private final double height = 40;

    public GameItem() {
    }

    public GameItem(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
