package com.MartaMaria.springdemo.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

	// setup logger
	private Logger myLogger = Logger.getLogger(getClass().getName());
	
	@Pointcut("execution(* com.MartaMaria.springdemo.controller.*.*(..))")
	private void forControllerPackage() {}
	
	@Pointcut("execution(* com.MartaMaria.springdemo.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* com.MartaMaria.springdemo.dao.*.*(..))")
	private void forDaoPackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
	private void forAppFlow() {}
	
	@Before("forAppFlow()")
	public void before(JoinPoint theJoinPoint) {
		
		String theCallingMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("===> in @Before: calling method: " + theCallingMethod);
		
		Object [] collectTheArguments = theJoinPoint.getArgs();
		for (Object tempArg: collectTheArguments) {
			myLogger.info("===> Argument: " + tempArg);
		}
	}
	
	@AfterReturning( pointcut="forAppFlow()",
					 returning="theResult"
					 )
	public void afterReturning(JoinPoint theJoinPoint, Object theResult) {
		
		String theMethodReturningFrom = theJoinPoint.getSignature().toShortString();
		myLogger.info("===> in @AfterReturning: from method: " + theMethodReturningFrom);
		
		myLogger.info("===> Data returned result: " + theResult);
		
	}
}
