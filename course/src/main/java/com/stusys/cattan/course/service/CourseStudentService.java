package com.stusys.cattan.course.service;

import com.stusys.cattan.course.common.ResultList;
import com.stusys.cattan.course.entity.CourseInstance;
import com.stusys.cattan.course.entity.CourseStudent;
import com.stusys.cattan.course.entity.Student;
import com.stusys.cattan.course.entity.Teacher;
import com.stusys.cattan.course.exception.CourseInstanceAlreadyExistsException;
import com.stusys.cattan.course.mapper.CourseInstanceMapper;
import com.stusys.cattan.course.mapper.CourseMapper;
import com.stusys.cattan.course.mapper.CourseStudentMapper;
import com.stusys.cattan.course.response.CourseInstanceRes;
import com.stusys.cattan.course.response.CourseStudentRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j

public class CourseStudentService {
    @Resource
    private CourseStudentMapper courseStudentMapper;

    @Autowired
    private StudentFeignService studentFeignService;

    @Autowired
    private SnowFlakeServiceFeign snowFlakeService;

    public Boolean updateGrade(Long courseStudentID, Double grade) {
        try {
            return courseStudentMapper.updateGrade(courseStudentID, grade);
        } catch (Exception e) {
            return false;
        }
    }

    public Long addCourseStudent(CourseStudent courseStudent) {

        CourseStudent foundCourseStudent = courseStudentMapper.retrieveCourseInstanceByIDAndStudent(courseStudent.getCourseInstanceID(), courseStudent.getStudentID());
        if (foundCourseStudent != null) {
            String errorMsg = "The course student with course  " + courseStudent.getStudentID() + " and courseID" + courseStudent.getCourseInstanceID() + " already been Registered";
            throw new CourseInstanceAlreadyExistsException(errorMsg, null, null);
        }

        Student student = studentFeignService.retrieveStudentByID(courseStudent.getStudentID());
        log.info("Add student" + student);
        if (student == null) {
            String errorMsg = "The student  " + courseStudent.getStudentID() + " hasn't been Registered";
            throw new CourseInstanceAlreadyExistsException(errorMsg, null, null);
        }

        try {

            CourseStudent res = CourseStudent.builder()
                    .courseStudentID(snowFlakeService.getID())
                    .courseInstanceID(courseStudent.getCourseInstanceID())
                    .studentID(courseStudent.getStudentID())
                    .build();

            courseStudentMapper.addCourseStudent(res);

            log.info("Add course succeed");

            return res.getStudentID();

        } catch (Exception e) {

            log.info("Add course failed ");

            throw e;
        }

    }

    private ResultList<CourseInstanceRes> makeCourseInstances(ResultList<CourseInstanceRes> result, List<CourseInstanceRes> students) {
        result.setList(students.stream()
                .map(CourseInstanceRes -> CourseInstanceRes.builder()
                        .courseInstanceID(CourseInstanceRes.getCourseInstanceID())
                        .courseID(CourseInstanceRes.getCourseID())
                        .teacherID(CourseInstanceRes.getTeacherID())
                        .courseName(CourseInstanceRes.getCourseName())
                        .time(CourseInstanceRes.getTime())
                        .evaluationMode(CourseInstanceRes.getEvaluationMode())
                        .credit(CourseInstanceRes.getCredit())
                        .required(CourseInstanceRes.getRequired())
                        .department(CourseInstanceRes.getDepartment())
                        .build())
                .collect(Collectors.toList()));

        return result;
    }

    public List<CourseStudentRes> retrieveCoursesByStudentID(Long studentID) {

        log.info("Retrieve courses by student id");

        List<CourseStudentRes> courseStudentRes = courseStudentMapper.retrieveCourseInstanceByStudentID(studentID);

        return courseStudentRes;

    }

    public ResultList<CourseStudentRes> retrieveStudentsByCourseInstanceID(Long courseInstanceID) {

        log.info("Retrieve course instance by course name");

        List<CourseStudent> courses = courseStudentMapper.retrieveCourseStudentsByCourseInstanceID(courseInstanceID);


        Iterator it = courses.iterator();

        List<CourseStudentRes> students = new ArrayList<>();


        while (it.hasNext()) {
            Object courseStudent = it.next();

            if (!(courseStudent instanceof CourseStudent)) {
                String errorMsg = " can't cast Object to CourseStudent";
                throw new CourseInstanceAlreadyExistsException(errorMsg, null, null);
            }

            CourseStudent cs = (CourseStudent) courseStudent;

            log.info("find student " + cs.getStudentID());

            Student student = studentFeignService.retrieveStudentByID(cs.getStudentID());

            log.info("find student " + student);

            if (student == null) {
                String errorMsg = "The student  " + cs.getStudentID() + " hasn't been Registered";
                throw new CourseInstanceAlreadyExistsException(errorMsg, null, null);
            }

            CourseStudentRes courseStudentRes = CourseStudentRes.builder()
                    .courseStudentID(cs.getCourseStudentID())
                    .courseInstanceID(cs.getCourseInstanceID())
                    .studentID(cs.getStudentID())
                    .name(student.getName())
                    .department(student.getDepartment())
                    .grade(cs.getGrade())
                    .build();

            log.info("course student res" + courseStudentRes);
            students.add(courseStudentRes);
        }

        ResultList<CourseStudentRes> result = new ResultList<>();

        result.setList(students.stream()
                .map(CourseStudentRes -> CourseStudentRes.builder()
                        .courseStudentID(CourseStudentRes.getCourseStudentID())
                        .courseInstanceID(CourseStudentRes.getCourseInstanceID())
                        .studentID(CourseStudentRes.getStudentID())
                        .name(CourseStudentRes.getName())
                        .department(CourseStudentRes.getDepartment())
                        .grade(CourseStudentRes.getGrade())
                        .build())
                .collect(Collectors.toList()));

        return result;

    }


}

