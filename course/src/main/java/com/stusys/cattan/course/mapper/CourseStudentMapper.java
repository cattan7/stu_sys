package com.stusys.cattan.course.mapper;

import com.stusys.cattan.course.entity.CourseInstance;
import com.stusys.cattan.course.entity.CourseStudent;
import com.stusys.cattan.course.response.CourseInstanceRes;
import com.stusys.cattan.course.response.CourseStudentRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseStudentMapper {
    void addCourseStudent(CourseStudent courseStudent);

    List<CourseInstanceRes> retrieveAllCourseInstances();

    List<CourseStudentRes> retrieveCourseInstanceByStudentID(@Param("studentID") Long studentID);

    List<CourseStudent> retrieveCourseStudentsByCourseInstanceID(@Param("courseInstanceID") Long courseInstanceID);

    CourseStudent retrieveCourseInstanceByIDAndStudent(@Param("courseInstanceID") Long courseID, @Param("studentID") Long studentID);

    Boolean updateGrade(@Param("courseStudentID") Long courseStudentID, @Param("grade") Double grade);

    void deleteCourseInstanceInstanceByCourseStudentID(@Param("courseStudentID") Long courseStudentID);
}
