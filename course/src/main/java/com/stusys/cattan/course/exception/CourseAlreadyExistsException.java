package com.stusys.cattan.course.exception;

import com.stusys.cattan.course.common.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;


public class CourseAlreadyExistsException extends ServiceException {
    public CourseAlreadyExistsException(String msg, String msgZh, Throwable cause) {
        super(msg, cause);
        setHttpStatus(HttpStatus.BAD_REQUEST);
        if (StringUtils.isEmpty(msgZh)) {
            msgZh = "课程已存在";
        }
        setMsg(msg, msgZh);
    }
}