package com.stusys.cattan.userclient.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(value = "id-generator")
public interface SnowFlakeServiceFeignDao {
    @GetMapping("/snowflake/long")
    Long getID();
}