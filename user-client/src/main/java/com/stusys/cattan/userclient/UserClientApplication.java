package com.stusys.cattan.userclient;

import io.seata.rm.datasource.DataSourceProxy;
import com.alibaba.druid.pool.DruidDataSource;
import lombok.Value;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

//
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
@MapperScan("com.stusys.cattan.userclient.mapper")
@EnableFeignClients
@EnableResourceServer
//@Import(DataSourceProxyAutoConfiguration.class)


public class UserClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserClientApplication.class, args);
    }
}
