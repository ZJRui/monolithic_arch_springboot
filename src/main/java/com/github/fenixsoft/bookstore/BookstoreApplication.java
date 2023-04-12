package com.github.fenixsoft.bookstore;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableCaching
@EnableGlobalMethodSecurity(prePostEnabled = true,jsr250Enabled = true)
/**
 * The jsr250Enabled property allows us to use the @RoleAllowed annotatio
 * The prePostEnabled property enables Spring Security pre/post annotations.
 */
public class BookstoreApplication {


    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }
}
