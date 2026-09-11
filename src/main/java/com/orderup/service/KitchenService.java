package com.orderup.service;

import com.orderup.model.*;

public interface KitchenService {
    InteractionResult interact(Player player, InteractionArea area, GameMap map);

    void updateHeldItem(Player player, InteractionArea area);

    InteractionResult placeOrDrop(
            Player player,
            InteractionArea area,
            GameMap map,
            Tile tile
    );

    InteractionResult interactWithTable(Player player, Table table, GameMap map);

    InteractionResult takeIngredientFromSource(
            Player player,
            InteractionArea area,
            GameMap map
    );

    Tile findTile(InteractionArea area, GameMap map);
}
