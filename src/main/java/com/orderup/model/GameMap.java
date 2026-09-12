package com.orderup.model;

import com.orderup.config.GameConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 地图格子和场上物品的容器。
 */
public class GameMap {
    private final Tile[][] tiles;
    private final List<GameItem> items = new ArrayList<>();

    public GameMap() {
        this.tiles = new Tile[GameConfig.MAP_ROWS][GameConfig.MAP_COLUMNS];
        for (int row = 0; row < GameConfig.MAP_ROWS; row++) {
            for (int column = 0; column < GameConfig.MAP_COLUMNS; column++) {
                tiles[row][column] = new Tile(row, column);
            }
        }
    }

    public Tile getTile(int row, int column) {
        return tiles[row][column];
    }

    public void setTile(int row, int column, Tile tile) {
        tiles[row][column] = tile;
    }

    public <T extends GameItem> T addItem(T item) {
        items.add(item);
        return item;
    }

    public void removeItem(GameItem item) {
        items.remove(item);
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public List<GameItem> getItems() {
        return Collections.unmodifiableList(items);
    }
}
