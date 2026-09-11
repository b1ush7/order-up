package com.orderup.service.Impl;

import com.orderup.model.GameMap;
import com.orderup.service.GameService;

/**
 * 创建并初始化关卡地图。
 */
public class GameServiceImpl implements GameService {
    @Override
    public GameMap createMap() {
        GameMap map = new GameMap();
        configureMap(map);
        return map;
    }

}
