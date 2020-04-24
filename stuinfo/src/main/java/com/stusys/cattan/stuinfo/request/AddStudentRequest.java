package com.stusys.cattan.stuinfo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("创建用户时的请求")
@Builder
public class AddStudentRequest {

//    @ApiModelProperty(value = "账号", example = "111")
//    private Long userid;

    @ApiModelProperty(value = "用户名", example = "谭欢")
    private String name;

    @ApiModelProperty(value = "学号", example = "111")
    private Long studentID;

    @ApiModelProperty(value = "性别", example = "FEMALE")
    private String gender;

    @ApiModelProperty(value = "证件类型", example = "PASSWORD")
    private String IDtype;

    @ApiModelProperty(value = "证件号码", example = "111")
    private Long ID;

    @ApiModelProperty(value = "学院", example = "111")
    private String department;

    @ApiModelProperty(value = "专业", example = "111")
    private String major;

    @ApiModelProperty(value = "学位", example = "MASTER")
    private String degree;

    @ApiModelProperty(value = "入学日期", example = "Sat, 26 May 2018 10:36:48 GMT")
    private Date entrancedAt;

    @ApiModelProperty(value = "国籍", example = "111")
    private String nationality;

    @ApiModelProperty(value = "籍贯", example = "111")
    private String censusRegistration;

    @ApiModelProperty(value = "家庭住址", example = "111")
    private String homeAdd;

    @ApiModelProperty(value = "紧急联系人", example = "111")
    private String emergencyContact;

}