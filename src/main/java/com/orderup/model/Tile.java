package com.orderup.model;

import com.orderup.config.GameConfig;

/**
 * 地图中的一个不可拾取格子。
 */
public class Tile {
    private final int row;
    private final int column;
    private final TileType type;
    private boolean interactable;

    public Tile(int row, int column) {
        this(row, column, TileType.FLOOR);
    }

    public Tile(int row, int column, TileType type) {
        this.row = row;
        this.column = column;
        this.type = type;
    }

    public int getX() {
        return column * GameConfig.TILE_SIZE;
    }

    public int getY() {
        return row * GameConfig.TILE_SIZE;
    }

    public int getSize() {
        return GameConfig.TILE_SIZE;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public TileType getType() {
        return type;
    }

    public boolean isInteractable() {
        return interactable;
    }

    public void setInteractable(boolean interactable) {
        this.interactable = interactable;
    }
}
