package io.seata.sample.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;

/**
 * @author linzf
 * @since 2019/5/29
 * 类描述：全局异常捕获处理
 */
@ControllerAdvice
public class GlobalExceptionsHandler {

    private Logger log = LoggerFactory.getLogger(GlobalExceptionsHandler.class);

    /**
     * 功能描述：全局异常处理
     *
     * @param e
     * @return 返回处理结果
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object errorHandler(Exception e) throws Exception {
        // 此处为属性级的错误日志的处理
        if (e instanceof ConstraintViolationException) {
            log.info("绑定错误日志为：{}", e.getMessage());
            return "请求数据格式错误";
            // 此处为方法级别的错误日志处理
        } else if (e instanceof MethodArgumentNotValidException) {
            log.info("方法级的绑定错误日志为：{}", e.getMessage());
            return "请求数据格式错误";
            // 此处为全局错误日志的处理
        } else {
            log.info("错误日志为：{}", e.getMessage());
            return "全局异常错误给捕获了！";
        }
    }


}
