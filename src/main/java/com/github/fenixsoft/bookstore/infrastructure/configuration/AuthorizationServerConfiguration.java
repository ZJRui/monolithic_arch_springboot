package com.github.fenixsoft.bookstore.infrastructure.configuration;

import com.github.fenixsoft.bookstore.domian.auth.service.AuthenticAccountDetailService;
import com.github.fenixsoft.bookstore.domian.auth.service.JWTAccessToken;
import com.github.fenixsoft.bookstore.domian.auth.service.JWTAccessTokenService;
import com.github.fenixsoft.bookstore.domian.auth.service.OAuthClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;


/**
 *spring security Oauth2授权服务器配置
 */
@Configuration
/**
 * 授权服务器
 */
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private JWTAccessTokenService jwtAccessTokenService;

    /**
     * Oauth2客户端信息服务
     */
    @Autowired
    private OAuthClientDetailsService oAuthClientDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticAccountDetailService authenticAccountDetailService;


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(oAuthClientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(authenticAccountDetailService)
                .tokenServices(jwtAccessTokenService)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        security.allowFormAuthenticationForClients().tokenKeyAccess("permitAll()").checkTokenAccess("permitAll()");
    }
}
