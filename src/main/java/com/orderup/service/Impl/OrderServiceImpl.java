package com.orderup.service.Impl;

import com.orderup.config.GameConfig;
import com.orderup.model.*;
import com.orderup.service.OrderService;

import java.util.*;

/**
 * 创建、更新并提交订单。
 */
public class OrderServiceImpl implements OrderService {
    private final List<Order> activeOrders = new ArrayList<>();
    private final ScoreServiceImpl scoreService;
    private final Random random;
    private final List<Recipe> recipes= GameConfig.recipes;

    /**
     * 使用默认计分服务和随机数生成器创建订单服务。
     */
    public OrderServiceImpl() {
        this(new ScoreServiceImpl(), new Random());
    }

    /**
     * 创建可注入计分策略和随机数生成器的订单服务，便于测试。
     *
     * @param scoreService 订单成功后使用的计分服务
     * @param random 随机选择菜谱时使用的随机数生成器
     */
    OrderServiceImpl(ScoreServiceImpl scoreService, Random random) {
        this.scoreService = scoreService;
        this.random = random;
    }

    /** {@inheritDoc} */
    @Override
    public Order createRandomOrder() {
        Recipe recipe = recipes.get(random.nextInt(recipes.size()));
        Order order = new Order(UUID.randomUUID().toString(), recipe);
        activeOrders.add(order);
        return order;
    }

    /** {@inheritDoc} */
    @Override
    public List<Order> getActiveOrders() {
        return Collections.unmodifiableList(activeOrders);
    }

    /** {@inheritDoc} */
    @Override
    public void updateOrders(double deltaSeconds) {
        if (deltaSeconds < 0) {
            throw new IllegalArgumentException("Delta seconds cannot be negative.");
        }
        activeOrders.forEach(order -> order.update(deltaSeconds));
        activeOrders.removeIf(order -> order.getRemainingSeconds() == 0);
    }

    /** {@inheritDoc} */
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
