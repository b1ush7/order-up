package com.orderup.service;

import com.orderup.config.GameConfig;
import com.orderup.model.*;

public interface GameService {
    GameMap createMap();

    default void configureMap(GameMap map) {
        for (int row = 0; row < GameConfig.MAP_ROWS; row++) {
            placeTable(map, row, 0);
            placeTable(map, row, GameConfig.MAP_COLUMNS - 1);
        }
        for (int column = 0; column < GameConfig.MAP_COLUMNS; column++) {
            placeTable(map, 0, column);
            placeTable(map, GameConfig.MAP_ROWS - 1, column);
        }
        for (int row = 3; row < 5; row++) {
            placeTable(map, row, 5);
        }

        map.setTile(0, 1, new Tile(0, 1, TileType.INGREDIENT_SOURCE));
        map.addItem(new Plate(130, 130));
    }

    default void placeTable(GameMap map, int row, int column) {
        map.setTile(row, column, new Table(row, column));
    }
}
