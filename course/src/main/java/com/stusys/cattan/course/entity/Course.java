package com.stusys.cattan.course.entity;

import lombok.*;

import java.util.Date;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    private Long courseID;

    private String courseName;

    private Boolean required;

    private Float credit;

    private String department;
}
