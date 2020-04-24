package com.stusys.cattan.userclient.controller;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.stusys.cattan.userclient.common.BaseResponse;
import com.stusys.cattan.userclient.common.ResultList;
import com.stusys.cattan.userclient.entity.User;
import com.stusys.cattan.userclient.exception.UserAlreadyExistsException;
import com.stusys.cattan.userclient.mapper.UserMapper;

import com.stusys.cattan.userclient.request.AddUserRequest;
import com.stusys.cattan.userclient.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import lombok.extern.slf4j.Slf4j;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Api(tags = "用户业务信息相关API")
@RestController
@Transactional
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/users/v1")
    @ApiOperation("管理员添加用户")
    public ResponseEntity<BaseResponse<Long>> createUser
            (
                    @RequestBody AddUserRequest addUserRequest
            ) {
        {
            log.info("Starting register new user {}", addUserRequest);
            Long userId = userService.createUser(addUserRequest);
            log.info("Register user succeed");
            return ResponseEntity.ok().body(new BaseResponse<>(userId));
        }
    }

    @GetMapping("/users/v1")
    @ApiOperation("获取所有用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", required = false, defaultValue = "1", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页个数", required = false, defaultValue = "8", dataType = "int")
    })
    public ResponseEntity<BaseResponse<ResultList<User>>> retrieveUsers
            (
                    @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                    @RequestParam(value = "size", required = false, defaultValue = "8") Integer size,
                    Authentication authentication
            ) {
        try {
            log.info("Starting retrieve all users");
            Page p = PageHelper.startPage(page, size);

            ResultList<User> resultList = userService.retrieveAllUsers();

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

    @GetMapping("/users/role/{role}/v1")
    @ApiOperation("获取某角色下的用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role", value = "角色名", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "page", value = "页数", required = false, defaultValue = "1", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页个数", required = false, defaultValue = "8", dataType = "int")
    })
    public ResponseEntity<BaseResponse<ResultList<User>>> retrieveUsersByRole
            (
                    @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                    @RequestParam(value = "size", required = false, defaultValue = "8") Integer size,
                    @PathVariable String role) {
        try {
            log.info("Starting retrieve all users");
            Page p = PageHelper.startPage(page, size);

            ResultList<User> resultList = userService.retrieveUsersByRole(role);

            PageInfo info = new PageInfo(p.getResult());
            resultList.setPage(page);
            resultList.setSize(size);
            resultList.setTotal(info.getTotal());
            log.info("Retrieve users by role succeed");
            return ResponseEntity.ok().body(new BaseResponse<>(resultList));
        } finally {
            PageHelper.clearPage();
        }

    }

    @GetMapping("/users/{userid}/v1")
    @ApiOperation("获取某角色下的用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "角色名", required = true, dataType = "Long", paramType = "path")
    })
    public ResponseEntity<BaseResponse<User>> retrieveStudentByStudentID
            (
                    @PathVariable Long userid) {
        try {
            log.info("Starting user by userid");

            User result = userService.retrieveUserById(userid);


            log.info("Retrieve users by role succeed");
            return ResponseEntity.ok().body(new BaseResponse<>(result));
        } finally {

        }
    }

    @DeleteMapping("/users/{userid}/v1")
    @ApiOperation("管理员删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "角色名", required = true, dataType = "Long", paramType = "path")
    })
    public ResponseEntity<BaseResponse<String>> deleteUser
            (
                    @PathVariable Long userid) {
        {
            userService.deleteUser(userid);
            log.info("Register user succeed");
            return ResponseEntity.ok().body(new BaseResponse<>("删除成功"));
        }
    }

    @GetMapping("/feign/users/{userid}/v1")
    @ApiOperation("获取某角色下的用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "角色名", required = true, dataType = "Long", paramType = "path")
    })
    public User feignRetrieveStudentByStudentID
            (
                    @PathVariable Long userid) {
        try {
            log.info("Starting user by userid");

            User user = userService.retrieveUserById(userid);


            log.info("Retrieve users by role succeed");
            return user;
        } finally {

        }

    }
}
