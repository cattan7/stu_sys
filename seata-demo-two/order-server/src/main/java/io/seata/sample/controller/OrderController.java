package io.seata.sample.controller;

import io.seata.sample.entity.Order;
import io.seata.sample.service.OrderService;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author IT云清
 */
@RestController
@RequestMapping(value = "order")
public class OrderController {

    @Autowired
    private OrderService orderServiceImpl;

    /**
     * 创建订单
     * http://localhost:8180/order/createTimeOut?userId=1&productId=1&count=10&money=100
     *
     * @param order
     * @return
     */
    @GetMapping("createTimeOut")
    public String createTimeOut(Order order) {
        orderServiceImpl.createTimeOut(order);
        return "Create order success";
    }


    /**
     * 创建订单
     * http://localhost:8180/order/createException?userId=1&productId=1&count=10&money=100
     *
     * @param order
     * @return
     */
    @GetMapping("createException")
    public String createException(Order order) {
        orderServiceImpl.createException(order);
        return "Create order success";
    }

    /**
     * 创建订单
     *
     * @param order http://localhost:8180/order/create?userId=1&productId=1&count=10&money=100
     * @return
     */
    @GetMapping("create")
    public String create(Order order) {
        orderServiceImpl.create(order);
        return "Create order success";
    }

    /**
     * 修改订单状态
     *
     * @param userId
     * @param money
     * @param status
     * @return
     */
    @RequestMapping("update")
    String update(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money, @RequestParam("status") Integer status) {
        orderServiceImpl.update(userId, money, status);
        return "订单状态修改成功";
    }
}
