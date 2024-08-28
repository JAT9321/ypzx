package com.jiao;


import com.jiao.log.annotaion.EnableLogAspect;
import com.jiao.properties.MinioProperties;
import com.jiao.properties.UserAuthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(value = {UserAuthProperties.class, MinioProperties.class})
@EnableScheduling // 开启定时任务
@EnableLogAspect // 自定义的日志注解，为了让spring加载管理自定义的日志切面类
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }
}
