package com.stusys.cattan.course.entity;

import lombok.*;

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
