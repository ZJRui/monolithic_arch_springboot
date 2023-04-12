package com.github.fenixsoft.bookstore.domian.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class OAuthClientDetailsService implements ClientDetailsService {
    /**
     * 客户端ID
     * 这里的客户端就是指本项目的前端代码
     */
    private static final String CLIENT_ID = "bookstore_frontend";
    /**
     * 客户端密钥
     * 在OAuth2协议中，ID是可以公开的，密钥应当保密，密钥用以证明当前申请授权的客户端是未被冒充的
     */
    private static final String CLIENT_SECRET = "bookstore_secret";
    @Inject
    PasswordEncoder passwordEncoder;

    private ClientDetailsService clientDetailsService;

    @PostConstruct
    public void init() throws Exception {
        InMemoryClientDetailsServiceBuilder builder = new InMemoryClientDetailsServiceBuilder();
        builder.withClient(CLIENT_ID)
                .secret(passwordEncoder.encode(CLIENT_SECRET))
                .scopes("BROWSER")
                .authorizedGrantTypes("password", "refresh_token");
        clientDetailsService = builder.build();
    }

    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        return clientDetailsService.loadClientByClientId(s);
    }
}
