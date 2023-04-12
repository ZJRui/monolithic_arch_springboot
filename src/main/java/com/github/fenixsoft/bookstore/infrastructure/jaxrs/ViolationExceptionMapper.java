package com.github.fenixsoft.bookstore.infrastructure.jaxrs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.stream.Collectors;

@Provider
public class ViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    private static final Logger log = LoggerFactory.getLogger(ViolationExceptionMapper.class);

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        log.warn("客户端传入了校验结果为非法的数据", exception);
        String msg = exception.getConstraintViolations().stream().map(javax.validation.ConstraintViolation::getMessage).collect(Collectors.joining("；"));
        return CommonResponse.send(Response.Status.BAD_REQUEST, msg);
    }
}
