package com.orderup.service;

import com.orderup.config.GameConfig;
import com.orderup.model.*;

public interface GameService {
    /**
     * 按默认关卡创建并初始化地图。
     *
     * @return 包含地板、设施和初始物品的地图
     */
    default GameMap createMap() {
        return createMap(GameConfig.DEFAULT_LEVEL);
    }

    /**
     * 按指定关卡创建并初始化地图。
     *
     * @param level 关卡编号
     * @return 包含该关卡食材源和设施的地图
     */
    GameMap createMap(int level);

    /**
     * 在地图上放置边界桌面、中央桌面、食材源和初始盘子。
     *
     * @param map 要配置的空白地图
     * @param level 关卡编号
     */
    default void configureMap(GameMap map, int level) {
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

        for (GameConfig.IngredientSourceConfig source : GameConfig.getIngredientSources(level)) {
            map.setTile(
                    source.row(),
                    source.column(),
                    new IngredientSource(source.row(), source.column(), source.ingredientType())
            );
        }
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
