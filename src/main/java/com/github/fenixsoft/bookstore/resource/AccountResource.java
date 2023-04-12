package com.github.fenixsoft.bookstore.resource;


import com.github.fenixsoft.bookstore.application.AccountApplicationService;
import com.github.fenixsoft.bookstore.domian.account.Account;
import com.github.fenixsoft.bookstore.domian.account.validation.AuthenticatedAccount;
import com.github.fenixsoft.bookstore.domian.account.validation.NotConflictAccount;
import com.github.fenixsoft.bookstore.domian.account.validation.UniqueAccount;
import com.github.fenixsoft.bookstore.infrastructure.jaxrs.CommonResponse;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/accounts")
@Component
@CacheConfig(cacheNames = "resource.accounts")
@Produces("application/json")
@Consumes("application/json")
public class AccountResource {

    @Inject
    private AccountApplicationService service;
    /**
     *
     * Q:为什么@Cacheable没有指定cacheNames?
     * A:因为@CacheConfig已经指定了cacheNames，这里就不需要再指定了
     *
     * @param username
     * @return
     */
    @GET
    @Path("/{username}")
    @Cacheable(key="#username"  )
    public Account getUser(@PathParam("username")String username){
        return null;
    }

    @POST
    @CacheEvict(key="#account.username")
    public Response createUser(@Valid @UniqueAccount Account user){
        return CommonResponse.op(() -> service.createAccount(user));
    }
    /**
     * 更新用户信息
     */
    @PUT
    @CacheEvict(key = "#user.username")
    public Response updateUser(@Valid @AuthenticatedAccount @NotConflictAccount Account user) {
        return CommonResponse.op(() -> service.updateAccount(user));
    }
}
