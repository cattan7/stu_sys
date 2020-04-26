package com.stusys.cattan.teacher.dto;

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

    private Boolean active;

    private Long creator;
}