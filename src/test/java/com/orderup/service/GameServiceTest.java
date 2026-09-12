package com.orderup.service;

import com.orderup.model.GameMap;
import com.orderup.model.IngredientSource;
import com.orderup.model.IngredientType;
import com.orderup.model.Plate;
import com.orderup.model.Table;
import com.orderup.service.Impl.GameServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameServiceTest {
    @Test
    void createsTheFirstMapWithTablesSourceAndItem() {
        GameMap map = new GameServiceImpl().createMap();

        assertInstanceOf(Table.class, map.getTile(0, 0));
        IngredientSource fishSource = assertInstanceOf(
                IngredientSource.class,
                map.getTile(0, 1)
        );
        assertEquals(IngredientType.FISH, fishSource.getIngredientType());
        IngredientSource riceSource = assertInstanceOf(
                IngredientSource.class,
                map.getTile(0, 2)
        );
        assertEquals(IngredientType.RICE, riceSource.getIngredientType());
        assertInstanceOf(Table.class, map.getTile(4, 5));
        assertInstanceOf(Plate.class, map.getItems().get(0));
    }

    @Test
    void createsTheSecondMapWithAKelpSource() {
        GameMap map = new GameServiceImpl().createMap(2);

        IngredientSource kelpSource = assertInstanceOf(
                IngredientSource.class,
                map.getTile(0, 3)
        );
        assertEquals(IngredientType.KELP, kelpSource.getIngredientType());
    }

    @Test
    void rejectsAnUnknownLevel() {
        GameService service = new GameServiceImpl();

        assertThrows(IllegalArgumentException.class, () -> service.createMap(3));
    }
}
