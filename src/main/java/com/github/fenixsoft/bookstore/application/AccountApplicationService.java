package com.github.fenixsoft.bookstore.application;


import com.github.fenixsoft.bookstore.domian.account.Account;
import com.github.fenixsoft.bookstore.domian.account.AccountRepository;
import com.github.fenixsoft.bookstore.infrastructure.utility.Encryption;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

@Named
/**
 * 注意下文是 javax的@Transactional
 * 与JTA相比，Spring的事务性注释带有额外的配置：
 *
 * 隔离 – Spring 通过隔离属性提供事务范围的隔离;但是，在 JTA 中，此功能仅在连接级别可用。
 * Propagation – 通过 Spring 中的 propagation 属性和 Java EE 中的 value 属性在两个库中都可用;Spring提供嵌套作为附加传播类型（事务7种传播机制）。
 * 只读 – 仅在Spring通过 readOnly 属性提供。
 * 超时 – 仅在Spring通过超时属性可用。
 * 回滚 – 两个注释都提供回滚管理;JTA 提供了 rollbackOn 和 dontRollbackOn 属性，而 Spring 提供了 rollbackFor 和 noRollbackFor，以及两个附加属性：rollbackForClassName 和 noRollbackForClassName。
 *
 * JTA 事务性注释适用于CDI管理的Bean和Java EE规范定义为托管Bean的类，而Spring的事务性注释仅适用于SpringBean。
 *
 * 同样值得注意的是，对JTA 1.2的支持是在Spring Framework 4.0中引入的。因此，我们可以在Spring应用程序中使用JTA事务注释。但是，反过来是不可能的，因为我们无法在Spring上下文之外使用Spring注释。
 *
 */
@Transactional
public class AccountApplicationService {

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private Encryption encryption;

    public void createAccount(Account account){
        account.setPassword(encryption.encode(account.getPassword()));
        accountRepository.save(account);
    }
    public Account findAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    public void updateAccount(Account account) {
        accountRepository.save(account);
    }

}
