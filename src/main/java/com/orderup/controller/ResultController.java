package com.orderup.controller;

public class ResultController {
    private Runnable restartGame = () -> { };
    private Runnable returnToMenu = () -> { };

    /**
     * 注入结算页所需的页面操作。
     *
     * @param restartGame 重新开始一局游戏的回调
     * @param returnToMenu 返回开始菜单的回调
     */
    public void configure(Runnable restartGame, Runnable returnToMenu) {
        this.restartGame = restartGame;
        this.returnToMenu = returnToMenu;
    }

    /**
     * 处理 FXML 中的“重新开始”按钮事件。
     */
    public void onRestartButtonClick() {
        restartGame.run();
    }

    /**
     * 处理 FXML 中的“返回菜单”按钮事件。
     */
    public void onMenuButtonClick() {
        returnToMenu.run();
    }
}
