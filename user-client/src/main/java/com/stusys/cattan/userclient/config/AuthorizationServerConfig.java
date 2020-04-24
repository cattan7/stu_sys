package com.stusys.cattan.userclient.config;

import org.springframework.http.HttpMethod;
import com.stusys.cattan.userclient.component.JwtTokenEnhancer;
import com.stusys.cattan.userclient.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证服务器配置
 * Created by macro on 2019/9/30.
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private UserService userService;

    @Autowired
    @Qualifier("jwtTokenStore")
    private TokenStore tokenStore;
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private JwtTokenEnhancer jwtTokenEnhancer;
    //
//    /**
//     * 使用密码模式需要配置
//     */
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
////        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
////        List<TokenEnhancer> delegates = new ArrayList<>();
////        delegates.add(jwtTokenEnhancer); //配置JWT的内容增强器
////        delegates.add(jwtAccessTokenConverter);
////        enhancerChain.setTokenEnhancers(delegates);
//        //endpoints.tokenEnhancer(enhancerChain) //token扩展
//                //.accessTokenConverter(jwtAccessTokenConverter)
//                //.tokenStore(tokenStore) //tokenStore
//                //.authenticationManager(authenticationManager) //开启密码授权模式
//        endpoints.authenticationManager(authenticationManager)
//                .userDetailsService(userService); //对用户详细信息的检查，比如查看是否存在
//                //.allowedTokenEndpointRequestMethods(HttpMethod.OPTIONS, HttpMethod.GET, HttpMethod.POST);
//    }
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                .withClient("stu_sys")
//                .secret("123456")
//                .accessTokenValiditySeconds(3600)
//                .refreshTokenValiditySeconds(864000)
//                // .autoApprove(true) //自动授权配置
//                .scopes("all")
//                .authorizedGrantTypes("authorization_code","password");
//    }
//
//    @Override
//
//    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
//        //允许表单认证
//       // oauthServer.allowFormAuthenticationForClients();
//    }
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    /**
     * 使用密码模式需要配置
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(jwtTokenEnhancer); //配置JWT的内容增强器
        delegates.add(jwtAccessTokenConverter);
        enhancerChain.setTokenEnhancers(delegates);
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userService)
                .tokenEnhancer(enhancerChain) //token扩展
                .accessTokenConverter(jwtAccessTokenConverter)
                .tokenStore(tokenStore); //tokenStore
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("admin")//配置client_id
                .secret(passwordEncoder.encode("admin123456"))//配置client_secret
                .accessTokenValiditySeconds(3600)//配置访问token的有效期
                .refreshTokenValiditySeconds(864000)//配置刷新token的有效期
                .redirectUris("http://www.baidu.com")//配置redirect_uri，用于授权成功后跳转
                .scopes("all")//配置申请的权限范围
                .authorizedGrantTypes("authorization_code", "password");//配置grant_type，表示授权类型
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        //允许表单认证
        oauthServer.allowFormAuthenticationForClients();
    }
}
