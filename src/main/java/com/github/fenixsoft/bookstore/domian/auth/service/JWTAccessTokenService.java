package com.github.fenixsoft.bookstore.domian.auth.service;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class JWTAccessTokenService extends DefaultTokenServices {


    @Inject
    public JWTAccessTokenService(JWTAccessToken accessToken, OAuthClientDetailsService oAuthClientDetailsService, AuthenticationManager authenticationManager) {
        //设置令牌的持久化容器
        setTokenStore(new JwtTokenStore(accessToken));
        setClientDetailsService(oAuthClientDetailsService);
        setAuthenticationManager(authenticationManager);
        setTokenEnhancer(accessToken);

        setAccessTokenValiditySeconds(60*60*3);
        setRefreshTokenValiditySeconds(60*60*24*15);
        setSupportRefreshToken(true);
        setReuseRefreshToken(true);
    }
}
