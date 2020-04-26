package com.stusys.cattan.course.service;

import com.stusys.cattan.course.common.ResultList;
import com.stusys.cattan.course.entity.CourseInstance;
import com.stusys.cattan.course.entity.Teacher;
import com.stusys.cattan.course.exception.CourseInstanceAlreadyExistsException;
import com.stusys.cattan.course.mapper.CourseInstanceMapper;
import com.stusys.cattan.course.mapper.CourseMapper;
import com.stusys.cattan.course.response.CourseInstanceRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j

public class CourseInstanceService {
    @Resource
    private CourseInstanceMapper courseInstanceMapper;

    @Autowired
    private TeacherFeignService teacherFeignService;

    @Autowired
    private SnowFlakeServiceFeign snowFlakeService;

    public Long addCourseInstance(CourseInstance courseInstance) {

        CourseInstanceRes foundCourseInstance = courseInstanceMapper.retrieveCourseInstanceByIDAndTeacher(courseInstance.getCourseID(), courseInstance.getTeacherID());
        if (foundCourseInstance != null) {
            String errorMsg = "The course instance with teacher  " + courseInstance.getTeacherID() + " and courseID" + courseInstance.getCourseID() + " already been Registered";
            throw new CourseInstanceAlreadyExistsException(errorMsg, null, null);
        }

        Teacher teacher = teacherFeignService.retrieveTeacherByID(courseInstance.getTeacherID());
        if (teacher == null) {
            String errorMsg = "The teacher  " + courseInstance.getTeacherID() + " hasn't been Registered";
            throw new CourseInstanceAlreadyExistsException(errorMsg, null, null);
        }

        try {

            CourseInstance res = CourseInstance.builder()
                    .courseInstanceID(snowFlakeService.getID())
                    .courseID(courseInstance.getCourseID())
                    .teacherID(courseInstance.getTeacherID())
                    .time(courseInstance.getTime())
                    .evaluationMode(courseInstance.getEvaluationMode())
                    .build();

            courseInstanceMapper.addCourseInstance(res);

            log.info("Add course succeed");

            return res.getCourseID();

        } catch (Exception e) {

            log.info("Add course failed ");

            throw e;
        }

    }

    public boolean deleteCourseInstance(Long courseInstanceID) {
        CourseInstanceRes courseInstance = courseInstanceMapper.retrieveCourseInstanceByID(courseInstanceID);
        if (courseInstance == null) {
            return false;
        }
        courseInstanceMapper.deleteCourseInstanceByCourseInstanceID(courseInstanceID);
        return true;
    }

    public CourseInstanceRes retrieveCourseInstanceByID(Long courseInstanceID) {

        CourseInstanceRes courseInstance = courseInstanceMapper.retrieveCourseInstanceByID(courseInstanceID);

        return courseInstance;

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

    public ResultList<CourseInstanceRes> retrieveAllCourseInstances() {
        log.info("Retrieve all students");
        List<CourseInstanceRes> students = courseInstanceMapper.retrieveAllCourseInstances();
        log.info("Retrieve all students " + students);
        ResultList<CourseInstanceRes> result = new ResultList<>();

        result = this.makeCourseInstances(result, students);

        return result;
    }

    public ResultList<CourseInstanceRes> retrieveCourseInstancesByCourseName(String courseName) {

        log.info("Retrieve course instance by course name");

        List<CourseInstanceRes> courses = courseInstanceMapper.retrieveCourseInstancesByCourseName(courseName);

        ResultList<CourseInstanceRes> result = new ResultList<>();

        result = this.makeCourseInstances(result, courses);

        return result;
    }

    public ResultList<CourseInstanceRes> retrieveCourseInstancesByTeacherID(Long teacherID) {

        log.info("Retrieve course instance by course name");

        List<CourseInstanceRes> courses = courseInstanceMapper.retrieveCourseInstancesByTeacherID(teacherID);

        ResultList<CourseInstanceRes> result = new ResultList<>();

        this.makeCourseInstances(result, courses);

        return result;
    }

    public CourseInstanceRes retrieveCourseInstanceByIDAndTeacherID(Long courseID, Long teacherID) {
        log.info("Retrieve course instance by course name");

        CourseInstanceRes course = courseInstanceMapper.retrieveCourseInstanceByIDAndTeacher(courseID, teacherID);

        return course;
    }

    public CourseInstanceRes retrieveCourseByCourseID(Long courseID) {

        log.info("Retrieve course instance by course name");

        CourseInstanceRes course = courseInstanceMapper.retrieveCourseInstanceByID(courseID);

        return course;
    }

}
