package com.lyc.recruit.exception;

import com.lyc.recruit.common.ApiRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理统一异常的handler
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e) {
        log.error("Default Exception", e);
        return ApiRestResponse.error(RecruitExceptionEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(RecruitException.class)
    @ResponseBody
    public Object handleRecruitException(RecruitException e) {
        log.error("RecruitException", e);
        return ApiRestResponse.error(e.getCode(), e.getMessage());
    }


}
