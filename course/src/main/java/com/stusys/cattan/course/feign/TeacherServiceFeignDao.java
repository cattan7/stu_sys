package com.stusys.cattan.course.feign;

import com.stusys.cattan.course.common.BaseResponse;
import com.stusys.cattan.course.entity.Teacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(value = "teacherInfo")
public interface TeacherServiceFeignDao {

    @GetMapping("/teachers/{teacherID}/v1")
    ResponseEntity<BaseResponse<Teacher>> retrieveTeacherByID(@PathVariable Long teacherID);

}