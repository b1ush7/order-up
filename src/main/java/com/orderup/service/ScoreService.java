package com.orderup.service;

import com.orderup.model.Order;
import com.orderup.model.OrderResult;

public interface ScoreService {
    /**
     * 计算完成订单可获得的基础分。
     *
     * @param order 已匹配的订单
     * @return 菜谱设定的基础分；订单为空时返回 0
     */
    int calculateSuccessScore(Order order);

    /**
     * 根据订单剩余时间占总时限的比例计算小费。
     *
     * @param order 待计算小费的活动订单
     * @return 小费分数；订单无效或非活动状态时返回 0
     */
    int calculateTip(Order order);

    /**
     * 根据失败结果计算扣分。
     *
     * @param result 订单提交或超时结果
     * @return 需要扣除的正整数分值；成功或结果为空时返回 0
     */
    int calculatePenalty(OrderResult result);
}
