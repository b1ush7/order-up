package com.orderup.service;

import com.orderup.model.*;

public interface KitchenService {
    /**
     * 根据玩家手持状态和面前目标，执行拾取、放下、取食材或桌面交互。
     *
     * @param player 发起交互的玩家
     * @param area   玩家面前的交互检测区域
     * @param map    当前游戏地图
     * @return 交互是否成功及提示信息
     */
    InteractionResult interact(Player player, InteractionArea area, GameMap map);

    /**
     * 将玩家手持物品的位置同步到交互区域。
     *
     * @param player 可能持有物品的玩家
     * @param area 物品应跟随的交互区域
     */
    void updateHeldItem(Player player, InteractionArea area);

    /**
     * 将玩家手中物品放到桌面，或放在当前交互位置。
     *
     * @param player 持有物品的玩家
     * @param area 放下物品时使用的位置
     * @param map 当前游戏地图
     * @param tile 交互区域命中的格子，可能为 {@code null}
     * @return 放置结果及提示信息
     */
    InteractionResult placeOrDrop(
            Player player,
            InteractionArea area,
            GameMap map,
            Tile tile
    );

    /**
     * 处理玩家手持物品与桌面现有物品之间的放置或装盘。
     *
     * @param player 发起交互的玩家
     * @param table 目标桌面
     * @param map 当前游戏地图
     * @return 桌面交互结果
     */
    InteractionResult interactWithTable(Player player, Table table, GameMap map);

    /**
     * 从食材源创建一份食材，加入地图并交给玩家。
     *
     * @param player 拿取食材的玩家
     * @param area 新食材的初始位置
     * @param map 当前游戏地图
     * @param source 本次交互的食材源
     * @return 取得食材的结果
     */
    InteractionResult takeIngredientFromSource(
            Player player,
            InteractionArea area,
            GameMap map,
            IngredientSource source
    );

    /**
     * 根据交互区域的中心点确定唯一目标格子。
     *
     * @param area 交互检测区域
     * @param map 当前游戏地图
     * @return 命中的格子；没有命中时返回 {@code null}
     */
    Tile findTile(InteractionArea area, GameMap map);
}
