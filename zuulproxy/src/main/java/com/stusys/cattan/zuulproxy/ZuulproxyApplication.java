package com.stusys.cattan.zuulproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
@EnableOAuth2Sso
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)

public class ZuulproxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulproxyApplication.class, args);
    }

}
