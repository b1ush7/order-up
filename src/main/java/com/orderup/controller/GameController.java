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

    public void startGame() {
        state = GameState.RUNNING;
        gameTimer.start(GameConfig.GAME_SECONDS);
    }

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

    public void press(Direction direction) {
        player.press(direction);
    }

    public void release(Direction direction) {
        player.release(direction);
    }

    public void clearInput() {
        player.clearInput();
    }

    public InteractionResult interact() {
        if (state != GameState.RUNNING) {
            return InteractionResult.failed("游戏未运行");
        }
        return kitchenService.interact(player, interactionArea, gameMap);
    }

    public void finishGame() {
        if (state == GameState.FINISHED) {
            return;
        }
        stopGame();
        onGameFinished.run();
    }

    public void stopGame() {
        state = GameState.FINISHED;
        gameTimer.stop();
        player.clearInput();
    }

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

    public Player getPlayer() {
        return player;
    }

    public InteractionArea getInteractionArea() {
        return interactionArea;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public int getRemainingSeconds() {
        return gameTimer.getRemainingSeconds();
    }

    public GameState getState() {
        return state;
    }

}
