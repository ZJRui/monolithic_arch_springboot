package com.github.fenixsoft.bookstore.domian.auth;


import com.github.fenixsoft.bookstore.domian.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticAccountRepository {

    @Autowired
    AccountRepository accountRepository;


    public  AuthenticAccount findByUsername(String username){
        return new AuthenticAccount(Optional.ofNullable(accountRepository.findByUsername(username)).orElseThrow(()->new UsernameNotFoundException("用户" + username + "不存在")));
    }

}
