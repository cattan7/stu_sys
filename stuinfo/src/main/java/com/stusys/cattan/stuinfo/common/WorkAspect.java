//package com.stusys.cattan.stuinfo.common;
//
//import com.alibaba.fescar.common.util.StringUtils;
//import com.alibaba.nacos.client.logger.LoggerFactory;
//import io.seata.core.context.RootContext;
//import io.seata.core.exception.TransactionException;
//import io.seata.tm.api.GlobalTransaction;
//import io.seata.tm.api.GlobalTransactionContext;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Method;
//import java.util.logging.Logger;
//
//@Aspect
//@Component
//@Slf4j
//public class WorkAspect {
//    @Before("execution(* com.stusys.cattan.stuinfo.service.*.*(..))")
//    public void before(JoinPoint joinPoint) throws TransactionException {
//        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
//        Method method = signature.getMethod();
//        log.info("拦截到需要分布式事务的方法," + method.getName());
//        // 此处可用redis或者定时任务来获取一个key判断是否需要关闭分布式事务
//        GlobalTransaction tx = GlobalTransactionContext.getCurrentOrCreate();
//        tx.begin(300000, "test-client");
//        log.info("创建分布式事务完毕" + tx.getXid());
//    }
//
//    @AfterThrowing(throwing = "e", pointcut = "execution(* com.stusys.cattan.stuinfo.service.*.*(..))")
//    public void doRecoveryActions(Throwable e) throws TransactionException {
//        log.info("方法执行异常:{}", e.getMessage());
//        if (!StringUtils.isBlank(RootContext.getXID())) {
//            GlobalTransactionContext.reload(RootContext.getXID()).rollback();
//        }
//    }
//}
