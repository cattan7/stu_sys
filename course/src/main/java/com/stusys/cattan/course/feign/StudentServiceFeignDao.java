package com.stusys.cattan.course.feign;

import com.stusys.cattan.course.common.BaseResponse;
import com.stusys.cattan.course.entity.Student;
import com.stusys.cattan.course.entity.Teacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Component
@FeignClient(value = "stuinfo")
public interface StudentServiceFeignDao {

    @GetMapping("/student/{studentID}/v1")
    ResponseEntity<BaseResponse<Student>> retrieveStudentByID(@PathVariable Long studentID);

}
