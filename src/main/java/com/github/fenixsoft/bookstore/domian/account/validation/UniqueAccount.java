package com.github.fenixsoft.bookstore.domian.account.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE})
@Constraint(validatedBy = AccountValidation.UniqueAccountValidator.class)
public @interface UniqueAccount {

    String message() default "用户名|邮箱|手机号码已存在";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
