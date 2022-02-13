package com.example.annotateP;

import com.example.spring01.DepartmentB;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 通过java的代码装配bean
 */

@Configuration
public class annotateJAVA {
        @Bean
        public DepartmentB myArticle() {
                return new DepartmentB();
        }
}
