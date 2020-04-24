package com.stusys.cattan.teacher.mapper;

import com.stusys.cattan.teacher.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TeacherMapper {
    void addTeacher(Teacher teacher);

    List<Teacher> retrieveAllTeachers();

    List<Teacher> retrieveTeachersByDepartment(@Param("department") String department);

    Teacher retrieveTeacherByUserId(@Param("userid") Long userid);

    Teacher retrieveTeacherByTeacherID(@Param("teacherID") Long teacherID);

    void deleteTeacher(@Param("TeacherID") Long TeacherID);
}
