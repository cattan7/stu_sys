package com.stusys.cattan.course.mapper;

import com.stusys.cattan.course.entity.CourseInstance;
import com.stusys.cattan.course.response.CourseInstanceRes;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface CourseInstanceMapper {
    void addCourseInstance(CourseInstance courseInstance);

    List<CourseInstanceRes> retrieveAllCourseInstances();

    List<CourseInstanceRes> retrieveCourseInstancesByTeacherID(@Param("teacherID") Long teacherID);

    List<CourseInstanceRes> retrieveCourseInstancesByCourseName(@Param("courseName") String courseName);

    CourseInstanceRes retrieveCourseInstanceByID(@Param("courseInstanceID") Long courseInstanceID);

    CourseInstanceRes retrieveCourseInstanceByIDAndTeacher(@Param("courseID") Long courseID, @Param("teacherID") Long teacherID);

    void deleteCourseInstanceByCourseInstanceID(@Param("courseInstanceID") Long courseInstanceID);
}
