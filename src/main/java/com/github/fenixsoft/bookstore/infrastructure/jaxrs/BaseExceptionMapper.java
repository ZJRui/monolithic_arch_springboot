package com.github.fenixsoft.bookstore.infrastructure.jaxrs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BaseExceptionMapper  implements ExceptionMapper<Throwable>{

    private static final Logger log = LoggerFactory.getLogger(BaseExceptionMapper.class);

    @Override
    public Response toResponse(Throwable exception) {
        log.error(exception.getMessage(), exception);
        return CommonResponse.failure(exception.getMessage());
    }
}
