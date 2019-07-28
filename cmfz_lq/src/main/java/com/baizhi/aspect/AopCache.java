package com.baizhi.aspect;

import com.baizhi.Annotation.AopAnnocation;
import com.baizhi.utils.SerializeUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
/*
@Configuration
@Aspect
@Slf4j
public class AopCache {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Around("execution(* com.baizhi.service.*.*(..))")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature=(MethodSignature) proceedingJoinPoint.getSignature();
        AopAnnocation annotation = methodSignature.getMethod().getAnnotation(AopAnnocation.class);
        if(annotation==null){
            return proceedingJoinPoint.proceed();
        }
        String name = proceedingJoinPoint.getSignature().getName();
        String id = proceedingJoinPoint.getTarget().getClass().getName();
        Object[] args = proceedingJoinPoint.getArgs();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name);
        for (Object arg : args) {
            stringBuilder.append(arg);
        }
        String key = stringBuilder.toString();
        String o = (String)stringRedisTemplate.opsForHash().get(id, key);
        if(o==null){
            log.info("缓存中没有值,加入缓存");
            Object proceed = proceedingJoinPoint.proceed();
            log.info("加入缓存");
            stringRedisTemplate.opsForHash().put(id,key, SerializeUtils.serialize(proceed));
            return proceed;
        }else {
            log.info("缓存中有值直接拿");
            return SerializeUtils.serializeToObject(o);
        }
    }
    @AfterReturning("execution(* com.baizhi.service.*.*(..))")
    public void after(JoinPoint joinPoint){
        MethodSignature methodSignature=(MethodSignature) joinPoint.getSignature();
        AopAnnocation annotation = methodSignature.getMethod().getAnnotation(AopAnnocation.class);
        if(annotation==null){
            log.info("清除当前namespace所有缓存");
            String id = joinPoint.getTarget().getClass().getName();
            stringRedisTemplate.delete(id);
        }
    }
}
*/