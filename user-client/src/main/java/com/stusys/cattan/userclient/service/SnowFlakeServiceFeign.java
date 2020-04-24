package com.stusys.cattan.userclient.service;

import com.stusys.cattan.userclient.feign.SnowFlakeServiceFeignDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Service
public class SnowFlakeServiceFeign {

    @Autowired
    private SnowFlakeServiceFeignDao snowFlakeServiceFeignDao;

    @PostMapping("/snowflake/long")
    public Long getID() {
        return snowFlakeServiceFeignDao.getID();
    }

}
