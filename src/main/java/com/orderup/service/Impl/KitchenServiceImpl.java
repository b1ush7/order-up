package com.orderup.service.Impl;

import com.orderup.config.GameConfig;
import com.orderup.model.GameItem;
import com.orderup.model.GameMap;
import com.orderup.model.Ingredient;
import com.orderup.model.IngredientSource;
import com.orderup.model.InteractionArea;
import com.orderup.model.InteractionResult;
import com.orderup.model.Plate;
import com.orderup.model.Player;
import com.orderup.model.Table;
import com.orderup.model.Tile;

/**
 * 处理拾取、放下、食材来源和桌面装盘。
 */
public class KitchenServiceImpl implements com.orderup.service.KitchenService {
    /** {@inheritDoc} */
    @Override
    public InteractionResult interact(Player player, InteractionArea area, GameMap map) {
        Tile tile = findTile(area, map);

        if (player.hasHeldItem()) {
            return placeOrDrop(player, area, map, tile);
        }
        if (tile instanceof Table table && !table.isEmpty()) {
            player.pickUp(table.take());
            return InteractionResult.ok("拿起物品");
        }
        for (GameItem item : map.getItems()) {
            if (item != player.getHeldItem()
                    && area.intersects(item.getX(), item.getY(), item.getWidth(), item.getHeight())) {
                player.pickUp(item);
                return InteractionResult.ok("拿起物品");
            }
        }
        if (tile instanceof IngredientSource source) {
            return takeIngredientFromSource(player, area, map, source);
        }
        return InteractionResult.failed("附近没有可交互物品");
    }

    /** {@inheritDoc} */
    @Override
    public void updateHeldItem(Player player, InteractionArea area) {
        GameItem heldItem = player.getHeldItem();
        if (heldItem != null) {
            heldItem.setX(area.getX());
            heldItem.setY(area.getY());
        }
    }

    /** {@inheritDoc} */
    @Override
    public InteractionResult placeOrDrop(
            Player player,
            InteractionArea area,
            GameMap map,
            Tile tile
    ) {
        if (tile instanceof Table table) {
            return interactWithTable(player, table, map);
        }

        GameItem droppedItem = player.releaseHeldItem();
        droppedItem.setX(area.getX());
        droppedItem.setY(area.getY());
        return InteractionResult.ok("放下物品");
    }

    /** {@inheritDoc} */
    @Override
    public InteractionResult interactWithTable(Player player, Table table, GameMap map) {
        GameItem heldItem = player.getHeldItem();
        GameItem tableItem = table.getItem();

        if (heldItem instanceof Ingredient ingredient && tableItem instanceof Plate plate) {
            if (!plate.addIngredient(ingredient)) {
                return InteractionResult.failed("该食材尚不能装盘");
            }
            player.releaseHeldItem();
            map.removeItem(ingredient);
            return InteractionResult.ok("食材已装盘");
        }

        if (heldItem instanceof Plate plate && tableItem instanceof Ingredient ingredient) {
            if (!plate.addIngredient(ingredient)) {
                return InteractionResult.failed("该食材尚不能装盘");
            }
            table.take();
            map.removeItem(ingredient);
            return InteractionResult.ok("食材已装盘");
        }

        if (table.place(heldItem)) {
            player.releaseHeldItem();
            return InteractionResult.ok("物品已放到桌上");
        }
        return InteractionResult.failed("桌面已被占用");
    }

    /** {@inheritDoc} */
    @Override
    public InteractionResult takeIngredientFromSource(
            Player player,
            InteractionArea area,
            GameMap map,
            IngredientSource source
    ) {
        Ingredient ingredient = map.addItem(
                new Ingredient(source.getIngredientType(), area.getX(), area.getY())
        );
        player.pickUp(ingredient);
        return InteractionResult.ok("取得食材");
    }

    /** {@inheritDoc} */
    @Override
    public Tile findTile(InteractionArea area, GameMap map) {
        double centerX = area.getX() + InteractionArea.WIDTH / 2;
        double centerY = area.getY() + InteractionArea.HEIGHT / 2;

        int column = (int) (centerX / GameConfig.TILE_SIZE);
        int row = (int) (centerY / GameConfig.TILE_SIZE);

        if (row < 0 || row >= GameConfig.MAP_ROWS
                || column < 0 || column >= GameConfig.MAP_COLUMNS) {
            return null;
        }

        return map.getTile(row, column);
    }
}
