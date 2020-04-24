package com.stusys.cattan.stuinfo.feign;

import com.stusys.cattan.stuinfo.common.BaseResponse;

import com.stusys.cattan.stuinfo.request.AddUserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
//@FeignClient(value="user",fallback = UserFeignImpl.class)
@FeignClient(value = "user")
public interface UserServiceFeignDao {
    @PostMapping("/users/v1")
    ResponseEntity<BaseResponse<Long>> addUser(@RequestBody AddUserRequest addUserRequest);

    @DeleteMapping("/users/{userid}/v1")
    void deleteUser(@PathVariable Long userid);

//    @GetMapping("/users/v1")
//    ResponseEntity<BaseResponse<ResultList<User>>> retrieveUsers();
//
//    @GetMapping("/users/role/{role}/v1")
//    ResponseEntity<BaseResponse<ResultList<User>>> retrieveUsersByRole(@PathVariable String role);
//
//    @GetMapping("/feign/users/{userid}/v1")
//    User retrieveUsersById(@PathVariable Long userid);
}


