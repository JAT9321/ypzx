package com.jiao.pay.configuration;


import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.jiao.pay.properties.AlipayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlipayConfiguration {

    @Autowired
    private AlipayProperties alipayProperties;

    @Bean
    public AlipayClient alipayClient() {

        DefaultAlipayClient alipayClient = new DefaultAlipayClient(alipayProperties.getAlipayUrl(),
                alipayProperties.getAppId(),
                alipayProperties.getAppPrivateKey(),
                AlipayProperties.format,
                AlipayProperties.charset,
                alipayProperties.getAlipayPublicKey(),
                AlipayProperties.sign_type);
        return alipayClient;
    }
}
