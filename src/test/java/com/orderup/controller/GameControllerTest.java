package com.orderup.controller;

import com.orderup.model.GameState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameControllerTest {
    @Test
    void pauseStopsUpdatesUntilGameResumes() {
        GameController controller = new GameController(1, () -> { });
        controller.startGame();
        int initialSeconds = controller.getRemainingSeconds();

        controller.pauseGame();
        controller.update(2);

        assertEquals(GameState.PAUSED, controller.getState());
        assertEquals(initialSeconds, controller.getRemainingSeconds());

        controller.resumeGame();
        controller.update(1);

        assertEquals(GameState.RUNNING, controller.getState());
        assertEquals(initialSeconds - 1, controller.getRemainingSeconds());
    }
}
