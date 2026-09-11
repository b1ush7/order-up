package com.orderup.service.Impl;

import com.orderup.model.GameItem;
import com.orderup.model.GameMap;
import com.orderup.model.Ingredient;
import com.orderup.model.IngredientType;
import com.orderup.model.InteractionArea;
import com.orderup.model.InteractionResult;
import com.orderup.model.Plate;
import com.orderup.model.Player;
import com.orderup.model.Table;
import com.orderup.model.Tile;
import com.orderup.model.TileType;

/**
 * 处理拾取、放下、食材来源和桌面装盘。
 */
public class KitchenServiceImpl implements com.orderup.service.KitchenService {
    @Override
    public InteractionResult interact(Player player, InteractionArea area, GameMap map) {
        Tile tile = findTile(area, map);

        if (player.hasHeldItem()) {
            return placeOrDrop(player, area, map, tile);
        }
        if (tile != null && tile.getType() == TileType.INGREDIENT_SOURCE) {
            return takeIngredientFromSource(player, area, map);
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
        return InteractionResult.failed("附近没有可交互物品");
    }

    @Override
    public void updateHeldItem(Player player, InteractionArea area) {
        GameItem heldItem = player.getHeldItem();
        if (heldItem != null) {
            heldItem.setX(area.getX());
            heldItem.setY(area.getY());
        }
    }

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

    @Override
    public InteractionResult takeIngredientFromSource(
            Player player,
            InteractionArea area,
            GameMap map
    ) {
        Ingredient ingredient = map.addItem(
                new Ingredient(IngredientType.FISH, area.getX(), area.getY())
        );
        player.pickUp(ingredient);
        return InteractionResult.ok("取得食材");
    }

    @Override
    public Tile findTile(InteractionArea area, GameMap map) {
        for (Tile[] row : map.getTiles()) {
            for (Tile tile : row) {
                if (area.intersects(tile.getX(), tile.getY(), tile.getSize(), tile.getSize())) {
                    return tile;
                }
            }
        }
        return null;
    }
}
