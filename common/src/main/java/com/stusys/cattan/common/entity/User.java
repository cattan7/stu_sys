package com.stusys.cattan.common.entity;

import lombok.*;

import java.util.Date;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long userid;

    private String username;

    private String password;

    private String role;

    private Date createdAt;

    private Byte active;

    private Long creator;
}