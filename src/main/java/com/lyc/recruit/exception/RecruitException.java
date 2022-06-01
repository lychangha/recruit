package com.lyc.recruit.exception;

/**
 * 统一异常
 */
public class RecruitException extends RuntimeException {
    private final Integer code;
    private final String message;

    public RecruitException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public RecruitException(RecruitExceptionEnum exceptionEnum) {
        this(exceptionEnum.getCode(), exceptionEnum.getMsg());
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
