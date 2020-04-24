package com.stusys.cattan.stuinfo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient(value = "id-generator")
public interface SnowFlakeServiceFeignDao {
    @GetMapping("/snowflake/long")
    Long getID();
}