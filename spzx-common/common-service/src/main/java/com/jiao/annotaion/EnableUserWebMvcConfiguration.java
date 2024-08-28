package com.jiao.annotaion;

import com.jiao.config.UserWebMvcConfiguration;
import com.jiao.interceptor.UserLoginAuthInterceptor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Import(value = {UserLoginAuthInterceptor.class, UserWebMvcConfiguration.class})
public @interface EnableUserWebMvcConfiguration {

}
