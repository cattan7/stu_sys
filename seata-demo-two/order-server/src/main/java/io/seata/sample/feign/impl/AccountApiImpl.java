package io.seata.sample.feign.impl;

import io.seata.core.context.RootContext;
import io.seata.core.exception.TransactionException;
import io.seata.sample.feign.AccountApi;
import io.seata.tm.api.GlobalTransactionContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author linzf
 * @since 2020/1/2
 * 类描述： 服务降级的实现
 */
@Component
public class AccountApiImpl implements AccountApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountApiImpl.class);

    @Override
    public String decreaseTimeOut(Long userId, BigDecimal money) {
        LOGGER.debug("decreaseTimeOut服务降级了,XID的值是：{}", RootContext.getXID());
        if (!StringUtils.isBlank(RootContext.getXID())) {
            try {
                GlobalTransactionContext.reload(RootContext.getXID()).rollback();
            } catch (TransactionException e) {
                e.printStackTrace();
            }
        }
        return "decreaseTimeOut";
    }

    @Override
    public String decreaseException(Long userId, BigDecimal money) {
        LOGGER.debug("decreaseException服务降级了");
        return "decreaseException";
    }

    @Override
    public String decrease(Long userId, BigDecimal money) {
        LOGGER.debug("decrease服务降级了");
        return "decrease";
    }
}
