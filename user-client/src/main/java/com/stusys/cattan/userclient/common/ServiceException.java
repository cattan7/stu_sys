package com.stusys.cattan.userclient.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

public class ServiceException extends RuntimeException {
    private HttpStatus httpStatus;

    private String msg;

    private String msgZh;


    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setMsg(String msg, String msgZh) {
        this.msg = msg;
        this.msgZh = msgZh;

    }

    public ServiceException(String msg, Throwable cause) {
        super(msg, cause);
    }


    public ResponseEntity<BaseResponse> toResponseEntity() {
        String statusMsg = StringUtils.isEmpty(msgZh) ? msg : msgZh;
        return ResponseEntity.status(httpStatus).body(new BaseResponse<>(statusMsg, null));
    }
}
