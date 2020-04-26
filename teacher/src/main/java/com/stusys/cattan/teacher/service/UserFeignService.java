package com.stusys.cattan.teacher.service;

import com.stusys.cattan.teacher.common.BaseResponse;
import com.stusys.cattan.teacher.common.ResultList;
import com.stusys.cattan.teacher.dto.User;
import com.stusys.cattan.teacher.feign.UserServiceFeignDao;
import com.stusys.cattan.teacher.request.AddUserRequest;
import feign.Feign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Service
public class UserFeignService {
    @Autowired
    private UserServiceFeignDao userServiceFeignDao;

    @PostMapping("/users/v1")
    public ResponseEntity<BaseResponse<Long>> addUser(@RequestBody AddUserRequest addUserRequest) {
        Boolean active = true;
        Date current = new Date();
        User user = User.builder()
                .username(addUserRequest.getUserName())
                .password(addUserRequest.getPassword())
                .role(addUserRequest.getRole())
                .createdAt(current)
                .active(active)
                .build();
        return userServiceFeignDao.addUser(addUserRequest);
    }

    @GetMapping("/users/v1")
    public ResponseEntity<BaseResponse<ResultList<User>>> retrieveUsers() {
        return userServiceFeignDao.retrieveUsers();
    }

    @GetMapping("/users/role/{role}/v1")
    public ResponseEntity<BaseResponse<ResultList<User>>> retrieveUsersByRole(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "8") Integer size,
            @PathVariable String role) {
        return userServiceFeignDao.retrieveUsersByRole(role);
    }

    public User retrieveUsersById(Long userid) {

        return userServiceFeignDao.retrieveUsersById(userid);
    }

    @DeleteMapping("/users/{userid}/v1")
    public void deleteUser(@PathVariable Long userid) {
        userServiceFeignDao.deleteUser(userid);
    }
}