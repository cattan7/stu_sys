package io.seata.sample.config;

import io.seata.core.context.RootContext;
import io.seata.core.exception.TransactionException;
import io.seata.tm.api.GlobalTransactionContext;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author linzf
 * @since 2019/12/27
 * 类描述： 用于处理程序调用发生异常的时候由于异常被处理以后无法触发事务，而进行的处理，使之可以正常的触发事务。
 */
@Aspect
@Component
public class WorkAspect {

    private final static Logger logger = LoggerFactory.getLogger(WorkAspect.class);

    @AfterThrowing(throwing = "e", pointcut = "execution(* io.seata.sample.service.*.*(..))")
    public void doRecoveryActions(Throwable e) throws TransactionException {
        // 通过RootContext.getXID()是否有值来决定是否触发全局事务的回滚
        logger.info("方法执行异常:{},xid:{}", e.getMessage(), RootContext.getXID());
        if (!StringUtils.isBlank(RootContext.getXID())) {
            GlobalTransactionContext.reload(RootContext.getXID()).rollback();
        }
    }

}
