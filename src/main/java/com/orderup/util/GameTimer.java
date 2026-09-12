package com.orderup.util;

/**
 * 与显示框架无关的游戏倒计时，由游戏循环传入每帧经过的秒数。
 */
public class GameTimer {
    private final Runnable onTimeUp;

    private int remainingSeconds;
    private double accumulatedSeconds;
    private boolean running;

    public GameTimer(Runnable onTimeUp) {
        this.onTimeUp = onTimeUp;
    }

    public void start(int totalSeconds) {
        if (totalSeconds <= 0) {
            throw new IllegalArgumentException("Countdown seconds must be greater than zero.");
        }

        remainingSeconds = totalSeconds;
        accumulatedSeconds = 0;
        running = true;
    }

    public void update(double deltaSeconds) {
        if (!running) {
            return;
        }
        if (deltaSeconds < 0) {
            throw new IllegalArgumentException("Delta seconds cannot be negative.");
        }

        accumulatedSeconds += deltaSeconds;
        while (running && accumulatedSeconds >= 1) {
            accumulatedSeconds -= 1;
            remainingSeconds--;

            if (remainingSeconds <= 0) {
                remainingSeconds = 0;
                running = false;
                onTimeUp.run();
            }
        }
    }

    public void stop() {
        running = false;
    }

    public int getRemainingSeconds() {
        return remainingSeconds;
    }
}
