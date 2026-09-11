package com.orderup.config;

/**
 * 游戏运行参数的唯一来源。
 */
public final class GameConfig {
    public static final double WINDOW_WIDTH = 1280;
    public static final double WINDOW_HEIGHT = 720;
    public static final int MAP_ROWS = 9;
    public static final int MAP_COLUMNS = 13;
    public static final int TILE_SIZE = 80;
    public static final int GAME_SECONDS = 600;
    public static final double FIXED_STEP_SECONDS = 1.0 / 60.0;
    public static final double MAX_ACCUMULATED_SECONDS = 0.25;
    public static final double PLAYER_START_X = 200;
    public static final double PLAYER_START_Y = 200;

    private GameConfig() {
    }
}
