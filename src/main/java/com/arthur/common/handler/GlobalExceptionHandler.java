package com.arthur.common.handler;

import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.http.HttpStatus;
import com.arthur.common.core.BaseResult;
import com.arthur.common.exception.BusinessException;
import com.arthur.common.exception.DemoModeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

/**
 * 全局异常处理
 * @author Arthur
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 处理运行时异常
    @ExceptionHandler(RuntimeException.class)
    public BaseResult handleRuntimeError(RuntimeException e) {
        return BaseResult.fail(HttpStatus.HTTP_INTERNAL_ERROR, e.getMessage());
    }

    // 处理运行时超时异常
    @ExceptionHandler(AsyncRequestTimeoutException.class)
    public BaseResult handleTimeoutError(AsyncRequestTimeoutException e) {
        return BaseResult.fail(HttpStatus.HTTP_GATEWAY_TIMEOUT, "接口请求超时");
    }

    // 处理自定义异常
    @ExceptionHandler(BusinessException.class)
    public BaseResult handleBusinessError(BusinessException e) {
        return BaseResult.fail(HttpStatus.HTTP_BAD_REQUEST, e.getMessage());
    }

    // 处理空指针异常
    @ExceptionHandler(NullPointerException.class)
    public BaseResult handleNullPointerException(NullPointerException e) {
        return BaseResult.fail(HttpStatus.HTTP_NOT_FOUND, "空指针异常");
    }

    // 处理参数校验异常
    @ExceptionHandler(ValidateException.class)
    public BaseResult handleValidateError(ValidateException e) {
        return BaseResult.fail(HttpStatus.HTTP_UNPROCESSABLE_ENTITY, e.getMessage());
    }

    // 处理请求方式异常
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseResult handleRequestMethodError(HttpRequestMethodNotSupportedException e) {
        return BaseResult.fail(HttpStatus.HTTP_BAD_REQUEST, "请求方式不支持");
    }

    // 处理其他未捕获异常
    @ExceptionHandler(Exception.class)
    public BaseResult handleServerError(Exception e) {
        log.error(e.getMessage());
        return BaseResult.fail(HttpStatus.HTTP_INTERNAL_ERROR, "服务器内部错误");
    }

    // 处理演示模式异常
    @ExceptionHandler(DemoModeException.class)
    public BaseResult handleDemoModeError(DemoModeException e) {
        return BaseResult.fail(HttpStatus.HTTP_FORBIDDEN, "演示模式，不允许操作");
    }

}
