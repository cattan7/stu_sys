package com.stusys.cattan.userclient.exception;

import com.stusys.cattan.userclient.common.ServiceException;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

public class UserNotExistsException extends ServiceException {
    public UserNotExistsException(String msg, String msgZh, Throwable cause) {
        super(msg, cause);
        setHttpStatus(HttpStatus.BAD_REQUEST);
        if (StringUtils.isEmpty(msgZh)) {
            msgZh = "用户不存在";
        }
        setMsg(msg, msgZh);
    }
}
