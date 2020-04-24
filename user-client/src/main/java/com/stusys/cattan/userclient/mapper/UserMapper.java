package com.stusys.cattan.userclient.mapper;

import com.stusys.cattan.userclient.entity.User;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {


    void addUser(User user);

    User retrieveUserByUserName(@Param("username") String username);

    User retrieveUserById(@Param("userid") Long id);

    List<User> retrieveUsersByRole(@Param("role") String role);

    List<User> retrieveAllUsers();

    void deleteUserByUserid(@Param("userid") Long userid);


}