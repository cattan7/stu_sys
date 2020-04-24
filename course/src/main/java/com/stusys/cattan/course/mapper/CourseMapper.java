package com.stusys.cattan.course.mapper;

import com.stusys.cattan.course.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseMapper {
    void addCourse(Course course);

    List<Course> retrieveAllCourses();

    List<Course> retrieveCoursesByDep(@Param("department") String department);

    Course retrieveCourseByCourseID(@Param("courseID") Long courseID);

    Course retrieveCourseByCourseName(@Param("courseName") String courseName);

    void deleteCourseByCourseID(@Param("courseID") Long courseID);
}
