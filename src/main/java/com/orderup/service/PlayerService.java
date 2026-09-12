package com.orderup.service;

import com.orderup.model.GameMap;
import com.orderup.model.Player;

public interface PlayerService {
    /**
     * 根据玩家当前按键状态计算移动，并处理斜向速度、世界边界和设施碰撞。
     *
     * @param player 要移动的玩家
     * @param deltaSeconds 本次逻辑更新要推进的秒数
     * @param worldWidth 玩家可移动区域的宽度
     * @param worldHeight 玩家可移动区域的高度
     * @param map 用于碰撞检测的地图
     */
    void move(
            Player player,
            double deltaSeconds,
            double worldWidth,
            double worldHeight,
            GameMap map
    );
}
