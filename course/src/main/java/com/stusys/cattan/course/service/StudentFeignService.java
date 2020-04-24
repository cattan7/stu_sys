package com.stusys.cattan.course.service;

import com.stusys.cattan.course.entity.Student;
import com.stusys.cattan.course.entity.Teacher;
import com.stusys.cattan.course.feign.StudentServiceFeignDao;
import com.stusys.cattan.course.feign.TeacherServiceFeignDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class StudentFeignService {
    @Autowired
    private StudentServiceFeignDao studentServiceFeignDao;

    @GetMapping("/student/{studentID}/v1")
    Student retrieveStudentByID(@PathVariable Long studentID) {

        return studentServiceFeignDao.retrieveStudentByID(studentID).getBody().getData();

    }
}