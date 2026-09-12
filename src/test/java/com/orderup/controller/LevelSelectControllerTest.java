package com.orderup.controller;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LevelSelectControllerTest {
    @Test
    void sendsSelectedLevelToNavigation() {
        AtomicInteger selectedLevel = new AtomicInteger();
        LevelSelectController controller = new LevelSelectController();
        controller.configure(selectedLevel::set, () -> { });

        controller.onLevelOneButtonClick();
        assertEquals(1, selectedLevel.get());

        controller.onLevelTwoButtonClick();
        assertEquals(2, selectedLevel.get());
    }

    @Test
    void returnsToMainMenu() {
        AtomicBoolean returned = new AtomicBoolean();
        LevelSelectController controller = new LevelSelectController();
        controller.configure(level -> { }, () -> returned.set(true));

        controller.onBackButtonClick();

        assertTrue(returned.get());
    }
}
