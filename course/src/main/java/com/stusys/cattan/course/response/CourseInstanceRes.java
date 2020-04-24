package com.stusys.cattan.course.response;

import lombok.*;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CourseInstanceRes {

    private Long courseInstanceID;

    private Long teacherID;

    private Long courseID;

    private String evaluationMode;

    private String time;

    // Course类的属性

    private String courseName;

    private Boolean required;

    private Double credit;

    private String department;


}