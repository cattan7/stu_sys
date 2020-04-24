package com.stusys.cattan.stuinfo.mapper;

import com.stusys.cattan.stuinfo.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentMapper {
    void addStudent(Student student);

    List<Student> retrieveAllStudents();

    List<Student> retrieveStudentsByDepartment(@Param("department") String department);

    List<Student> retrieveStudentsByDepAndMajor(@Param("department") String department, @Param("major") String Major);

    List<Student> retrieveStudentsByDepAndMajorAndDegree(@Param("department") String department, @Param("major") String Major, @Param("degree") String degree);

    List<Student> retrieveStudentsByDepAndDegree(@Param("department") String department, @Param("degree") String degree);

    Student retrieveStudentByUserId(@Param("userid") Long userid);

    Student retrieveStudentByStudentID(@Param("studentID") Long studentID);

    void deleteStudentByStudentID(@Param("studentID") Long studentID);

}
