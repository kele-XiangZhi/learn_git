package com.example.spring01;

import com.example.annotateP.TestStarter;
import com.example.annotateP.UserInfoProperties;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 利用 公司 部门 小组 进行配置一个自动化装配
 * 并且设置为starter 也就是设置成别人可进行调用的一个
 *
 * 自动装在 并且配置一个starter  自动装在不说了 就是一个bean的自动加载 主要通过注解的方式 @ComponentScan
 * +配置一个属性文件 ConfigurationProperties 也就是说属性的java化
 * @EnableConfigurationProperties(HelloProperties.class)  指定属性加载文件
 *
 * 也就是初始化了一个属性文件 然后进行了配置 。
 */

@Configuration //配置类 表示的这是一个配置文件  配置文件配合@bean 就可以进行自动化的配置 java的装配
@ComponentScan("com.example.spring01")  //自动扫描对应的包 和同包下的文件 存在Component的就行 bean 也就是 注解的bean装配
@EnableConfigurationProperties(UserInfoProperties.class) //配置文件 和配置属性文件的 key 和value都放入到 了属性文件中  跟自动化配置一样
@ConditionalOnProperty //条件过滤
@AutoConfigureBefore //配置前事件
public class Test01 {

    private final  UserInfoProperties userInfoProperties;

    public Test01(UserInfoProperties userInfoProperties) {
        this.userInfoProperties = userInfoProperties;
    }

    @Bean
    @ConditionalOnMissingBean(TestStarter.class)
    @ConditionalOnProperty(prefix = "msg", value = "enabled", havingValue = "true")
    public TestStarter testStarter(){
        TestStarter testStarter = new TestStarter(userInfoProperties.getName(),userInfoProperties.getId());
        return testStarter;
    }


}
