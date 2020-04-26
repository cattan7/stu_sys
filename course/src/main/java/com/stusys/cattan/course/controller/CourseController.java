package com.stusys.cattan.course.controller;

import com.stusys.cattan.course.common.BaseResponse;
import com.stusys.cattan.course.entity.Course;
import com.stusys.cattan.course.entity.CourseInstance;
import com.stusys.cattan.course.entity.CourseStudent;
import com.stusys.cattan.course.entity.Student;
import com.stusys.cattan.course.exception.CourseInstanceAlreadyExistsException;
import com.stusys.cattan.course.response.CourseInstanceRes;
import com.stusys.cattan.course.response.CourseStudentRes;
import com.stusys.cattan.course.service.CourseInstanceService;
import com.stusys.cattan.course.service.CourseService;
import com.stusys.cattan.course.common.ResultList;

import com.stusys.cattan.course.service.CourseStudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Api(tags = "课程业务信息相关API")
@RestController

public class CourseController {
    @Resource
    private CourseService courseService;

    @Resource
    private CourseInstanceService courseInstanceService;

    @Resource
    private CourseStudentService courseStudentService;

    @PostMapping("/course/v1")
    @ApiOperation("管理员创建课程")
    public ResponseEntity<BaseResponse<Object>> createCourse
            (
                    @RequestBody Course course
            ) {
        {
            log.info("Starting register new Course {}", course);
            Long userId = courseService.addCourse(course);
            log.info("Register course succeed");
            return ResponseEntity.ok().body(new BaseResponse<>(userId));
        }
    }

    @GetMapping("/course/v1")
    @ApiOperation("获取所有课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", required = false, defaultValue = "1", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页个数", required = false, defaultValue = "8", dataType = "int")
    })
    public ResponseEntity<BaseResponse<ResultList<Course>>> retrieveUsers
            (
                    @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                    @RequestParam(value = "size", required = false, defaultValue = "8") Integer size
            ) {
        try {
            log.info("Starting retrieve all users");
            Page p = PageHelper.startPage(page, size);

            ResultList<Course> resultList = courseService.retrieveAllCourses();

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

    @GetMapping("/courses/dep/{department}/v1")
    @ApiOperation("获取某学部下的课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "department", value = "学部名", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "page", value = "页数", required = false, defaultValue = "1", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页个数", required = false, defaultValue = "8", dataType = "int")
    })
    public ResponseEntity<BaseResponse<ResultList<Course>>> retrieveUsersByRole
            (
                    @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                    @RequestParam(value = "size", required = false, defaultValue = "8") Integer size,
                    @PathVariable String department) {
        try {
            log.info("Starting retrieve all users");
            Page p = PageHelper.startPage(page, size);

            ResultList<Course> resultList = courseService.retrieveCoursesByDep(department);

            PageInfo info = new PageInfo(p.getResult());
            resultList.setPage(page);
            resultList.setSize(size);
            resultList.setTotal(info.getTotal());
            log.info("Retrieve course by department succeed");
            return ResponseEntity.ok().body(new BaseResponse<>(resultList));
        } finally {
            PageHelper.clearPage();
        }
    }

    @GetMapping("/courses/name/{courseName}/v1")
    @ApiOperation("获取课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseName", value = "课程名", required = true, dataType = "String", paramType = "path")
    })
    public ResponseEntity<BaseResponse<Course>> retrieveCourseByCourseName
            (
                    @PathVariable String courseName
            ) {
        try {
            log.info("Starting retrieve all users");

            Course course = courseService.retrieveCourseByCourseName(courseName);

            return ResponseEntity.ok().body(new BaseResponse<>(course));
        } finally {
            PageHelper.clearPage();
        }
    }

    @PostMapping("/course-instance/v1")
    @ApiOperation("管理员分配课程")
    public ResponseEntity<BaseResponse<Object>> createCourseInstance
            (
                    @RequestBody CourseInstance courseInstance
            ) {
        {
            log.info("Starting register new Course instance {}", courseInstance);

            Course course = courseService.retrieveCourseByCourseID(courseInstance.getCourseID());
            log.info("Find course " + course);
            if (course == null) {
                String errorMsg = "The course  " + courseInstance.getTeacherID() + " hasn't been Registered";
                throw new CourseInstanceAlreadyExistsException(errorMsg, null, null);
            }
            Long userId = courseInstanceService.addCourseInstance(courseInstance);
            log.info("Register course instance succeed");
            return ResponseEntity.ok().body(new BaseResponse<>(userId));
        }
    }

    @GetMapping("/courses-instance/v1")
    @ApiOperation("获取课程实例")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", required = false, defaultValue = "1", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页个数", required = false, defaultValue = "8", dataType = "int")
    })
    public ResponseEntity<BaseResponse<ResultList<CourseInstanceRes>>> retrieveCourseInstances
            (
                    @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                    @RequestParam(value = "size", required = false, defaultValue = "8") Integer size
            ) {
        try {
            log.info("Starting retrieve all users");

            ResultList<CourseInstanceRes> courses = courseInstanceService.retrieveAllCourseInstances();

            Page p = PageHelper.startPage(page, size);

            PageInfo info = new PageInfo(p.getResult());

            courses.setPage(page);
            courses.setSize(size);
            courses.setTotal(info.getTotal());

            return ResponseEntity.ok().body(new BaseResponse<>(courses));
        } finally {
            PageHelper.clearPage();
        }
    }

    @DeleteMapping("/course-instance/{id}/v1")
    @ApiOperation("管理员删除课程实例")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "课程实例id", required = true, dataType = "Long", paramType = "path")
    })
    public ResponseEntity<BaseResponse<String>> deleteUser
            (
                    @PathVariable Long id
            ) {
        courseInstanceService.deleteCourseInstance(id);
        log.info("delete course instance succeed");
        return ResponseEntity.ok().body(new BaseResponse<>("删除成功"));
    }


    @GetMapping("/courses-instance/name/{courseName}/v1")
    @ApiOperation("获取课程实例")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseName", value = "课程名", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "page", value = "页数", required = false, defaultValue = "1", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页个数", required = false, defaultValue = "8", dataType = "int")
    })
    public ResponseEntity<BaseResponse<ResultList<CourseInstanceRes>>> retrieveCourseInstancesByCourseName
            (
                    @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                    @RequestParam(value = "size", required = false, defaultValue = "8") Integer size,
                    @PathVariable String courseName
            ) {
        try {
            log.info("Starting retrieve all users");

            ResultList<CourseInstanceRes> courses = courseInstanceService.retrieveCourseInstancesByCourseName(courseName);

            Page p = PageHelper.startPage(page, size);

            PageInfo info = new PageInfo(p.getResult());

            courses.setPage(page);
            courses.setSize(size);
            courses.setTotal(info.getTotal());

            return ResponseEntity.ok().body(new BaseResponse<>(courses));
        } finally {
            PageHelper.clearPage();
        }
    }

    @GetMapping("/courses-instance/teacher/{teacherID}/v1")
    @ApiOperation("获取课程实例")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherID", value = "教师编号", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "page", value = "页数", required = false, defaultValue = "1", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页个数", required = false, defaultValue = "8", dataType = "int")
    })
    public ResponseEntity<BaseResponse<ResultList<CourseInstanceRes>>> retrieveCourseInstancesByTeacherID
            (
                    @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                    @RequestParam(value = "size", required = false, defaultValue = "8") Integer size,
                    @PathVariable Long teacherID
            ) {
        try {
            log.info("Starting retrieve all users");

            ResultList<CourseInstanceRes> courses = courseInstanceService.retrieveCourseInstancesByTeacherID(teacherID);

            Page p = PageHelper.startPage(page, size);

            PageInfo info = new PageInfo(p.getResult());

            courses.setPage(page);
            courses.setSize(size);
            courses.setTotal(info.getTotal());

            return ResponseEntity.ok().body(new BaseResponse<>(courses));
        } finally {
            PageHelper.clearPage();
        }
    }

    @GetMapping("/courses-instance/teacher/{teacherID}/course/{courseID}v1")
    @ApiOperation("获取课程实例")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherID", value = "教师编号", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "courseID", value = "课程编号", required = true, dataType = "Long", paramType = "path")

    })
    public ResponseEntity<BaseResponse<CourseInstanceRes>> retrieveCourseInstanceByIDAndTeacherID
            (
                    @PathVariable Long teacherID,
                    @PathVariable Long courseID
            ) {
        log.info("Starting retrieve all users");

        CourseInstanceRes courses = courseInstanceService.retrieveCourseInstanceByIDAndTeacherID(courseID, teacherID);

        return ResponseEntity.ok().body(new BaseResponse<>(courses));
    }

    @PostMapping("/course-student/v1")
    @ApiOperation("学生选课")
    public ResponseEntity<BaseResponse<Object>> addCourseStudent
            (
                    @RequestBody CourseStudent courseStudent
            ) {
        {
            log.info("Starting register new Course instance {}", courseStudent);

            CourseInstanceRes courseInstance = courseInstanceService.retrieveCourseByCourseID(courseStudent.getCourseInstanceID());
            log.info("Find course instance " + courseInstance);
            if (courseInstance == null) {
                String errorMsg = "The course instance  " + courseStudent.getCourseInstanceID() + " hasn't been Registered";
                throw new CourseInstanceAlreadyExistsException(errorMsg, null, null);
            }
            Long userId = courseStudentService.addCourseStudent(courseStudent);
            log.info("Register course instance succeed");
            return ResponseEntity.ok().body(new BaseResponse<>(userId));
        }
    }

    @GetMapping("/course-student/{courseInstanceID}/students/v1")
    @ApiOperation("获取课程学生")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseInstanceID", value = "课程编号", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "page", value = "页数", required = false, defaultValue = "1", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页个数", required = false, defaultValue = "8", dataType = "int")
    })
    public ResponseEntity<BaseResponse<ResultList<CourseStudentRes>>> retrieveStudentsByCourseInstanceID
            (
                    @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                    @RequestParam(value = "size", required = false, defaultValue = "8") Integer size,
                    @PathVariable Long courseInstanceID
            ) {
        log.info("Starting get students of course " + courseInstanceID);


        Page p = PageHelper.startPage(page, size);

        ResultList<CourseStudentRes> result = courseStudentService.retrieveStudentsByCourseInstanceID(courseInstanceID);
        log.info("Find students " + result);

        PageInfo info = new PageInfo(p.getResult());

        result.setPage(page);
        result.setSize(size);
        result.setTotal(info.getTotal());

        log.info("get students of course " + courseInstanceID + " succeed");
        return ResponseEntity.ok().body(new BaseResponse<>(result));
    }

    @GetMapping("/course-student/{studentID}/courses/v1")
    @ApiOperation("获取学生选课列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentID", value = "学生学号", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "page", value = "页数", required = false, defaultValue = "1", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页个数", required = false, defaultValue = "8", dataType = "int")
    })
    public ResponseEntity<BaseResponse<ResultList<CourseStudentRes>>> retrieveCourseResByStudentID
            (
                    @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                    @RequestParam(value = "size", required = false, defaultValue = "8") Integer size,
                    @PathVariable Long studentID
            ) {

        List<CourseStudentRes> courseStudentRes = courseStudentService.retrieveCoursesByStudentID(studentID);

        Iterator it = courseStudentRes.iterator();

        while (it.hasNext()) {
            Object item = it.next();

            if (!(item instanceof CourseStudentRes)) {
                String errorMsg = " can't cast Object to CourseStudentRes";
                throw new CourseInstanceAlreadyExistsException(errorMsg, null, null);
            }

            CourseStudentRes csr = (CourseStudentRes) item;

            CourseInstanceRes res = courseInstanceService.retrieveCourseInstanceByID(csr.getCourseInstanceID());

            log.info("find res " + res);

            if (res == null) {
                String errorMsg = "The course instance  " + csr.getCourseInstanceID() + " hasn't been Registered";
                throw new CourseInstanceAlreadyExistsException(errorMsg, null, null);
            }


            csr.setName(res.getCourseName());
            csr.setDepartment(res.getDepartment());
            csr.setCredit(res.getCredit());
            csr.setRequired(res.getRequired());
        }

        ResultList<CourseStudentRes> result = new ResultList<>();

        result.setList(courseStudentRes.stream()
                .map(CourseStudentRes -> CourseStudentRes.builder()
                        .courseStudentID(CourseStudentRes.getCourseStudentID())
                        .courseInstanceID(CourseStudentRes.getCourseInstanceID())
                        .teacherID(CourseStudentRes.getTeacherID())
                        .name(CourseStudentRes.getName())
                        .department(CourseStudentRes.getDepartment())
                        .required(CourseStudentRes.getRequired())
                        .credit(CourseStudentRes.getCredit())
                        .grade(CourseStudentRes.getGrade())
                        .build())
                .collect(Collectors.toList()));

        Page p = PageHelper.startPage(page, size);

        PageInfo info = new PageInfo(p.getResult());

        result.setPage(page);
        result.setSize(size);
        result.setTotal(info.getTotal());

        return ResponseEntity.ok().body(new BaseResponse<>(result));
    }

    @PutMapping("/course-student/{courseStudentID}/grade/v1")
    @ApiOperation("更新成绩")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "courseStudentID", value = "courseStudentID", required = true, dataType = "Long", paramType = "path")
    })
    public ResponseEntity<BaseResponse<String>> updataGrade
            (
                    @PathVariable Long courseStudentID,
                    @RequestBody Double grade
            ) {
        if (courseStudentService.updateGrade(courseStudentID, grade)) {
            return ResponseEntity.ok().body(new BaseResponse<>("ok"));
        } else {
            return ResponseEntity.ok().body(new BaseResponse<>("error"));
        }
    }
}

