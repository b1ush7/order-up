package com.orderup.service;

import com.orderup.model.GameMap;
import com.orderup.model.Plate;
import com.orderup.model.Table;
import com.orderup.model.TileType;
import com.orderup.service.Impl.GameServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class GameServiceTest {
    @Test
    void createsTheFirstMapWithTablesSourceAndItem() {
        GameMap map = new GameServiceImpl().createMap();

        assertInstanceOf(Table.class, map.getTile(0, 0));
        assertEquals(TileType.INGREDIENT_SOURCE, map.getTile(0, 1).getType());
        assertInstanceOf(Table.class, map.getTile(4, 5));
        assertInstanceOf(Plate.class, map.getItems().get(0));
    }
}
