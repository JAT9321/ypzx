package com.jiao.order;

import com.jiao.annotaion.EnableUserTokenFeignInterceptor;
import com.jiao.annotaion.EnableUserWebMvcConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {
        "com.jiao.fegin.cart"
        ,"com.jiao.fegin.user"
        ,"com.jiao.fegin.product"
})
@EnableUserTokenFeignInterceptor
@EnableUserWebMvcConfiguration
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}
