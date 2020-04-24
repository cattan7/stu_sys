package io.seata.sample.feign;

import java.math.BigDecimal;

import io.seata.sample.feign.impl.AccountApiImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author IT云清
 */
@FeignClient(value = "account-server", fallback = AccountApiImpl.class)
public interface AccountApi {


    /**
     * 扣减账户余额超时调用的方法
     *
     * @param userId 用户id
     * @param money  金额
     * @return
     */
    @RequestMapping("/account/decreaseTimeOut")
    String decreaseTimeOut(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money);

    /**
     * 扣减账户余额抛出异常
     *
     * @param userId 用户id
     * @param money  金额
     * @return
     */
    @RequestMapping("/account/decreaseException")
    String decreaseException(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money);

    /**
     * 扣减账户余额
     *
     * @param userId 用户id
     * @param money  金额
     * @return
     */
    @RequestMapping("/account/decrease")
    String decrease(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money);
}
