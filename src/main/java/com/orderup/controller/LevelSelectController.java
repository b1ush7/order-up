package com.orderup.controller;

import java.util.function.IntConsumer;

/**
 * 接收关卡选择页的操作，并把选择结果交给页面导航层。
 */
public class LevelSelectController {
    private IntConsumer startLevel = level -> { };
    private Runnable returnToMenu = () -> { };

    /**
     * 注入关卡选择页所需的页面操作。
     *
     * @param startLevel 选择关卡后执行的回调，参数为关卡编号
     * @param returnToMenu 返回主菜单的回调
     */
    public void configure(IntConsumer startLevel, Runnable returnToMenu) {
        this.startLevel = startLevel;
        this.returnToMenu = returnToMenu;
    }

    /** 进入第一关。 */
    public void onLevelOneButtonClick() {
        startLevel.accept(1);
    }

    /** 进入第二关。 */
    public void onLevelTwoButtonClick() {
        startLevel.accept(2);
    }

    /** 返回主菜单。 */
    public void onBackButtonClick() {
        returnToMenu.run();
    }
}
