package com.orderup.config;

import com.orderup.model.DishType;
import com.orderup.model.Ingredient;
import com.orderup.model.IngredientStatus;
import com.orderup.model.IngredientType;
import com.orderup.model.Recipe;

import java.util.List;
import java.util.Set;

/**
 * 游戏运行参数的唯一来源。
 */
public final class GameConfig {
    public static final double WINDOW_WIDTH = 1280;
    public static final double WINDOW_HEIGHT = 720;
    public static final int MAP_ROWS = 9;
    public static final int MAP_COLUMNS = 13;
    public static final int TILE_SIZE = 80;
    public static final int DEFAULT_LEVEL = 1;
    public static final int GAME_SECONDS = 600;
    public static final double FIXED_STEP_SECONDS = 1.0 / 60.0;
    public static final double MAX_ACCUMULATED_SECONDS = 0.25;
    public static final double PLAYER_START_X = 200;
    public static final double PLAYER_START_Y = 200;
    public static final List<Recipe> RECIPES = List.of(
            new Recipe(DishType.SASHIMI, 100, 30, Set.of(new Ingredient(IngredientType.FISH, IngredientStatus.CUT))),
            new Recipe(DishType.ROLL, 150, 45, Set.of(
                    new Ingredient(IngredientType.KELP, IngredientStatus.RAW),
                    new Ingredient(IngredientType.RICE, IngredientStatus.COOKED)
            ))
    );

    private static final List<IngredientSourceConfig> LEVEL_1_SOURCES = List.of(
            new IngredientSourceConfig(0, 1, IngredientType.FISH),
            new IngredientSourceConfig(0, 2, IngredientType.RICE)
    );

    private static final List<IngredientSourceConfig> LEVEL_2_SOURCES = List.of(
            new IngredientSourceConfig(0, 1, IngredientType.FISH),
            new IngredientSourceConfig(0, 2, IngredientType.RICE),
            new IngredientSourceConfig(0, 3, IngredientType.KELP)
    );

    private GameConfig() {
    }

    /**
     * 返回指定关卡中所有食材源的位置和食材类型。
     *
     * @param level 关卡编号，当前只支持 1 和 2
     * @return 对应关卡的食材源配置
     */
    public static List<IngredientSourceConfig> getIngredientSources(int level) {
        return switch (level) {
            case 1 -> LEVEL_1_SOURCES;
            case 2 -> LEVEL_2_SOURCES;
            default -> throw new IllegalArgumentException("不存在的关卡：" + level);
        };
    }

    /**
     * 一个食材源的静态地图配置。
     */
    public record IngredientSourceConfig(int row, int column, IngredientType ingredientType) {
    }
}
