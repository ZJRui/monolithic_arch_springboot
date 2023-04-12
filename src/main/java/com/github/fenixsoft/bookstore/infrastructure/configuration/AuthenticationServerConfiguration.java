package com.github.fenixsoft.bookstore.infrastructure.configuration;

import com.github.fenixsoft.bookstore.domian.auth.provider.PreAuthenticatedAuthenticationProvider;
import com.github.fenixsoft.bookstore.domian.auth.provider.UsernamePasswordAuthenticationProvider;
import com.github.fenixsoft.bookstore.domian.auth.service.AuthenticAccountDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring Security用户认证服务器配置
 */
@Configuration
@EnableWebSecurity
public class AuthenticationServerConfiguration  extends WebSecurityConfiguration{
    /**
     * 注意这里 继承的是 项目中的 WebSecurityConfiguration
     */

    @Autowired
    private AuthenticAccountDetailService authenticAccountDetailService;

    @Autowired
    private UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

    @Autowired
    private PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 配置spring security的安全认证服务
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(authenticAccountDetailService).passwordEncoder(passwordEncoder);
        auth.authenticationProvider(usernamePasswordAuthenticationProvider);
        auth.authenticationProvider(preAuthenticatedAuthenticationProvider);
    }
}
