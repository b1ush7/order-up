package com.orderup.service;

import com.orderup.config.GameConfig;
import com.orderup.model.*;

public interface GameService {
    /**
     * 创建并完成一张新地图的初始化。
     *
     * @return 包含地板、设施和初始物品的地图
     */
    GameMap createMap();

    /**
     * 在地图上放置边界桌面、中央桌面、食材源和初始盘子。
     *
     * @param map 要配置的空白地图
     */
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

    /**
     * 将地图的指定格子替换为桌面。
     *
     * @param map 目标地图
     * @param row 格子行号
     * @param column 格子列号
     */
    default void placeTable(GameMap map, int row, int column) {
        map.setTile(row, column, new Table(row, column));
    }
}
