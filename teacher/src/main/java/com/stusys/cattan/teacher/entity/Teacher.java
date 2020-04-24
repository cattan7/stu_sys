package com.stusys.cattan.teacher.entity;

import lombok.*;

import java.util.Date;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    private Long userid;

    private String teacherName;

    private Long teacherID;

    private String department;

    private String email;

    private String phoneNumber;
}
