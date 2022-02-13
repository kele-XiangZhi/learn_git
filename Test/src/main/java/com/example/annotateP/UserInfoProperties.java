package com.example.annotateP;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "userinfo")
@Getter
@Setter
public class UserInfoProperties {

    private String name;
    private String id;

}
