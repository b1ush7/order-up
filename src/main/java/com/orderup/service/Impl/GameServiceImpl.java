package com.orderup.service.Impl;

import com.orderup.model.GameMap;
import com.orderup.service.GameService;

/**
 * 创建并初始化关卡地图。
 */
public class GameServiceImpl implements GameService {
    /** {@inheritDoc} */
    @Override
    public GameMap createMap(int level) {
        GameMap map = new GameMap();
        configureMap(map, level);
        return map;
    }

}
