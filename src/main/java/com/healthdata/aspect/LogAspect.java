package com.healthdata.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.AfterThrowing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.healthdata.entity.Log;
import com.healthdata.mappers.LogMapper;

import java.time.LocalDateTime;

@Aspect
@Component
public class LogAspect {
    @Autowired
    private LogMapper logMapper;

    @Pointcut("execution(* com.healthdata.controller..*(..))")
    public void controllerMethods() {}

    @Around("controllerMethods()")
    public Object logUserOperation(ProceedingJoinPoint joinPoint) throws Throwable {
        Log log = new Log();
        log.setTime(LocalDateTime.now());
        log.setLevel("INFO");
        log.setSource("用户操作: " + joinPoint.getSignature().getDeclaringType().getSimpleName() + "." + joinPoint.getSignature().getName());
        log.setImportant(true);
        log.setDangerous(false);
        log.setStatus(false); // 0: 未处理
        log.setContent("用户执行了主要操作: " + joinPoint.getSignature().toShortString());
        logMapper.insertLog(log);
        return joinPoint.proceed();
    }

    @AfterThrowing(pointcut = "controllerMethods()", throwing = "ex")
    public void logException(ProceedingJoinPoint joinPoint, Throwable ex) {
        Log log = new Log();
        log.setTime(LocalDateTime.now());
        log.setLevel("ERROR");
        log.setSource("异常发生于: " + joinPoint.getSignature().getDeclaringType().getSimpleName() + "." + joinPoint.getSignature().getName());
        log.setImportant(false);
        log.setDangerous(true);
        log.setStatus(false); // 0: 未处理
        log.setContent("异常信息: " + ex.getMessage());
        logMapper.insertLog(log);
    }
}
