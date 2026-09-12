package com.orderup.util;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameTimerTest {
    @Test
    void countsDownUsingAccumulatedFrameTime() {
        AtomicInteger callbacks = new AtomicInteger();
        GameTimer timer = new GameTimer(callbacks::incrementAndGet);

        timer.start(2);
        timer.update(0.5);
        assertEquals(2, timer.getRemainingSeconds());

        timer.update(1.5);
        assertEquals(0, timer.getRemainingSeconds());
        assertEquals(1, callbacks.get());
    }

    @Test
    void rejectsInvalidTimeValues() {
        GameTimer timer = new GameTimer(() -> { });

        assertThrows(IllegalArgumentException.class, () -> timer.start(0));
        timer.start(1);
        assertThrows(IllegalArgumentException.class, () -> timer.update(-0.1));
    }
}
