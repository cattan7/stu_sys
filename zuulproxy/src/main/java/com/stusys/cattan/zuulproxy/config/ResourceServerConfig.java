package com.stusys.cattan.zuulproxy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.context.request.RequestContextListener;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                //去掉获取token会被拦截
                .antMatchers("/userService/**","/client/**").permitAll()
                .anyRequest().authenticated();
    }
//    @Bean
//    public RequestContextListener requestContextListener() {
//        return new RequestContextListener();
//    }
}