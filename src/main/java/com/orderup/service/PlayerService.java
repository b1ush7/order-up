package com.orderup.service;

import com.orderup.model.GameMap;
import com.orderup.model.Player;

public interface PlayerService {
    void move(
            Player player,
            double deltaSeconds,
            double worldWidth,
            double worldHeight,
            GameMap map
    );
}
