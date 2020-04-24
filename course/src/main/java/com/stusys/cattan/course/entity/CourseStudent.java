package com.stusys.cattan.course.entity;

import lombok.*;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CourseStudent {
    private Long courseStudentID;
    private Long courseInstanceID;
    private Long studentID;
    private Double grade;
}
