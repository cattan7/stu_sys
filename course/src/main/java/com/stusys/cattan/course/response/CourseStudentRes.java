package com.stusys.cattan.course.response;


import lombok.*;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CourseStudentRes {

    private Long courseStudentID;

    private Long courseInstanceID;

    private Long studentID;

    private Double grade;

    // Course类的属性

    private String name;

    private String department;

    private Boolean required;

    private Double credit;

    // CourseInstance 类属性

    private Long teacherID;


}