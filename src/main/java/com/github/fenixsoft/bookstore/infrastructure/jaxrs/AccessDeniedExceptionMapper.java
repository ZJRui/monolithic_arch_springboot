package com.github.fenixsoft.bookstore.infrastructure.jaxrs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.nio.file.AccessDeniedException;

@Provider
public class AccessDeniedExceptionMapper implements ExceptionMapper<AccessDeniedException> {

    private static final Logger log = LoggerFactory.getLogger(AccessDeniedExceptionMapper.class);

    @Context
    private HttpServletRequest request;
    @Override
    public Response toResponse(AccessDeniedException e) {
        log.warn("越权访问被禁止 {}: {}", request.getMethod(), request.getPathInfo());
        return CommonResponse.send(Response.Status.FORBIDDEN, e.getMessage());
    }
}
