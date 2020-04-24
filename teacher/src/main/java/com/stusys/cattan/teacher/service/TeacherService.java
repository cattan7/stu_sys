package com.stusys.cattan.teacher.service;

import com.stusys.cattan.teacher.common.BaseResponse;
import com.stusys.cattan.teacher.common.ResultList;
import com.stusys.cattan.teacher.dto.User;
import com.stusys.cattan.teacher.entity.Teacher;
import com.stusys.cattan.teacher.exception.TeacherAlreadyExistsException;
import com.stusys.cattan.teacher.exception.UserNotExistException;
import com.stusys.cattan.teacher.mapper.TeacherMapper;
import com.stusys.cattan.teacher.request.AddTeacherRequest;
import com.stusys.cattan.teacher.request.AddUserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j

public class TeacherService {
    @Resource
    private TeacherMapper teacherMapper;
    @Autowired
    private UserFeignService userFeignService;

    public Long addTeacher(AddTeacherRequest addTeacherRequest) {

        Teacher teacher = teacherMapper.retrieveTeacherByTeacherID(addTeacherRequest.getTeacherID());
        if (teacher != null) {
            String errorMsg = "The teacher with teacher id " + addTeacherRequest.getTeacherID() + " already been Registered";
            throw new TeacherAlreadyExistsException(errorMsg, null, null);
        }

        AddUserRequest addUserRequest = AddUserRequest.builder()
                .userName(addTeacherRequest.getTeacherName())
                .role("TEACHER")
                .password("123456")
                .build();
        ResponseEntity<BaseResponse<Long>> response = userFeignService.addUser(addUserRequest);

        log.info("Add user with userid " + response.getBody().getData());

        //3. 注册学生
        log.info("Add teacher " + addTeacherRequest.getTeacherName() + " " + addTeacherRequest.getTeacherID());
        try {
            teacher = Teacher.builder()
                    .userid(response.getBody().getData())
                    .teacherName(addTeacherRequest.getTeacherName())
                    .teacherID(addTeacherRequest.getTeacherID())
                    .department(addTeacherRequest.getDepartment())
                    .email(addTeacherRequest.getEmail())
                    .phoneNumber(addTeacherRequest.getEmail())
                    .build();
            log.info("Add teacher succeed");
            teacherMapper.addTeacher(teacher);
            return teacher.getTeacherID();
        } catch (Exception e) {
            log.info("Add teacher failed " + teacher.getUserid() + e);
            this.deleteTeacher(addTeacherRequest.getTeacherID());
            userFeignService.deleteUser(teacher.getUserid());
            log.info("delete user and  student  " + teacher.getUserid());

            throw e;
        }

    }

    public boolean deleteTeacher(Long teacherID) {
        Teacher teacher = teacherMapper.retrieveTeacherByTeacherID(teacherID);
        if (teacher == null) {
            return false;
        }
        teacherMapper.deleteTeacher(teacherID);
        return true;
    }


    private ResultList<Teacher> makeTeachers(ResultList<Teacher> result, List<Teacher> students) {
        result.setList(students.stream()
                .map(Teacher -> Teacher.builder()
                        .userid(Teacher.getUserid())
                        .teacherName(Teacher.getTeacherName())
                        .teacherID(Teacher.getTeacherID())
                        .department(Teacher.getDepartment())
                        .email(Teacher.getEmail())
                        .phoneNumber(Teacher.getEmail())
                        .build())
                .collect(Collectors.toList()));

        return result;
    }

    public ResultList<Teacher> retrieveAllTeachers() {
        log.info("Retrieve all students");
        List<Teacher> students = teacherMapper.retrieveAllTeachers();

        ResultList<Teacher> result = new ResultList<>();

        this.makeTeachers(result, students);

        return result;
    }

    public ResultList<Teacher> retrieveTeachersByDepartment(String department) {
        log.info("Retrieve students by dep");

        List<Teacher> students = teacherMapper.retrieveTeachersByDepartment(department);

        ResultList<Teacher> result = new ResultList<>();

        this.makeTeachers(result, students);

        return result;
    }

    public Teacher retrieveTeacherByTeacherID(Long teacherID) {
        log.info("Retrieve students by dep");

        Teacher teacher = teacherMapper.retrieveTeacherByTeacherID(teacherID);

        return teacher;
    }
}
