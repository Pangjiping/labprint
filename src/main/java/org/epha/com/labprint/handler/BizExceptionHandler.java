package org.epha.com.labprint.handler;

import lombok.extern.slf4j.Slf4j;
import org.epha.com.labprint.entity.BizException;
import org.epha.com.labprint.entity.ResponseData;
import org.epha.com.labprint.enums.ResponseEnum;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: YRH
 * @date: 2023/3/7
 */
@RestControllerAdvice
@Slf4j
public class BizExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseData<?> exceptionHandler(Exception e) {
        if (e instanceof BizException) {
            log.error("biz exec error, e = ", e);
            return ResponseData.create((BizException) e);
        }
        log.error("biz exec error, e = ", e);
        return ResponseData.create(ResponseEnum.UNEXPECTED);
    }
}
