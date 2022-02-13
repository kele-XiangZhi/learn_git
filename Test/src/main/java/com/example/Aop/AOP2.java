package com.example.Aop;

import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * 注解的形式添加的一个aop 不需要添加到xml中
 */
@Aspect  //切面容器 其实就是通过注解的形式默认他是个xml一样的 aop拦截
public class AOP2 {
    @Pointcut(value = "* com.example.*.GroupC.*pang(..)")
    public void point(){ //这个point类似一个ID的意思

    }

    @Before(value = "point()")  //aop前执行 也就是类似
    public void before(){
        System.out.println("----group 中方法 pang拦截前事件---- 2");
    }

    @AfterReturning(value = "point()") //执行后方法
    public void after(){
        System.out.println("----group 中方法 pang拦截后事件---- 4");
    }

    @Around(value = "point()")
    public void around(ProceedingJoinPoint joinPoint)throws Throwable{
        System.out.println("---aop的拦截点 未执行前第一步 ---- 1");
        joinPoint.proceed(); //拦截的方法执行的地方
        System.out.println("---aop的拦截点 在执行玩后直接第三步 ---- 3");
    }
}
