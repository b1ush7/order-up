package com.orderup.service;

import com.orderup.model.Order;
import com.orderup.model.OrderResult;
import com.orderup.model.Plate;

import java.util.List;

public interface OrderService {
    Order createRandomOrder();

    List<Order> getActiveOrders();

    void updateOrders(double deltaSeconds);

    OrderResult submitPlate(Plate plate);
}
