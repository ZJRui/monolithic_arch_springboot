package com.github.fenixsoft.bookstore.infrastructure.configuration;

import com.github.fenixsoft.bookstore.domian.auth.service.JWTAccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import javax.annotation.security.RolesAllowed;

/**
 *
 * 资源服务器配置
 * <p>
 * 配置资源服务访问权限，主流有两种方式：
 * 一是在这里通过{@link HttpSecurity}的<code>antMatchers</code>方法集中配置
 * 二是启用全局方法级安全支持{@link EnableGlobalMethodSecurity} 在各个资源的访问方法前，通过注解来逐个配置，使用的注解包括有：
 * JSR 250标准注解{@link RolesAllowed}，可完整替代Spring的{@link Secured}功能
 * 以及可以使用EL表达式的Spring注解{@link PreAuthorize}、{@link PostAuthorize}
 *
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    private JWTAccessTokenService jwtAccessTokenService;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.csrf().disable();

        http.headers().frameOptions().disable();
        http.authorizeRequests().antMatchers("/oauth/**").permitAll();

    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenServices(jwtAccessTokenService);

    }
}
