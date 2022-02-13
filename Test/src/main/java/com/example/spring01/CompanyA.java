package com.example.spring01;

import com.example.inter.ICompanyA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.Resource;

/**
 * 一个具有接口的实体类 用于区别验证 spring的根本原理 也就是外包装一层。通过接口关联。
 * 可以达到被调用不实现解耦的情况。同事也是事物死循环相互以来的解决原理。
 *
 * 有接口的代理类就是proxy3  没有的就会创建个类似的接口 cglie
 *
 *  spring装配 bean的方式主要有三种：
 *  1.注解
 *  通过声明@ComponentScan类 同包下的所有 注有@Component的类 在搭配类中 @Autowired 自动依赖-根绝类型装配，  @Resource 根绝name装配
 *  2.java代码
 *  @Configuration 搭配@bean的方法  也就是在方法中说明这是个配置文件， 在方法中bean注解说是一个bean
 *  3.xml的形式
 *  其实就是xml配置文件中配置一个bean的节点，指明要bean的类
 */



@ConfigurationProperties(prefix = "company")
public class CompanyA implements ICompanyA {
    // 注解的方式 默认加载bean。也就是加载departmentB的模型到contest
    @Autowired(required = true)
    DepartmentB depar;

    //以具体属性为指向的加载 某个bean数据  类似于一个select * from 表 where 表。属性=值 的一个数据列取出
    @Resource(name="groupc")
    GroupC groupc;

    //重写的注解
    @Override
    public void ping(){
        System.out.println(" --1-->再次执行ping-----");
    }
}
