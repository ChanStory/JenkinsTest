package com.myapp.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j//log객체 생성 생략
public class AroundAdvice {

	@Around("execution(* com.myapp.controller..*(..))")
	public Object logging(ProceedingJoinPoint pjp) throws Throwable {
		
		String targetClassName = pjp.getTarget().getClass().getName();
		String targetMethodName = pjp.getSignature().getName();

		if (log.isDebugEnabled()) {
			log.debug("start - {}.{}", targetClassName, targetMethodName);
		}

		Object returnValue = pjp.proceed();

		if (log.isDebugEnabled()) {
			log.debug("end - {}.{}", targetClassName, targetMethodName);
		}

		return returnValue;
	}
}
