package com.github.fenixsoft.bookstore.infrastructure.utility;


import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.inject.Named;
import java.util.Optional;

@Named
public class Encryption {


    /**
     * Spring Security提供了一个PasswordEncoder接口，它有一个encode()方法，该方法接受一个字符串并返回一个加密后的字符串。
     * 配置认证使用的密码加密算法：BCrypt
     * * 由于在Spring Security很多验证器中都要用到{@link PasswordEncoder}的加密，所以这里要添加@Bean注解发布出去
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public String encode(CharSequence rawPassword) {
        // noinspection SpringConfigurationProxyMethods
        return passwordEncoder().encode(Optional.ofNullable(rawPassword).orElse(""));
    }
}
