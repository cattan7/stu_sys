package com.stusys.cattan.stuinfo.service;

import com.stusys.cattan.stuinfo.common.BaseResponse;
import com.stusys.cattan.stuinfo.common.ResultList;
import com.stusys.cattan.stuinfo.dto.User;
import com.stusys.cattan.stuinfo.entity.Student;
import com.stusys.cattan.stuinfo.exception.StudentAlreadyExistsException;
import com.stusys.cattan.stuinfo.feign.UserServiceFeignDao;
import com.stusys.cattan.stuinfo.mapper.StudentMapper;
import com.stusys.cattan.stuinfo.request.AddStudentRequest;
import com.stusys.cattan.stuinfo.request.AddUserRequest;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j

public class StudentService {
    @Resource
    private StudentMapper studentMapper;

    //    @Autowired
//    private UserFeignService userFeignService;
    @Autowired
    private UserFeignService userFeignService;

    // @GlobalTransactional(name = "add_stu_tran", rollbackFor = Exception.class)
    public Long addStudent(AddStudentRequest addStudentRequest) {

        // 1. 检测是否已经注册
        Student student = studentMapper.retrieveStudentByStudentID(addStudentRequest.getStudentID());

        if (student != null) {
            String errorMsg = "The student with student id " + addStudentRequest.getStudentID() + " already been Registered";
            throw new StudentAlreadyExistsException(errorMsg, null, null);
        }

        // 2. 创建用户
        AddUserRequest addUserRequest = AddUserRequest.builder()
                .userName(addStudentRequest.getName())
                .role("STUDENT")
                .password("123456")
                .build();
        ResponseEntity<BaseResponse<Long>> response = userFeignService.addUser(addUserRequest);

        log.info("Add user with userid " + response.getBody().getData());

        //3. 注册学生
        log.info("Add student " + addStudentRequest.getName() + " " + addStudentRequest.getStudentID());
        try {
            student = Student.builder()
                    .userid(response.getBody().getData())
                    .name(addStudentRequest.getName())
                    .studentID(addStudentRequest.getStudentID())
                    .gender(addStudentRequest.getGender())
                    .IDtype(addStudentRequest.getIDtype())
                    .ID(addStudentRequest.getID())
                    .department(addStudentRequest.getDepartment())
                    .major(addStudentRequest.getMajor())
                    .degree(addStudentRequest.getDegree())
                    .entrancedAt(addStudentRequest.getEntrancedAt())
                    .nationality(addStudentRequest.getNationality())
                    .censusRegistration(addStudentRequest.getCensusRegistration())
                    .emergencyContact(addStudentRequest.getEmergencyContact())
                    .homeAdd(addStudentRequest.getHomeAdd())
                    .build();
            studentMapper.addStudent(student);
            log.info("Add student succeed " + student.getStudentID());
            return student.getStudentID();
        } catch (Exception e) {
            log.info("Add student failed " + student.getUserid());
            this.deleteStudent(addStudentRequest.getStudentID());
            userFeignService.deleteUser(student.getUserid());
            log.info("delete user and  student  " + student.getUserid());
            throw e;
        }

    }

    public boolean deleteStudent(Long studentID) {
        Student student = studentMapper.retrieveStudentByStudentID(studentID);
        if (student == null) {
            return false;
        }
        studentMapper.deleteStudentByStudentID(studentID);
        return true;
    }

    private ResultList<Student> makeStudents(ResultList<Student> result, List<Student> students) {
        result.setList(students.stream()
                .map(Student -> Student.builder()
                        .userid(Student.getUserid())
                        .name(Student.getName())
                        .studentID(Student.getStudentID())
                        .IDtype(Student.getIDtype())
                        .ID(Student.getID())
                        .gender(Student.getGender())
                        .entrancedAt(Student.getEntrancedAt())
                        .department(Student.getDepartment())
                        .major(Student.getMajor())
                        .degree(Student.getDegree())
                        .nationality(Student.getNationality())
                        .censusRegistration(Student.getCensusRegistration())
                        .emergencyContact(Student.getEmergencyContact())
                        .homeAdd(Student.getHomeAdd())
                        .build())
                .collect(Collectors.toList()));

        return result;
    }

    public ResultList<Student> retrieveAllStudents() {
        log.info("Retrieve all students");
        List<Student> students = studentMapper.retrieveAllStudents();

        ResultList<Student> result = new ResultList<>();

        this.makeStudents(result, students);

        return result;
    }

    public ResultList<Student> retrieveStudentsByDepartment(String department) {
        log.info("Retrieve students by dep");

        List<Student> students = studentMapper.retrieveStudentsByDepartment(department);

        ResultList<Student> result = new ResultList<>();

        this.makeStudents(result, students);

        return result;
    }

    public ResultList<Student> retrieveStudentsByDepAndMajor(String department, String major) {
        log.info("Retrieve students by dep and major");

        List<Student> students = studentMapper.retrieveStudentsByDepAndMajor(department, major);

        ResultList<Student> result = new ResultList<>();

        this.makeStudents(result, students);

        return result;
    }

    public ResultList<Student> retrieveStudentsByDepAndMajorAndDegree(String department, String major, String degree) {
        log.info("Retrieve students by dep and major and degree");

        List<Student> students = studentMapper.retrieveStudentsByDepAndMajorAndDegree(department, major, degree);

        ResultList<Student> result = new ResultList<>();

        this.makeStudents(result, students);

        return result;
    }

    public ResultList<Student> retrieveStudentsByDepAndDegree(String department, String degree) {
        log.info("Retrieve students by dep and degree");

        List<Student> students = studentMapper.retrieveStudentsByDepAndDegree(department, degree);

        ResultList<Student> result = new ResultList<>();

        this.makeStudents(result, students);

        return result;
    }

    public Student retrieveStudentsByID(Long studentID) {
        log.info("Retrieve students by id");

        Student student = studentMapper.retrieveStudentByStudentID(studentID);

        log.info("Retrieve students by id " + studentID + student);

        return student;
    }
}
