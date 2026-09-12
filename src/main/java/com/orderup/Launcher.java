package com.orderup;

import com.orderup.config.GameConfig;
import com.orderup.controller.ResultController;
import com.orderup.controller.StartController;
import com.orderup.view.GameView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * Order Up 的 JavaFX 启动入口，负责窗口和页面切换。
 */
public class Launcher extends Application {
    private static final String START_FXML = "/com/orderup/fxml/start.fxml";
    private static final String GAME_FXML = "/com/orderup/fxml/game.fxml";
    private static final String RESULT_FXML = "/com/orderup/fxml/result.fxml";
    private static final String MENU_MUSIC = "/com/orderup/audio/startmenu.mp3";

    private Stage primaryStage;
    private MediaPlayer menuMusic;
    private GameView currentGameView;

    public static void main(String[] args) {
        Application.launch(Launcher.class, args);
    }

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        stage.setTitle("Order Up!");
        stage.setMinWidth(960);
        stage.setMinHeight(540);
        showStartScene();
        stage.show();
    }

    public void showStartScene() {
        showScene(START_FXML);
        playMenuMusic();
    }

    public void showGameScene() {
        stopMenuMusic();
        showScene(GAME_FXML);
    }

    public void showResultScene() {
        stopMenuMusic();
        showScene(RESULT_FXML);
    }

    private void showScene(String fxmlPath) {
        URL resource = Launcher.class.getResource(fxmlPath);
        if (resource == null) {
            throw new IllegalStateException("FXML resource was not found: " + fxmlPath);
        }

        try {
            FXMLLoader loader = new FXMLLoader(resource);
            Parent root = loader.load();
            Object nextController = loader.getController();

            if (currentGameView != null) {
                currentGameView.dispose();
                currentGameView = null;
            }
            configureController(nextController);

            Scene scene = primaryStage.getScene();
            if (scene == null) {
                primaryStage.setScene(new Scene(
                        root,
                        GameConfig.WINDOW_WIDTH,
                        GameConfig.WINDOW_HEIGHT
                ));
            } else {
                scene.setRoot(root);
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to load scene: " + fxmlPath, exception);
        }
    }

    private void configureController(Object controller) {
        if (controller instanceof StartController startController) {
            startController.configure(this::showGameScene, Platform::exit);
        } else if (controller instanceof ResultController resultController) {
            resultController.configure(this::showGameScene, this::showStartScene);
        } else if (controller instanceof GameView gameView) {
            gameView.setOnGameFinished(this::showResultScene);
            currentGameView = gameView;
        }
    }

    private void playMenuMusic() {
        if (menuMusic == null) {
            URL resource = Launcher.class.getResource(MENU_MUSIC);
            if (resource == null) {
                System.err.println("Start menu music was not found.");
                return;
            }
            menuMusic = new MediaPlayer(new Media(resource.toExternalForm()));
            menuMusic.setCycleCount(MediaPlayer.INDEFINITE);
            menuMusic.setVolume(0.35);
        }
        menuMusic.play();
    }

    private void stopMenuMusic() {
        if (menuMusic != null) {
            menuMusic.stop();
        }
    }

    @Override
    public void stop() {
        if (currentGameView != null) {
            currentGameView.dispose();
        }
        if (menuMusic != null) {
            menuMusic.dispose();
        }
    }
}
