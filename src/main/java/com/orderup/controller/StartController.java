package com.orderup.controller;

public class StartController {
    private Runnable startGame = () -> { };
    private Runnable openSettings = () -> { };
    private Runnable quitGame = () -> { };

    /**
     * 注入开始菜单所需的页面操作。
     *
     * @param startGame 点击开始游戏后执行的回调
     * @param openSettings 点击设置后执行的回调
     * @param quitGame  点击退出游戏后执行的回调
     */
    public void configure(Runnable startGame, Runnable openSettings, Runnable quitGame) {
        this.startGame = startGame;
        this.openSettings = openSettings;
        this.quitGame = quitGame;
    }

    /**
     * 处理 FXML 中的“开始游戏”按钮事件。
     */
    public void onStartButtonClick() {
        startGame.run();
    }

    /**
     * 处理 FXML 中的“游戏设置”按钮事件。
     */
    public void onSettingsButtonClick() {
        openSettings.run();
    }

    /**
     * 处理 FXML 中的“退出游戏”按钮事件。
     */
    public void onQuitButtonClick() {
        quitGame.run();
    }
}
