package com.stusys.cattan.teacher.controller;

import com.stusys.cattan.teacher.common.BaseResponse;
import com.stusys.cattan.teacher.common.ResultList;
import com.stusys.cattan.teacher.dto.User;
import com.stusys.cattan.teacher.feign.UserServiceFeignDao;
import com.stusys.cattan.teacher.request.AddUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FeignUserController {
    @Autowired
    UserServiceFeignDao userServiceFeignDao;

    @GetMapping("/users/v1")
    public ResponseEntity<BaseResponse<ResultList<User>>> retrieveUsers() {
        return userServiceFeignDao.retrieveUsers();
    }


    @PostMapping("/users/v1")
    public ResponseEntity<BaseResponse<Long>> addUser(@RequestBody AddUserRequest addUserRequest) {
        return userServiceFeignDao.addUser(addUserRequest);
    }

    @GetMapping("/users/role/{role}/v1")
    public ResponseEntity<BaseResponse<ResultList<User>>> retrieveUsersByRole(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "8") Integer size,
            @PathVariable String role) {
        return userServiceFeignDao.retrieveUsersByRole(role);
    }

    @GetMapping("/feign/users/{userid}/v1")
    public User retrieveUsersById(@PathVariable Long userid) {
        return userServiceFeignDao.retrieveUsersById(userid);
    }
}
