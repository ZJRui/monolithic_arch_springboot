package com.github.fenixsoft.bookstore.domian.auth.service;

import com.github.fenixsoft.bookstore.domian.auth.AuthenticAccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.inject.Inject;

public class AuthenticAccountDetailService implements UserDetailsService {

    @Inject
    private AuthenticAccountRepository authenticAccountRepository;
    /**
     * 根据用户名查询用户角色、权限等信息
     * 如果用户名无法查询到对应的用户，或者权限不满足，请直接抛出{@link UsernameNotFoundException}，勿返回null
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authenticAccountRepository.findByUsername(username);
    }
}
