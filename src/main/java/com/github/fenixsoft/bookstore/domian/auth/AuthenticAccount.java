package com.github.fenixsoft.bookstore.domian.auth;

import com.github.fenixsoft.bookstore.domian.account.Account;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

public class AuthenticAccount extends Account implements UserDetails {

    private Collection<GrantedAuthority> authorities = new HashSet<>();

    public AuthenticAccount() {
        super();
        authorities.add(new SimpleGrantedAuthority(Role.USER));
    }

    public AuthenticAccount(Account account) {
        this();
        BeanUtils.copyProperties(account, this);
        if(getId()==1){
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN));
        }
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
