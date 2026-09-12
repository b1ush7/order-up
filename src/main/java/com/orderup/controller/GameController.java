package com.orderup.controller;

import com.orderup.config.GameConfig;
import com.orderup.model.Direction;
import com.orderup.model.GameMap;
import com.orderup.model.GameState;
import com.orderup.model.InteractionArea;
import com.orderup.model.InteractionResult;
import com.orderup.model.Player;
import com.orderup.model.Tile;
import com.orderup.service.GameService;
import com.orderup.service.Impl.GameServiceImpl;
import com.orderup.service.Impl.KitchenServiceImpl;
import com.orderup.service.Impl.PlayerServiceImpl;
import com.orderup.service.PlayerService;
import com.orderup.util.GameTimer;

/**
 * 编排一局游戏，不包含 JavaFX 显示代码。
 */
public class GameController {
    private final Player player;
    private final InteractionArea interactionArea;
    private final GameMap gameMap;
    private final PlayerService playerService;
    private final com.orderup.service.KitchenService kitchenService;
    private final GameTimer gameTimer;
    private final Runnable onGameFinished;

    private GameState state = GameState.READY;

    /**
     * 创建一局新游戏，并初始化玩家、地图、交互区域和服务。
     *
     * @param onGameFinished 游戏结束后执行的回调，通常用于切换到结算页
     */
    public GameController(Runnable onGameFinished) {
        this.onGameFinished = onGameFinished;

        GameService gameService = new GameServiceImpl();
        playerService = new PlayerServiceImpl();
        kitchenService = new KitchenServiceImpl();

        player = new Player(GameConfig.PLAYER_START_X, GameConfig.PLAYER_START_Y);
        interactionArea = new InteractionArea();
        gameMap = gameService.createMap();
        gameTimer = new GameTimer(this::finishGame);
        interactionArea.updateFrom(player);
    }

    /**
     * 将游戏切换为运行状态，并按配置的总时长启动倒计时。
     */
    public void startGame() {
        state = GameState.RUNNING;
        gameTimer.start(GameConfig.GAME_SECONDS);
    }

    /**
     * 更新一次游戏逻辑：移动玩家、同步交互区和手持物品、
     * 刷新可交互格子，最后推进倒计时。
     *
     * @param deltaSeconds 本次逻辑更新要推进的秒数
     */
    public void update(double deltaSeconds) {
        if (state != GameState.RUNNING) {
            return;
        }
        playerService.move(
                player,
                deltaSeconds,
                GameConfig.WINDOW_WIDTH,
                GameConfig.WINDOW_HEIGHT,
                gameMap
        );
        interactionArea.updateFrom(player);
        kitchenService.updateHeldItem(player, interactionArea);
        updateInteractableTiles();
        gameTimer.update(deltaSeconds);
    }

    /**
     * 记录一个移动方向已被按下。
     *
     * @param direction 按下的移动方向
     */
    public void press(Direction direction) {
        player.press(direction);
    }

    /**
     * 记录一个移动方向已被松开。
     *
     * @param direction 松开的移动方向
     */
    public void release(Direction direction) {
        player.release(direction);
    }

    /**
     * 清空当前所有移动输入，防止窗口失去焦点后玩家继续移动。
     */
    public void clearInput() {
        player.clearInput();
    }

    /**
     * 让玩家与面前的设施或物品交互。
     *
     * @return 交互是否成功及对应提示信息
     */
    public InteractionResult interact() {
        if (state != GameState.RUNNING) {
            return InteractionResult.failed("游戏未运行");
        }
        return kitchenService.interact(player, interactionArea, gameMap);
    }

    /**
     * 正常结束本局游戏，停止运行状态后通知显示层切换页面。
     */
    public void finishGame() {
        if (state == GameState.FINISHED) {
            return;
        }
        stopGame();
        onGameFinished.run();
    }

    /**
     * 停止本局游戏和倒计时，但不触发页面切换回调。
     */
    public void stopGame() {
        state = GameState.FINISHED;
        gameTimer.stop();
        player.clearInput();
    }

    /**
     * 根据玩家面前的交互区域，刷新每个地图格子的范围标记。
     */
    private void updateInteractableTiles() {
        for (Tile[] row : gameMap.getTiles()) {
            for (Tile tile : row) {
                tile.setInteractable(interactionArea.intersects(
                        tile.getX(),
                        tile.getY(),
                        tile.getSize(),
                        tile.getSize()
                ));
            }
        }
    }

    /** @return 本局游戏的玩家对象 */
    public Player getPlayer() {
        return player;
    }

    /** @return 玩家当前面朝方向的交互检测区域 */
    public InteractionArea getInteractionArea() {
        return interactionArea;
    }

    /** @return 本局游戏使用的地图 */
    public GameMap getGameMap() {
        return gameMap;
    }

    /** @return 本局倒计时剩余的整秒数 */
    public int getRemainingSeconds() {
        return gameTimer.getRemainingSeconds();
    }

    /** @return 当前游戏状态 */
    public GameState getState() {
        return state;
    }

}
