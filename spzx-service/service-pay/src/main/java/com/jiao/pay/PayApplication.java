package com.jiao.pay;


import com.jiao.annotaion.EnableUserTokenFeignInterceptor;
import com.jiao.annotaion.EnableUserWebMvcConfiguration;
import com.jiao.pay.properties.AlipayProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {
        "com.jiao.feign.order",
        "com.jiao.fegin.product"
})
@EnableConfigurationProperties(value = {AlipayProperties.class})
@EnableUserWebMvcConfiguration
@EnableUserTokenFeignInterceptor
public class PayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }


}
