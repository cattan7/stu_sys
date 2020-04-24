package com.stusys.cattan.stuinfo.exception;

import com.stusys.cattan.stuinfo.common.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

public class StudentAlreadyExistsException extends ServiceException {
    public StudentAlreadyExistsException(String msg, String msgZh, Throwable cause) {
        super(msg, cause);
        setHttpStatus(HttpStatus.BAD_REQUEST);
        if (StringUtils.isEmpty(msgZh)) {
            msgZh = "该账户已经注册过了";
        }
        setMsg(msg, msgZh);
    }
}
