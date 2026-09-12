package com.orderup.model;

/**
 * 可以放置一个物品的桌面格子。
 */
public class Table extends Tile {
    private GameItem item;

    public Table(int row, int column) {
        super(row, column, TileType.TABLE);
    }

    public boolean place(GameItem item) {
        if (item == null || this.item != null) {
            return false;
        }
        this.item = item;
        item.setX(getX() + (getSize() - item.getWidth()) / 2.0);
        item.setY(getY() + (getSize() - item.getHeight()) / 2.0);
        return true;
    }

    public GameItem take() {
        GameItem taken = item;
        item = null;
        return taken;
    }

    public boolean isEmpty() {
        return item == null;
    }

    public GameItem getItem() {
        return item;
    }
}
