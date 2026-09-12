package com.orderup.service.Impl;

import com.orderup.config.GameConfig;
import com.orderup.model.Direction;
import com.orderup.model.GameMap;
import com.orderup.model.Player;
import com.orderup.model.Tile;

/**
 * 根据输入移动玩家并处理地图碰撞。
 */
public class PlayerServiceImpl implements com.orderup.service.PlayerService {
    /** {@inheritDoc} */
    @Override
    public void move(
            Player player,
            double deltaSeconds,
            double worldWidth,
            double worldHeight,
            GameMap map
    ) {
        if (deltaSeconds < 0) {
            throw new IllegalArgumentException("Delta seconds cannot be negative.");
        }

        double dx = horizontalInput(player);
        double dy = verticalInput(player);
        if (dx != 0 && dy != 0) {
            double diagonalScale = 1.0 / Math.sqrt(2);
            dx *= diagonalScale;
            dy *= diagonalScale;
        }

        double distance = player.getSpeed() * deltaSeconds;
        double nextX = clamp(player.getX() + dx * distance, 0, worldWidth - Player.WIDTH);
        if (!collidesWithBlockingTile(nextX, player.getY(), map)) {
            player.setPosition(nextX, player.getY());
        }

        double nextY = clamp(player.getY() + dy * distance, 0, worldHeight - Player.HEIGHT);
        if (!collidesWithBlockingTile(player.getX(), nextY, map)) {
            player.setPosition(player.getX(), nextY);
        }
    }

    /**
     * 将左右按键状态合并为水平方向值。
     *
     * @param player 输入状态的来源
     * @return 向左为 -1，向右为 1，抵消或无输入为 0
     */
    private double horizontalInput(Player player) {
        double direction = 0;
        if (player.isMoving(Direction.LEFT)) {
            direction--;
        }
        if (player.isMoving(Direction.RIGHT)) {
            direction++;
        }
        return direction;
    }

    /**
     * 将上下按键状态合并为垂直方向值。
     *
     * @param player 输入状态的来源
     * @return 向上为 -1，向下为 1，抵消或无输入为 0
     */
    private double verticalInput(Player player) {
        double direction = 0;
        if (player.isMoving(Direction.UP)) {
            direction--;
        }
        if (player.isMoving(Direction.DOWN)) {
            direction++;
        }
        return direction;
    }

    /**
     * 检查玩家放在候选坐标后是否会与任意阻挡格子重叠。
     *
     * @param x 玩家候选 X 坐标
     * @param y 玩家候选 Y 坐标
     * @param map 用于查找阻挡格子的地图
     * @return 存在碰撞时返回 {@code true}
     */
    private boolean collidesWithBlockingTile(double x, double y, GameMap map) {
        for (int row = 0; row < GameConfig.MAP_ROWS; row++) {
            for (int column = 0; column < GameConfig.MAP_COLUMNS; column++) {
                Tile tile = map.getTile(row, column);
                if (tile.getType().isBlocking() && intersects(x, y, tile)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 使用轴对齐矩形检测玩家与一个格子是否相交。
     */
    private boolean intersects(double x, double y, Tile tile) {
        return x < tile.getX() + tile.getSize()
                && x + Player.WIDTH > tile.getX()
                && y < tile.getY() + tile.getSize()
                && y + Player.HEIGHT > tile.getY();
    }

    /**
     * 将数值限制在指定闭区间内。
     */
    private double clamp(double value, double minimum, double maximum) {
        return Math.max(minimum, Math.min(value, maximum));
    }
}
