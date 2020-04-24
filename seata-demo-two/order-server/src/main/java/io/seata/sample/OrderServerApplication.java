package io.seata.sample;

import com.alibaba.cloud.seata.GlobalTransactionAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 订单服务
 *
 * @author wangzhongxiang
 */
@SpringBootApplication(exclude = GlobalTransactionAutoConfiguration.class)
@MapperScan("io.seata.sample.dao")
@EnableDiscoveryClient
@EnableFeignClients
public class OrderServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServerApplication.class, args);
    }

}