package com.orderup.service.Impl;

import com.orderup.model.Order;
import com.orderup.model.OrderResult;
import com.orderup.model.OrderStatus;

/**
 * 统一计算订单基础分、小费和失败扣分。
 */
public class ScoreServiceImpl implements com.orderup.service.ScoreService {
    /** {@inheritDoc} */
    @Override
    public int calculateSuccessScore(Order order) {
        return order == null ? 0 : order.getRecipe().getBaseScore();
    }

    /** {@inheritDoc} */
    @Override
    public int calculateTip(Order order) {
        if (order == null || order.getStatus() != OrderStatus.ACTIVE) {
            return 0;
        }
        double timeRatio = order.getRemainingSeconds() / order.getRecipe().getTimeLimitSeconds();
        return (int) Math.round(order.getRecipe().getBaseScore() * 0.5 * timeRatio);
    }

    /** {@inheritDoc} */
    @Override
    public int calculatePenalty(OrderResult result) {
        if (result == null || result.success()) {
            return 0;
        }
        return result.expired() ? 20 : 10;
    }
}
