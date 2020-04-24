package com.stusys.cattan.stuinfo.exception;

import com.stusys.cattan.stuinfo.common.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

public class UserNotExistException extends ServiceException {
    public UserNotExistException(String msg, String msgZh, Throwable cause) {
        super(msg, cause);
        setHttpStatus(HttpStatus.BAD_REQUEST);
        if (StringUtils.isEmpty(msgZh)) {
            msgZh = "用户信息错误";
        }
        setMsg(msg, msgZh);
    }
}
