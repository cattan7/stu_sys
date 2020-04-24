package com.stusys.cattan.stuinfo.service;

import com.stusys.cattan.stuinfo.feign.SnowFlakeServiceFeignDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class SnowFlakeServiceFeign {

    @Autowired
    private SnowFlakeServiceFeignDao snowFlakeServiceFeignDao;

    @PostMapping("/snowflake/long")
    public Long getID() {
        return snowFlakeServiceFeignDao.getID();
    }

}
