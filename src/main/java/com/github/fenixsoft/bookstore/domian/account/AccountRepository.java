package com.github.fenixsoft.bookstore.domian.account;


import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;


@CacheConfig(cacheNames = "repository.accounts")
public interface AccountRepository extends CrudRepository<Account,Integer> {



    @Override
    Iterable<Account> findAll();

    /**
     * @Cacheable(cacheNames="users")定义一个名为users的缓存： 调用方法的时候首先会总users缓存中查询匹配的缓存对象
     * 被注解的方法的返回值会被缓存
     *
     * @param username
     * @return
     */
    @Cacheable(key = "#username")
    Account findByUsername(String username);

    /**
     * 判断唯一性，用户名、邮箱、电话不允许任何一个重复
     * @param username
     * @param email
     * @param telephone
     * @return
     */
    boolean existsByUsernameOrEmailOrTelephone(String username, String email, String telephone);

    /**
     * 判断唯一性，用户名、邮箱、电话不允许任何一个重复
     */
    Collection<Account> findByUsernameOrEmailOrTelephone(String username, String email, String telephone);

    @Cacheable(key = "#username")
    boolean existsByUsername(String username);


    /**
     * @Caching是一个组合注解，可以同时定义多个缓存操作。可以为一个方法定义提供基于@Cachable @CacheEvict或者@CachePut注解的数组
     *
     * @CacheEvict从给定的缓存中移除一个值
     *
     *q:为什么这里的@CacheEvict没有指定cacheName?
     * a:因为@CacheConfig已经指定了默认的cacheName，所以这里不需要指定
     * ，spring cache 也支持使用 @CacheEvict 来删除缓存。@CacheEvict 就是一个触发器，在每次调用被它注解的方法时，
     * 就会触发删除它指定的缓存的动作。跟 @Cacheable 和 @CachePut 一样，@CacheEvict 也要求指定一个或多个缓存，也指定自定义一的缓存解析器和 key 生成器，也支持指定条件（condition 参数）。
     *
     * @param entity
     * @return
     * @param <S>
     */
    @Caching(evict = {
            @CacheEvict(key = "#entity.id"),
            @CacheEvict(key = "#entity.username")
    })
    <S extends Account> S save(S entity);

    @CacheEvict
    <S extends Account> Iterable<S> saveAll(Iterable<S> entities);

    @Cacheable(key = "#id")
    Optional<Account> findById(Integer id);

    @Cacheable(key = "#id")
    boolean existsById(Integer id);

    @CacheEvict(key = "#id")
    void deleteById(Integer id);

    @CacheEvict(key = "#entity.id")
    void delete(Account entity);

    @CacheEvict(allEntries = true)
    void deleteAll(Iterable<? extends Account> entities);

    @CacheEvict(allEntries = true)
    void deleteAll();






}
