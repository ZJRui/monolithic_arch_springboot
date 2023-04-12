package com.github.fenixsoft.bookstore.infrastructure.configuration;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@ApplicationPath("/restful")
public class JerseyConfiguration extends ResourceConfig {

    public JerseyConfiguration() {
        scanPackages("com.github.fenixsoft.bookstore.resource");
        scanPackages("com.github.fenixsoft.bookstore.infrastructure.jaxrs");


    }

    public void scanPackages(String scanPackage){
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Path.class));
        scanner.addIncludeFilter(new AnnotationTypeFilter(Provider.class));
        this.registerClasses(scanner.findCandidateComponents(scanPackage)
                .stream().map(beanDefinition -> ClassUtils.resolveClassName(Objects.requireNonNull(beanDefinition.getBeanClassName()), this.getClassLoader())).collect(Collectors.toSet()));

    }
}
