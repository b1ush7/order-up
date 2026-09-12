package com.orderup.view;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

import java.util.function.Consumer;

/**
 * 设置页面的 JavaFX 显示层，负责读取控件状态并通知 Launcher 应用设置。
 */
public class SettingsView {
    @FXML
    private CheckBox soundCheckBox;
    @FXML
    private CheckBox fullScreenCheckBox;

    private Consumer<Boolean> changeSound = enabled -> { };
    private Consumer<Boolean> changeFullScreen = enabled -> { };
    private Runnable returnToMenu = () -> { };

    /**
     * 显示当前设置并注入设置变更、返回菜单所需的回调。
     */
    public void configure(
            boolean soundEnabled,
            boolean fullScreen,
            Consumer<Boolean> changeSound,
            Consumer<Boolean> changeFullScreen,
            Runnable returnToMenu
    ) {
        this.changeSound = changeSound;
        this.changeFullScreen = changeFullScreen;
        this.returnToMenu = returnToMenu;
        soundCheckBox.setSelected(soundEnabled);
        fullScreenCheckBox.setSelected(fullScreen);
    }

    @FXML
    private void onSoundChanged() {
        changeSound.accept(soundCheckBox.isSelected());
    }

    @FXML
    private void onFullScreenChanged() {
        changeFullScreen.accept(fullScreenCheckBox.isSelected());
    }

    @FXML
    private void onBackButtonClick() {
        returnToMenu.run();
    }
}
