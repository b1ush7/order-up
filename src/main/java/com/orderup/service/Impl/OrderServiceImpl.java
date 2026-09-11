package com.orderup.service.Impl;

import com.orderup.model.DishType;
import com.orderup.model.Order;
import com.orderup.model.OrderResult;
import com.orderup.model.Plate;
import com.orderup.model.Recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * 创建、更新并提交订单。
 */
public class OrderServiceImpl implements com.orderup.service.OrderService {
    private final List<Recipe> recipes = List.of(
            new Recipe(DishType.SASHIMI, 100, 30),
            new Recipe(DishType.ROLL, 150, 45)
    );
    private final List<Order> activeOrders = new ArrayList<>();
    private final ScoreServiceImpl scoreService;
    private final Random random;

    public OrderServiceImpl() {
        this(new ScoreServiceImpl(), new Random());
    }

    OrderServiceImpl(ScoreServiceImpl scoreService, Random random) {
        this.scoreService = scoreService;
        this.random = random;
    }

    @Override
    public Order createRandomOrder() {
        Recipe recipe = recipes.get(random.nextInt(recipes.size()));
        Order order = new Order(UUID.randomUUID().toString(), recipe);
        activeOrders.add(order);
        return order;
    }

    @Override
    public List<Order> getActiveOrders() {
        return Collections.unmodifiableList(activeOrders);
    }

    @Override
    public void updateOrders(double deltaSeconds) {
        if (deltaSeconds < 0) {
            throw new IllegalArgumentException("Delta seconds cannot be negative.");
        }
        activeOrders.forEach(order -> order.update(deltaSeconds));
        activeOrders.removeIf(order -> order.getRemainingSeconds() == 0);
    }

    @Override
    public OrderResult submitPlate(Plate plate) {
        if (plate == null || plate.isEmpty()) {
            return OrderResult.failed("盘子为空");
        }

        Iterator<Order> iterator = activeOrders.iterator();
        while (iterator.hasNext()) {
            Order order = iterator.next();
            if (!plate.matches(order.getRecipe())) {
                continue;
            }
            int score = scoreService.calculateSuccessScore(order) + scoreService.calculateTip(order);
            order.complete();
            iterator.remove();
            plate.clear();
            return OrderResult.success("订单完成", score);
        }

        plate.clear();
        return OrderResult.failed("菜品与订单不匹配");
    }
}
