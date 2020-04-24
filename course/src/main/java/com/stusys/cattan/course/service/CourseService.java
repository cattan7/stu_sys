package com.stusys.cattan.course.service;

import com.stusys.cattan.course.entity.Course;
import com.stusys.cattan.course.exception.CourseAlreadyExistsException;
import com.stusys.cattan.course.mapper.CourseMapper;
import com.stusys.cattan.course.common.ResultList;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j

public class CourseService {
    @Resource
    private CourseMapper courseMapper;

    @Autowired
    private SnowFlakeServiceFeign snowFlakeService;

    public Long addCourse(Course course) {

        Course foundCourse = courseMapper.retrieveCourseByCourseName(course.getCourseName());
        if (foundCourse != null) {
            String errorMsg = "The course with course id " + course.getCourseID() + " already been Registered";
            throw new CourseAlreadyExistsException(errorMsg, null, null);
        }
        try {

            course = Course.builder()
                    .courseID(snowFlakeService.getID())
                    .courseName(course.getCourseName())
                    .required(course.getRequired())
                    .credit(course.getCredit())
                    .department(course.getDepartment())
                    .build();

            courseMapper.addCourse(course);

            log.info("Add course succeed");

            return course.getCourseID();

        } catch (Exception e) {

            log.info("Add course failed " + course.getDepartment());

            throw e;
        }

    }

    public boolean deleteCourse(Long courseID) {
        Course course = courseMapper.retrieveCourseByCourseID(courseID);
        if (course == null) {
            return false;
        }
        courseMapper.deleteCourseByCourseID(courseID);
        return true;
    }


    private ResultList<Course> makeCourses(ResultList<Course> result, List<Course> students) {
        result.setList(students.stream()
                .map(Course -> Course.builder()
                        .courseName(Course.getCourseName())
                        .courseID(Course.getCourseID())
                        .department(Course.getDepartment())
                        .required(Course.getRequired())
                        .credit(Course.getCredit())
                        .build())
                .collect(Collectors.toList()));

        return result;
    }

    public ResultList<Course> retrieveAllCourses() {
        log.info("Retrieve all students");
        List<Course> students = courseMapper.retrieveAllCourses();
        log.info("Retrieve all students " + students);
        ResultList<Course> result = new ResultList<>();

        this.makeCourses(result, students);

        return result;
    }

    public ResultList<Course> retrieveCoursesByDep(String department) {
        log.info("Retrieve students by dep " + department);

        List<Course> courses = courseMapper.retrieveCoursesByDep(department);

        ResultList<Course> result = new ResultList<>();

        this.makeCourses(result, courses);

        return result;
    }

    public Course retrieveCourseByCourseName(String courseName) {
        log.info("Retrieve students by dep " + courseName);

        Course course = courseMapper.retrieveCourseByCourseName(courseName);

        return course;
    }

    public Course retrieveCourseByCourseID(Long courseID) {

        log.info("Retrieve students by dep " + courseID);

        Course course = courseMapper.retrieveCourseByCourseID(courseID);

        return course;
    }
}

