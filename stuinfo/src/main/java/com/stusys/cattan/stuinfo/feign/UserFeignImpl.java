//package com.stusys.cattan.stuinfo.feign;
//
//import com.alibaba.fescar.common.util.StringUtils;
//import com.stusys.cattan.stuinfo.common.BaseResponse;
//import com.stusys.cattan.stuinfo.feign.UserServiceFeignDao;
//import com.stusys.cattan.stuinfo.request.AddUserRequest;
//import io.seata.core.context.RootContext;
//import io.seata.core.exception.TransactionException;
//import io.seata.tm.api.GlobalTransactionContext;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.RequestBody;
//
//@Slf4j
//@Component
//public class UserFeignImpl implements UserServiceFeignDao {
//    @Override
//    public ResponseEntity<BaseResponse<Long>> addUser(@RequestBody AddUserRequest addUserRequest){
//        log.debug("addUser 服务降级了,XID的值是：{}", RootContext.getXID());
//        if (!StringUtils.isBlank(RootContext.getXID())) {
//            try {
//                GlobalTransactionContext.reload(RootContext.getXID()).rollback();
//            } catch (TransactionException e) {
//                e.printStackTrace();
//            }
//        }
//        return ResponseEntity.ok().body(new BaseResponse<>(0L));
//    }
//}
