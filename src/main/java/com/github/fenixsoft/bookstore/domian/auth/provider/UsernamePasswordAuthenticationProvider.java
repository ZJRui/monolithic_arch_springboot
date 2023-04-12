package com.github.fenixsoft.bookstore.domian.auth.provider;

import com.github.fenixsoft.bookstore.domian.auth.service.AuthenticAccountDetailService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * 基于用域名、密码的身份认证器
 * 该身份认证器被AuthenticationManager验证管理器调用。 验证管理器支持多种验证方式，这里基于用户名密码的身份认证是方式之一
 */
@Named
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    @Inject
    private AuthenticAccountDetailService authenticAccountDetailService;
    @Inject
    private PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName().toLowerCase();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = authenticAccountDetailService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("密码不正确！");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
