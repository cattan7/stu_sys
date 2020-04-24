package com.stusys.cattan.course.service;

import com.stusys.cattan.course.common.BaseResponse;
import com.stusys.cattan.course.entity.Teacher;
import com.stusys.cattan.course.feign.TeacherServiceFeignDao;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Service
public class TeacherFeignService {
    @Autowired
    private TeacherServiceFeignDao teacherServiceFeignDao;

    @GetMapping("/teachers/{teacherID}/v1")
    Teacher retrieveTeacherByID(@PathVariable Long teacherID) {

        return teacherServiceFeignDao.retrieveTeacherByID(teacherID).getBody().getData();

    }
}
