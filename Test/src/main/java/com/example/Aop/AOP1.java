package com.example.Aop;

import org.aspectj.lang.ProceedingJoinPoint;

public class AOP1 {
    //执行前方法
    public void startTransaction(){
        System.out.println("    ====>begin ping... "); //2
    }

    //执行后方法
    public void commitTransaction(){
        System.out.println("    ====>finish ping... "); //4
    }

    //执行方法体
    public void around(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("    ====>around begin ping"); //1
        //调用process()方法才会真正的执行实际被代理的方法  也就是执行对应的方法
        joinPoint.proceed();

        System.out.println("    ====>around finish ping"); //3
    }
}
