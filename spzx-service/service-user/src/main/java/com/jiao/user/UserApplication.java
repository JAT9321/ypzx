package com.jiao.user;


import com.jiao.annotaion.EnableUserWebMvcConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableUserWebMvcConfiguration
public class UserApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(UserApplication.class, args);
    }

}
