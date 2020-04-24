package com.stusys.cattan.stuinfo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {

    private String msg = "";

    private T data;

    public BaseResponse(T data) {
        this.data = data;
    }
}

