package com.orderup.view;

import com.orderup.config.GameConfig;
import com.orderup.controller.GameController;
import com.orderup.model.Direction;
import com.orderup.model.GameState;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.layout.StackPane;

/**
 * 游戏页面的 JavaFX 显示层：接收输入、驱动主循环并绘制画面。
 */
public class GameView {
    // FXML 只注入显示控件；游戏数据由 GameController 管理。
    @FXML
    private Canvas gameCanvas;
    @FXML
    private Label timeLabel;
    @FXML
    private StackPane gameRoot;
    @FXML
    private StackPane pauseOverlay;
    @FXML
    private Button continueButton;

    private final GameMapView gameMapView = new GameMapView();
    private final InteractionAreaView interactionAreaView = new InteractionAreaView();
    private final GameItemView gameItemView = new GameItemView();
    private final PlayerView playerView = new PlayerView();

    // 场景切换由 Launcher 通过回调注入，View 不直接依赖 Launcher。
    private Runnable onGameFinished = () -> { };
    private Runnable returnToMenu = () -> { };
    private Runnable returnToLevelSelect = () -> { };
    private GameController controller;
    private AnimationTimer gameLoop;
    private long lastTime;
    private double accumulatedSeconds;
    private int lastRenderedSeconds = -1;
    private boolean interactKeyPressed;
    private boolean pauseKeyPressed;
    private boolean disposed;

    /**
     * 按选中的关卡创建游戏，并在 FXML 控件加载完成后启动主循环。
     *
     * @param level 关卡编号
     * @param onGameFinished 游戏结束后切换页面的回调
     * @param returnToMenu 返回主菜单的回调
     * @param returnToLevelSelect 返回关卡选择页的回调
     */
    public void configure(
            int level,
            Runnable onGameFinished,
            Runnable returnToMenu,
            Runnable returnToLevelSelect
    ) {
        if (controller != null) {
            throw new IllegalStateException("GameView 已经配置过");
        }
        this.onGameFinished = onGameFinished;
        this.returnToMenu = returnToMenu;
        this.returnToLevelSelect = returnToLevelSelect;
        controller = new GameController(level, this::finishGame);
        controller.startGame();

        configureInput();
        renderFrame(gameCanvas.getGraphicsContext2D());
        startGameLoop();
        Platform.runLater(gameCanvas::requestFocus);
    }

    private void configureInput() {
        gameCanvas.setFocusTraversable(true);
        gameRoot.addEventFilter(KeyEvent.KEY_PRESSED, this::onKeyPressed);
        gameRoot.addEventFilter(KeyEvent.KEY_RELEASED, this::onKeyReleased);
        gameCanvas.focusedProperty().addListener((observable, wasFocused, isFocused) -> {
            if (!isFocused) {
                controller.clearInput();
                interactKeyPressed = false;
                pauseKeyPressed = false;
            }
        });
    }

    private void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            if (!pauseKeyPressed) {
                pauseKeyPressed = true;
                togglePause();
            }
            event.consume();
            return;
        }

        if (controller.getState() != GameState.RUNNING) {
            if (event.getCode() == KeyCode.E || toDirection(event.getCode()) != null) {
                event.consume();
            }
            return;
        }

        if (event.getCode() == KeyCode.E) {
            // 按住 E 时 JavaFX 会重复发送事件，这里限制为每次按下只交互一次。
            if (!interactKeyPressed) {
                interactKeyPressed = true;
                controller.interact();
            }
            event.consume();
            return;
        }

        Direction direction = toDirection(event.getCode());
        if (direction != null) {
            controller.press(direction);
            event.consume();
        }
    }

    private void onKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            pauseKeyPressed = false;
            event.consume();
            return;
        }

        if (event.getCode() == KeyCode.E) {
            interactKeyPressed = false;
            event.consume();
            return;
        }

        Direction direction = toDirection(event.getCode());
        if (direction != null) {
            controller.release(direction);
            event.consume();
        }
    }

    private Direction toDirection(KeyCode keyCode) {
        return switch (keyCode) {
            case W -> Direction.UP;
            case S -> Direction.DOWN;
            case A -> Direction.LEFT;
            case D -> Direction.RIGHT;
            default -> null;
        };
    }

    /**
     * JavaFX 负责触发渲染；累加器保证游戏逻辑按固定 60 Hz 更新。
     */
    private void startGameLoop() {
        GraphicsContext graphics = gameCanvas.getGraphicsContext2D();
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                double elapsedSeconds = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;
                if (controller.getState() != GameState.RUNNING) {
                    accumulatedSeconds = 0;
                    renderFrame(graphics);
                    return;
                }
                accumulatedSeconds += Math.min(
                        elapsedSeconds,
                        GameConfig.MAX_ACCUMULATED_SECONDS
                );

                while (!disposed
                        && controller.getState() == GameState.RUNNING
                        && accumulatedSeconds >= GameConfig.FIXED_STEP_SECONDS) {
                    controller.update(GameConfig.FIXED_STEP_SECONDS);
                    accumulatedSeconds -= GameConfig.FIXED_STEP_SECONDS;
                }

                if (!disposed) {
                    renderFrame(graphics);
                }
            }
        };
        gameLoop.start();
    }

    @FXML
    private void onPauseButtonClick() {
        pauseGame();
    }

    @FXML
    private void onContinueButtonClick() {
        resumeGame();
    }

    @FXML
    private void onMenuButtonClick() {
        returnToMenu.run();
    }

    @FXML
    private void onLevelSelectButtonClick() {
        returnToLevelSelect.run();
    }

    private void togglePause() {
        if (controller.getState() == GameState.PAUSED) {
            resumeGame();
        } else if (controller.getState() == GameState.RUNNING) {
            pauseGame();
        }
    }

    private void pauseGame() {
        controller.pauseGame();
        interactKeyPressed = false;
        setPauseOverlayVisible(true);
        Platform.runLater(continueButton::requestFocus);
    }

    private void resumeGame() {
        controller.resumeGame();
        accumulatedSeconds = 0;
        lastTime = 0;
        setPauseOverlayVisible(false);
        Platform.runLater(gameCanvas::requestFocus);
    }

    private void setPauseOverlayVisible(boolean visible) {
        pauseOverlay.setManaged(visible);
        pauseOverlay.setVisible(visible);
    }

    private void renderFrame(GraphicsContext graphics) {
        // 绘制顺序即层级顺序：地图 -> 交互区 -> 物品 -> 玩家 -> HUD。
        graphics.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        gameMapView.render(graphics, controller.getGameMap());
        interactionAreaView.render(graphics, controller.getInteractionArea());
        gameItemView.render(graphics, controller.getGameMap().getItems());
        playerView.render(graphics, controller.getPlayer());
        renderTime(controller.getRemainingSeconds());
    }

    private void renderTime(int totalSeconds) {
        if (totalSeconds == lastRenderedSeconds) {
            return;
        }
        lastRenderedSeconds = totalSeconds;
        timeLabel.setText(String.format("%02d:%02d", totalSeconds / 60, totalSeconds % 60));
        timeLabel.setTextFill(totalSeconds <= 10 ? Color.RED : Color.WHITE);
    }

    private void finishGame() {
        stopGameLoop();
        onGameFinished.run();
    }

    private void stopGameLoop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }

    public void dispose() {
        // 换页时同时停止 JavaFX 循环和业务状态，避免旧页面继续更新。
        disposed = true;
        stopGameLoop();
        if (controller != null) {
            controller.stopGame();
        }
    }
}
