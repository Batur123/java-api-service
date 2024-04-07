package com.example.apiservice.aspect;

import com.example.apiservice.annotation.RateLimited;
import com.example.apiservice.middleware.RateLimitingMiddleware;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RateLimitingAspect {

    @Autowired
    private RateLimitingMiddleware rateLimitingMiddleware;

    @Around("@annotation(rateLimited)")
    public Object rateLimitedAdvice(ProceedingJoinPoint joinPoint, RateLimited rateLimited) throws Throwable {
        String ipAddress = ""; // Implement logic to get IP address

        int limit = rateLimited.limit();
        long windowInSeconds = rateLimited.windowInSeconds();

        if (rateLimitingMiddleware.isAllowed(ipAddress, limit, windowInSeconds)) {
            return joinPoint.proceed(); // Proceed with the original method invocation
        } else {
            throw new RuntimeException("Rate limit exceeded");
        }
    }
}
