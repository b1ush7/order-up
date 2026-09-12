package com.orderup.service;

import com.orderup.model.DishType;
import com.orderup.model.Ingredient;
import com.orderup.model.IngredientStatus;
import com.orderup.model.IngredientType;
import com.orderup.model.Order;
import com.orderup.model.OrderResult;
import com.orderup.model.Plate;
import com.orderup.service.Impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderServiceTest {
    @Test
    void submitsAPlateMatchingTheActiveOrder() {
        OrderServiceImpl service = new OrderServiceImpl();
        Order order = service.createRandomOrder();
        Plate plate = plateFor(order.getRecipe().getDishType());

        OrderResult result = service.submitPlate(plate);

        assertTrue(result.success());
        assertTrue(result.scoreDelta() > 0);
        assertTrue(plate.isEmpty());
    }

    private Plate plateFor(DishType dishType) {
        Plate plate = new Plate();
        if (dishType == DishType.SASHIMI) {
            plate.addIngredient(ingredient(IngredientType.FISH, IngredientStatus.CUT));
        } else {
            plate.addIngredient(ingredient(IngredientType.RICE, IngredientStatus.COOKED));
            plate.addIngredient(ingredient(IngredientType.KELP, IngredientStatus.RAW));
        }
        return plate;
    }

    private Ingredient ingredient(IngredientType type, IngredientStatus status) {
        Ingredient ingredient = new Ingredient(type, 0, 0);
        ingredient.setStatus(status);
        return ingredient;
    }
}
