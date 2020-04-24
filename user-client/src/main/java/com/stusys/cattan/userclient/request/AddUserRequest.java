package com.stusys.cattan.userclient.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@ApiModel("创建用户时的请求")
@Builder
public class AddUserRequest {

    @ApiModelProperty(value = "用户名", example = "谭欢")
    private String userName;

    @ApiModelProperty(value = "密码", example = "111")
    private String password;

    @ApiModelProperty(value = "角色", example = "TEACHER")
    private String role;

}

