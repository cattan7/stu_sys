package com.stusys.cattan.stuinfo.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stusys.cattan.stuinfo.common.BaseResponse;
import com.stusys.cattan.stuinfo.common.ResultList;
import com.stusys.cattan.stuinfo.entity.Student;
import com.stusys.cattan.stuinfo.request.AddStudentRequest;
import com.stusys.cattan.stuinfo.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@Api(tags = "学生业务信息相关API")
@RestController

public class StudentController {
    @Resource
    private StudentService studentService;

    @Transactional
    @PostMapping("/students/v1")
    @ApiOperation("管理员注册学生")
    public ResponseEntity<BaseResponse<Object>> createUser
            (
                    @RequestBody AddStudentRequest addStudentRequest
            ) {
        {
            log.info("Starting register new student {}", addStudentRequest);
            Long userId = studentService.addStudent(addStudentRequest);
            log.info("Register student succeed");
            return ResponseEntity.ok().body(new BaseResponse<>(userId));
        }
    }

    @GetMapping("/student/{studentID}/v1")
    @ApiOperation("管理员获取学生")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentID", value = "学号", required = true, dataType = "Long", paramType = "path"),
    })
    public ResponseEntity<BaseResponse<Object>> retrieveStudentByID
            (
                    @PathVariable Long studentID
            ) {
        log.info("Starting get student");
        Student student = studentService.retrieveStudentsByID(studentID);
        log.info("Register student succeed");
        return ResponseEntity.ok().body(new BaseResponse<>(student));
    }

    @GetMapping("/students/v1")
    @ApiOperation("获取所有学生")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", required = false, defaultValue = "1", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页个数", required = false, defaultValue = "8", dataType = "int")
    })
    public ResponseEntity<BaseResponse<ResultList<Student>>> retrieveUsers
            (
                    @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                    @RequestParam(value = "size", required = false, defaultValue = "8") Integer size
            ) {
        try {
            log.info("Starting retrieve all users");
            Page p = PageHelper.startPage(page, size);

            ResultList<Student> resultList = studentService.retrieveAllStudents();

            PageInfo info = new PageInfo(p.getResult());
            resultList.setPage(page);
            resultList.setSize(size);
            resultList.setTotal(info.getTotal());
            log.info("Retrieve user succeed");
            return ResponseEntity.ok().body(new BaseResponse<>(resultList));
        } finally {
            PageHelper.clearPage();
        }

    }

    @GetMapping("/students/dep/{department}/v1")
    @ApiOperation("获取某学部下的学生")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "department", value = "学部名", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "page", value = "页数", required = false, defaultValue = "1", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页个数", required = false, defaultValue = "8", dataType = "int")
    })
    public ResponseEntity<BaseResponse<ResultList<Student>>> retrieveUsersByRole
            (
                    @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                    @RequestParam(value = "size", required = false, defaultValue = "8") Integer size,
                    @PathVariable String department) {
        try {
            log.info("Starting retrieve all users");
            Page p = PageHelper.startPage(page, size);

            ResultList<Student> resultList = studentService.retrieveStudentsByDepartment(department);

            PageInfo info = new PageInfo(p.getResult());
            resultList.setPage(page);
            resultList.setSize(size);
            resultList.setTotal(info.getTotal());
            log.info("Retrieve student by department succeed");
            return ResponseEntity.ok().body(new BaseResponse<>(resultList));
        } finally {
            PageHelper.clearPage();
        }
    }

    @GetMapping("/students/dep/{department}/major/{major}/v1")
    @ApiOperation("获取某学部某专业下的学生")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "department", value = "学部名", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "major", value = "专业名", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "page", value = "页数", required = false, defaultValue = "1", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页个数", required = false, defaultValue = "8", dataType = "int")
    })
    public ResponseEntity<BaseResponse<ResultList<Student>>> retrieveStudentsByDepAndMajor
            (
                    @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                    @RequestParam(value = "size", required = false, defaultValue = "8") Integer size,
                    @PathVariable String department,
                    @PathVariable String major
            ) {
        try {
            log.info("Starting retrieve all users");
            Page p = PageHelper.startPage(page, size);

            ResultList<Student> resultList = studentService.retrieveStudentsByDepAndMajor(department, major);

            PageInfo info = new PageInfo(p.getResult());
            resultList.setPage(page);
            resultList.setSize(size);
            resultList.setTotal(info.getTotal());
            log.info("Retrieve student by department succeed");
            return ResponseEntity.ok().body(new BaseResponse<>(resultList));
        } finally {
            PageHelper.clearPage();
        }
    }

    @GetMapping("/students/dep/{department}/major/{major}/degree/{degree}/v1")
    @ApiOperation("获取某学部某专业某学位下的学生")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "department", value = "学部名", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "major", value = "专业名", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "degree", value = "学位名", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "page", value = "页数", required = false, defaultValue = "1", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页个数", required = false, defaultValue = "8", dataType = "int")
    })
    public ResponseEntity<BaseResponse<ResultList<Student>>> retrieveStudentsByDepAndMajorAndDegree
            (
                    @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                    @RequestParam(value = "size", required = false, defaultValue = "8") Integer size,
                    @PathVariable String department,
                    @PathVariable String major,
                    @PathVariable String degree
            ) {
        try {
            log.info("Starting retrieve all users");
            Page p = PageHelper.startPage(page, size);

            ResultList<Student> resultList = studentService.retrieveStudentsByDepAndMajorAndDegree(department, major, degree);

            PageInfo info = new PageInfo(p.getResult());
            resultList.setPage(page);
            resultList.setSize(size);
            resultList.setTotal(info.getTotal());
            log.info("Retrieve student by department succeed");
            return ResponseEntity.ok().body(new BaseResponse<>(resultList));
        } finally {
            PageHelper.clearPage();
        }
    }

    @GetMapping("/students/dep/{department}/degree/{degree}/v1")
    @ApiOperation("获取某学部某学位下的学生")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "department", value = "学部名", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "degree", value = "学位名", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "page", value = "页数", required = false, defaultValue = "1", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页个数", required = false, defaultValue = "8", dataType = "int")
    })
    public ResponseEntity<BaseResponse<ResultList<Student>>> retrieveStudentsByDepAndDegree
            (
                    @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                    @RequestParam(value = "size", required = false, defaultValue = "8") Integer size,
                    @PathVariable String department,
                    @PathVariable String degree
            ) {
        try {
            log.info("Starting retrieve all users");
            Page p = PageHelper.startPage(page, size);

            ResultList<Student> resultList = studentService.retrieveStudentsByDepAndDegree(department, degree);

            PageInfo info = new PageInfo(p.getResult());
            resultList.setPage(page);
            resultList.setSize(size);
            resultList.setTotal(info.getTotal());
            log.info("Retrieve student by department succeed");
            return ResponseEntity.ok().body(new BaseResponse<>(resultList));
        } finally {
            PageHelper.clearPage();
        }
    }
}

