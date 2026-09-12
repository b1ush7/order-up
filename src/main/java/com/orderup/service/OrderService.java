package com.orderup.service;

import com.orderup.model.Order;
import com.orderup.model.OrderResult;
import com.orderup.model.Plate;

import java.util.List;

public interface OrderService {
    /**
     * 从已配置的菜谱中随机创建一张订单，并加入活动订单列表。
     *
     * @return 新创建的活动订单
     */
    Order createRandomOrder();

    /**
     * 获取当前尚未完成的订单。
     *
     * @return 只读的活动订单列表
     */
    List<Order> getActiveOrders();

    /**
     * 推进所有活动订单的剩余时间，并移除已超时订单。
     *
     * @param deltaSeconds 本次要推进的秒数，不能为负数
     */
    void updateOrders(double deltaSeconds);

    /**
     * 尝试用盘中菜品匹配一张活动订单，并计算成功得分。
     *
     * @param plate 要提交的盘子
     * @return 提交是否成功、提示信息和分数变化
     */
    OrderResult submitPlate(Plate plate);
}
