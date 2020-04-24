package com.stusys.cattan.teacher.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("创建用户时的请求")
@Builder
public class AddTeacherRequest {

    @ApiModelProperty(value = "账号", example = "111")
    private Long userid;

    @ApiModelProperty(value = "用户名", example = "谭欢")
    private String teacherName;

    @ApiModelProperty(value = "学号", example = "111")
    private Long teacherID;

    @ApiModelProperty(value = "学院", example = "111")
    private String department;

    @ApiModelProperty(value = "邮箱", example = "111")
    private String email;

    @ApiModelProperty(value = "电话号码", example = "111")
    private String phoneNumber;

}