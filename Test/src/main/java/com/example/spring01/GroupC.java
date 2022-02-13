package com.example.spring01;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//通过lombok创建的一个对象 lombok 是一个以注解方式堆方法内的字段自动添加set get方的工具类

@Component
@ConfigurationProperties("groupc")
@Data
public class GroupC {

    private int id;
    private String name;

}
