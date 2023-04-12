package com.github.fenixsoft.bookstore.domian.account.validation;

import com.github.fenixsoft.bookstore.domian.account.Account;
import com.github.fenixsoft.bookstore.domian.account.AccountRepository;
import com.github.fenixsoft.bookstore.domian.auth.AuthenticAccount;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.function.Predicate;

public class AccountValidation<T extends Annotation> implements ConstraintValidator<T, Account> {


    @Inject
    protected AccountRepository repository;

    protected Predicate<Account> predicate = account -> true;

    @Override
    public boolean isValid(Account account, ConstraintValidatorContext constraintValidatorContext) {
        // 在JPA持久化时，默认采用Hibernate实现，插入、更新时都会调用BeanValidationEventListener进行验证
        // 而验证行为应该尽可能在外层进行，Resource中已经通过@Vaild注解触发过一次验证，这里会导致重复执行
        // 正常途径是使用分组验证避免，但@Vaild不支持分组，@Validated支持，却又是Spring的私有标签
        // 另一个途径是设置Hibernate配置文件中的javax.persistence.validation.mode参数为“none”，这个参数在Spring的yml中未提供桥接
        // 为了避免涉及到数据库操作的验证重复进行，在这里做增加此空值判断，利用Hibernate验证时验证器不是被Spring创建的特点绕开
        return repository==null || predicate.test(account);
    }

    public static class ExistsAccountValidator extends AccountValidation<ExistsAccount> {
        public void initialize(ExistsAccount constraintAnnotation) {
            predicate = c -> repository.existsById(c.getId());
        }
    }


    public static class AuthenticatedAccountValidator extends AccountValidation<AuthenticatedAccount> {
        public void initialize(AuthenticatedAccount constraintAnnotation) {
            predicate = c -> {
                //    经过spring security认证后 security会把一个SecurityContextImpl对象存储到session中，此对象中有当前用户的各种资料
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if ("anonymousUser".equals(principal)) {
                    return false;
                } else {
                    AuthenticAccount loginUser = (AuthenticAccount) principal;
                    return c.getId().equals(loginUser.getId());
                }
            };
        }
    }

    public static  class UniqueAccountValidator extends AccountValidation<UniqueAccount>{
        @Override
        public void initialize(UniqueAccount constraintAnnotation) {

            predicate=c->!repository.existsByUsernameOrEmailOrTelephone(c.getUsername(),c.getEmail(),c.getTelephone());
        }
    }

    public static class NotConflictAccountValidator extends AccountValidation<NotConflictAccount> {
        public void initialize(NotConflictAccount constraintAnnotation) {
            predicate = c -> {
                Collection<Account> collection = repository.findByUsernameOrEmailOrTelephone(c.getUsername(), c.getEmail(), c.getTelephone());
                // 将用户名、邮件、电话改成与现有完全不重复的，或者只与自己重复的，就不算冲突
                return collection.isEmpty() || (collection.size() == 1 && collection.iterator().next().getId().equals(c.getId()));
            };
        }
    }

}
