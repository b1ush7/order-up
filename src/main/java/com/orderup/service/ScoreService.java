package com.orderup.service;

import com.orderup.model.Order;
import com.orderup.model.OrderResult;

public interface ScoreService {
    int calculateSuccessScore(Order order);

    int calculateTip(Order order);

    int calculatePenalty(OrderResult result);
}
