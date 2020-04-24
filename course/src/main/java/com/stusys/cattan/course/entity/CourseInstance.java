package com.stusys.cattan.course.entity;

import lombok.*;

import java.util.Date;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CourseInstance {
    private Long courseInstanceID;

    private Long teacherID;

    private Long courseID;

    private String evaluationMode;

    private String time;

    // Course类的属性

//    private String courseName;
//
//    private boolean required;
//
//    private double credit;
//
//    private String department;
}