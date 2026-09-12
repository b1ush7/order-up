package com.orderup.service;

import com.orderup.model.Direction;
import com.orderup.model.GameMap;
import com.orderup.model.Player;
import com.orderup.service.Impl.GameServiceImpl;
import com.orderup.service.Impl.PlayerServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerServiceTest {
    private final PlayerService service = new PlayerServiceImpl();

    @Test
    void movesAtTheConfiguredSpeed() {
        Player player = new Player(200, 200);
        player.press(Direction.RIGHT);

        service.move(player, 1, 1280, 720, new GameMap());

        assertEquals(420, player.getX(), 0.001);
        assertEquals(200, player.getY(), 0.001);
    }

    @Test
    void normalizesDiagonalMovement() {
        Player player = new Player(200, 200);
        player.press(Direction.RIGHT);
        player.press(Direction.DOWN);

        service.move(player, 1, 1280, 720, new GameMap());

        double expected = 200 + 220 / Math.sqrt(2);
        assertEquals(expected, player.getX(), 0.001);
        assertEquals(expected, player.getY(), 0.001);
    }

    @Test
    void doesNotMoveThroughBlockingTiles() {
        Player player = new Player(90, 82);
        player.press(Direction.UP);

        service.move(player, 0.02, 1280, 720, new GameServiceImpl().createMap());

        assertEquals(82, player.getY(), 0.001);
    }
}
