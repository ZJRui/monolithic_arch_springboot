package com.github.fenixsoft.bookstore.domian.auth.provider;


import com.github.fenixsoft.bookstore.domian.auth.AuthenticAccount;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;

import javax.inject.Named;

@Named
public class PreAuthenticatedAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if(authentication.getPrincipal() instanceof UsernamePasswordAuthenticationToken){
            AuthenticAccount user = (AuthenticAccount) ((UsernamePasswordAuthenticationToken) authentication.getPrincipal()).getPrincipal();
            if(user.isEnabled()&&user.isAccountNonLocked()&&user.isAccountNonExpired()&&user.isCredentialsNonExpired()){
                return new PreAuthenticatedAuthenticationToken(user, "", user.getAuthorities());
            }else{
                throw new DisabledException("账户被禁用！");
            }
        }else{
            throw new PreAuthenticatedCredentialsNotFoundException("预验证失败，传上来的令牌是怎么来的？");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(PreAuthenticatedAuthenticationToken.class);
    }
}
