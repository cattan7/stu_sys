package io.seata.sample.service;

import io.seata.sample.entity.Order;

import java.math.BigDecimal;

/**
 * @author IT云清
 */
public interface OrderService {

    /**
     * 创建订单超时
     *
     * @param order
     * @return
     */
    void createTimeOut(Order order);

    /**
     * 创建订单抛出异常
     *
     * @param order
     * @return
     */
    void createException(Order order);

    /**
     * 创建订单
     *
     * @param order
     * @return
     */
    void create(Order order);

    /**
     * 修改订单状态
     *
     * @param userId
     * @param money
     * @param status
     */
    void update(Long userId, BigDecimal money, Integer status);
}
