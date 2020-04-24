package com.stusys.cattan.teacher.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stusys.cattan.teacher.common.BaseResponse;
import com.stusys.cattan.teacher.common.ResultList;
import com.stusys.cattan.teacher.entity.Teacher;
import com.stusys.cattan.teacher.request.AddTeacherRequest;
import com.stusys.cattan.teacher.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@Api(tags = "教师业务信息相关API")
@RestController

public class TeacherController {
    @Resource
    private TeacherService teacherService;

    @PostMapping("/teachers/v1")
    @ApiOperation("管理员注册教师")
    public ResponseEntity<BaseResponse<Object>> createTeacher
            (
                    @RequestBody AddTeacherRequest addTeacherRequest
            ) {
        {
            log.info("Starting register new Teacher {}", addTeacherRequest);
            Long userId = teacherService.addTeacher(addTeacherRequest);
            log.info("Register teacher succeed");
            return ResponseEntity.ok().body(new BaseResponse<>(userId));
        }
    }

    @GetMapping("/teachers/v1")
    @ApiOperation("获取所有教师")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", required = false, defaultValue = "1", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页个数", required = false, defaultValue = "8", dataType = "int")
    })
    public ResponseEntity<BaseResponse<ResultList<Teacher>>> retrieveUsers
            (
                    @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                    @RequestParam(value = "size", required = false, defaultValue = "8") Integer size
            ) {
        try {
            log.info("Starting retrieve all users");
            Page p = PageHelper.startPage(page, size);

            ResultList<Teacher> resultList = teacherService.retrieveAllTeachers();

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

    @GetMapping("/teachers/{teacherID}/v1")
    @ApiOperation("获取某学部下的教师")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherID", value = "id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "page", value = "页数", required = false, defaultValue = "1", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页个数", required = false, defaultValue = "8", dataType = "int")
    })
    public ResponseEntity<BaseResponse<Teacher>> retrieveTeacherByID
            (
                    @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                    @RequestParam(value = "size", required = false, defaultValue = "8") Integer size,
                    @PathVariable Long teacherID) {
        try {
            log.info("Retrieve teacher by id succeed");
            Page p = PageHelper.startPage(page, size);

            Teacher teacher = teacherService.retrieveTeacherByTeacherID(teacherID);

            log.info("Retrieve teacher by id succeed");
            return ResponseEntity.ok().body(new BaseResponse<>(teacher));
        } finally {
            PageHelper.clearPage();
        }
    }

    @GetMapping("/teachers/dep/{department}/v1")
    @ApiOperation("获取某学部下的教师")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "department", value = "学部名", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "page", value = "页数", required = false, defaultValue = "1", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页个数", required = false, defaultValue = "8", dataType = "int")
    })
    public ResponseEntity<BaseResponse<ResultList<Teacher>>> retrieveUsersByRole
            (
                    @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                    @RequestParam(value = "size", required = false, defaultValue = "8") Integer size,
                    @PathVariable String department) {
        try {
            log.info("Starting retrieve all users");
            Page p = PageHelper.startPage(page, size);

            ResultList<Teacher> resultList = teacherService.retrieveTeachersByDepartment(department);

            PageInfo info = new PageInfo(p.getResult());
            resultList.setPage(page);
            resultList.setSize(size);
            resultList.setTotal(info.getTotal());
            log.info("Retrieve teacher by department succeed");
            return ResponseEntity.ok().body(new BaseResponse<>(resultList));
        } finally {
            PageHelper.clearPage();
        }
    }
}

