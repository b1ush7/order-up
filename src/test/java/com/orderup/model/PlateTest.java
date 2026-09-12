package com.orderup.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlateTest {
    @Test
    void recognizesSashimiFromCutFish() {
        Ingredient fish = new Ingredient(IngredientType.FISH, 0, 0);
        fish.setStatus(IngredientStatus.CUT);
        Plate plate = new Plate();

        assertTrue(plate.addIngredient(fish));
        assertEquals(DishType.SASHIMI, plate.getDishType());
    }

    @Test
    void rejectsRawIngredients() {
        Plate plate = new Plate();

        assertFalse(plate.addIngredient(new Ingredient(IngredientType.FISH, 0, 0)));
        assertTrue(plate.isEmpty());
    }

    @Test
    void recognizesRollFromCookedRiceAndRawKelp() {
        Ingredient rice = new Ingredient(IngredientType.RICE, 0, 0);
        rice.setStatus(IngredientStatus.COOKED);
        Ingredient kelp = new Ingredient(IngredientType.KELP, 0, 0);
        Plate plate = new Plate();

        assertTrue(plate.addIngredient(rice));
        assertTrue(plate.addIngredient(kelp));
        assertEquals(DishType.ROLL, plate.getDishType());
    }
}
