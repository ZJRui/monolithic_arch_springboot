package com.github.fenixsoft.bookstore.domian.auth.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

@Named
public class JWTAccessToken  extends JwtAccessTokenConverter {


    // 签名私钥
    // 此处内容是我随便写的UUID，按照JWT约定默认是256Bit的，其实任何格式都可以，只是要注意保密，不要公开出去
    private static final String JWT_TOKEN_SIGNING_PRIVATE_KEY = "601304E0-8AD4-40B0-BD51-0B432DC47461";

    @Inject
    JWTAccessToken(UserDetailsService userDetailsService) {
        setSigningKey(JWT_TOKEN_SIGNING_PRIVATE_KEY);

        DefaultUserAuthenticationConverter converter=new DefaultUserAuthenticationConverter();
        converter.setUserDetailsService(userDetailsService);
        ((DefaultAccessTokenConverter) getAccessTokenConverter()).setUserTokenConverter(converter);
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        Authentication userAuthentication = authentication.getUserAuthentication();
        String[] authorities = userAuthentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new);
        Map<String, Object> payload = new HashMap<>();
        payload.put("authorities", authorities);
        payload.put("username", userAuthentication.getName());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(payload);
        return super.enhance(accessToken, authentication);

    }
}
