package com.stusys.cattan.userclient.entity;


import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@Builder
@EqualsAndHashCode
/**
 *  ① 定义 user 对象
 */
public class UserAuth {
    private Long id;
    private Long userid;
    private String token;
    private String salt;
    private Date createdAt;
    private Date modifiedAt;
}


