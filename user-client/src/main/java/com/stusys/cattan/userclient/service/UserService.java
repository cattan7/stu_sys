package com.stusys.cattan.userclient.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stusys.cattan.userclient.common.BaseResponse;
import com.stusys.cattan.userclient.common.ResultList;
import com.stusys.cattan.userclient.entity.User;
import com.stusys.cattan.userclient.exception.UserAlreadyExistsException;
import com.stusys.cattan.userclient.exception.UserNotExistsException;
import com.stusys.cattan.userclient.mapper.UserMapper;

import com.stusys.cattan.userclient.request.AddUserRequest;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService implements UserDetailsService {
    @Resource
    private UserMapper userMapper;

    @Autowired
    private SnowFlakeServiceFeign snowFlakeService;

    //  @GlobalTransactional(name = "add_user_tran", rollbackFor = Exception.class)
    public Long createUser(AddUserRequest addUserRequest) {
        //1. 检测是否已经注册
        User user = userMapper.retrieveUserByUserName(addUserRequest.getUserName());
        if (user != null) {
            String errorMsg = "The user with user name " + addUserRequest.getUserName() + " already been registered";
            throw new UserAlreadyExistsException(errorMsg, null, null);
        }


        //2. 未创建，则添加
        log.info("Add user", addUserRequest.getUserName());

        Boolean active = true;
        Date current = new Date();
        user = User.builder()
                .userid(snowFlakeService.getID())
                .username(addUserRequest.getUserName())
                .password(addUserRequest.getPassword())
                .role(addUserRequest.getRole())
                .createdAt(current)
                .active(active)
                .build();

        userMapper.addUser(user);
        log.info("Add user to graph succeed");
        // throw new UserAlreadyExistsException("errorMsg", null, null);
        return user.getUserid();

    }

    public void deleteUser(Long userid) {
        //1. 检测是否已经注册
        User user = userMapper.retrieveUserById(userid);
        if (user == null) {
            String errorMsg = "The user with user id " + userid + " haven't registered";
            throw new UserAlreadyExistsException(errorMsg, null, null);
        }

        userMapper.deleteUserByUserid(userid);

    }

    public ResultList<User> retrieveAllUsers() {
        log.info("Retrieve all users");
        List<User> users = userMapper.retrieveAllUsers();

        ResultList<User> result = new ResultList<>();

        result.setList(users.stream()
                .map(User -> User.builder()
                        .userid(User.getUserid())
                        .username(User.getUsername())
                        .role(User.getRole())
                        .active(User.getActive())
                        .createdAt(User.getCreatedAt())
                        .creator(User.getCreator())
                        .build())
                .collect(Collectors.toList()));

        return result;
    }

    public ResultList<User> retrieveUsersByRole(String role) {
        log.info("Retrieve users by role");
        List<User> users = userMapper.retrieveUsersByRole(role);

        ResultList<User> result = new ResultList<>();

        result.setList(users.stream()
                .map(User -> User.builder()
                        .userid(User.getUserid())
                        .username(User.getUsername())
                        .role(User.getRole())
                        .active(User.getActive())
                        .createdAt(User.getCreatedAt())
                        .creator(User.getCreator())
                        .build())
                .collect(Collectors.toList()));
        return result;
    }

    public User retrieveUserById(Long userid) {
        log.info("Retrieve users by role");
        User user = userMapper.retrieveUserById(userid);
        if (user == null) {
            String errorMsg = "The user with userid " + userid + " doesn't exist.";
            throw new UserNotExistsException(errorMsg, null, null);
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("Retrieve users by name");
        User user = userMapper.retrieveUserByUserName(s);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 加密
        String encodedPassword = passwordEncoder.encode(user.getPassword().trim());
        user.setPassword(encodedPassword);

        if (user == null) {
            String errorMsg = "The user with name " + s + " doesn't exist.";
            throw new UserNotExistsException(errorMsg, null, null);
        }
        log.info("Retrieve users by name succeed");
        return user;

    }
}
