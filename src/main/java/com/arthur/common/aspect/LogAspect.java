package com.arthur.common.aspect;

import com.alibaba.fastjson2.JSON;
import com.arthur.common.annotation.Log;
import com.arthur.common.utils.IpUtil;
import com.arthur.common.utils.ServletUtil;
import com.arthur.domain.SysOperateLog;
import com.arthur.service.SysOperateLogService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 操作日志切面
 * @author Arthur
 */
@Aspect
@Component
@AllArgsConstructor
public class LogAspect {

    private final SysOperateLogService service;

    /** 计算操作消耗时间 */
    private static final ThreadLocal<Long> TIME_THREADLOCAL = new NamedThreadLocal<>("Cost Time");

    /**
     * 处理请求前执行
     */
    @Before("@annotation(log)")
    public void doBefore(Log log) {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    /**
     * 处理完请求后执行
     */
    @AfterReturning(value = "@annotation(log)", returning = "jsonResult")
    public void doAfterThrowing(JoinPoint joinPoint, Log log, Object jsonResult) {
        record(joinPoint, log, null, jsonResult);
    }

    /**
     * 拦截异常请求
     */
    @AfterThrowing(value = "@annotation(log)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log log, final Exception e) {
        record(joinPoint, log, e, null);
    }

    public void record(JoinPoint joinPoint, Log log, final Exception e, Object jsonResult) {
        SysOperateLog operateLog = new SysOperateLog();
        operateLog.setBusinessType(log.type().ordinal());
        operateLog.setModule(log.module());
        operateLog.setDescription(log.desc());
        operateLog.setAction(ServletUtil.getRequest().getMethod());
        operateLog.setMethod(joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName());
//        operateLog.setOperator();
        operateLog.setIpaddr(IpUtil.getIpAddr());
        operateLog.setUrl(StringUtils.substring(ServletUtil.getRequest().getRequestURI(), 0, 255));
        operateLog.setParams(JSON.toJSONString(joinPoint.getArgs()[0]));
        operateLog.setResult(StringUtils.substring(JSON.toJSONString(jsonResult), 0, 2000));
        if (e != null) {
            operateLog.setStatus(0);
            operateLog.setError(StringUtils.substring(e.getMessage(), 0, 2000));
        }
        operateLog.setStatus(1);
        operateLog.setCreatedTime(new Date());
        operateLog.setCostTime(System.currentTimeMillis() - TIME_THREADLOCAL.get());

        service.save(operateLog);
    }

}
