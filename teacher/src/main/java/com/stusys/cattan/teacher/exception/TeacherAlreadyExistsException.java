package com.stusys.cattan.teacher.exception;

import com.stusys.cattan.teacher.common.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

public class TeacherAlreadyExistsException extends ServiceException {
    public TeacherAlreadyExistsException(String msg, String msgZh, Throwable cause) {
        super(msg, cause);
        setHttpStatus(HttpStatus.BAD_REQUEST);
        if (StringUtils.isEmpty(msgZh)) {
            msgZh = "该账户已经注册过了";
        }
        setMsg(msg, msgZh);
    }
}
