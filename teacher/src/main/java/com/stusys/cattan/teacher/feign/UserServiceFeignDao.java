package com.stusys.cattan.teacher.feign;

import com.stusys.cattan.teacher.common.BaseResponse;
import com.stusys.cattan.teacher.common.ResultList;
import com.stusys.cattan.teacher.dto.User;
import com.stusys.cattan.teacher.request.AddUserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(value = "user")
public interface UserServiceFeignDao {
    @PostMapping("/users/v1")
    ResponseEntity<BaseResponse<Long>> addUser(@RequestBody AddUserRequest addUserRequest);

    @GetMapping("/users/v1")
    ResponseEntity<BaseResponse<ResultList<User>>> retrieveUsers();

    @GetMapping("/users/role/{role}/v1")
    ResponseEntity<BaseResponse<ResultList<User>>> retrieveUsersByRole(@PathVariable String role);

    @GetMapping("/feign/users/{userid}/v1")
    User retrieveUsersById(@PathVariable("userid") Long userid);

    @DeleteMapping("/users/{userid}/v1")
    void deleteUser(@PathVariable Long userid);
}


