package io.seata.sample.controller;

import io.seata.sample.service.AccountService;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author IT云清
 */
@RestController
@RequestMapping("account")
public class AccountController {

    @Autowired
    private AccountService accountServiceImpl;

    /**
     * 扣减账户余额超时的方法
     *
     * @param userId 用户id
     * @param money  金额
     * @return
     */
    @RequestMapping("decreaseTimeOut")
    public String decreaseTimeOut(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money) {
        accountServiceImpl.decreaseTimeOut(userId, money);
        return "Account decrease success";
    }

    /**
     * 扣减账户余额抛出异常
     *
     * @param userId 用户id
     * @param money  金额
     * @return
     */
    @RequestMapping("decreaseException")
    public String decreaseException(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money) {
        accountServiceImpl.decreaseException(userId, money);
        return "Account decrease success";
    }

    /**
     * 扣减账户余额
     *
     * @param userId 用户id
     * @param money  金额
     * @return
     */
    @RequestMapping("decrease")
    public String decrease(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money) {
        accountServiceImpl.decrease(userId, money);
        return "Account decrease success";
    }
}
