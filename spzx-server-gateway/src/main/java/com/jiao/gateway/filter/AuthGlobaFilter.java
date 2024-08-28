package com.jiao.gateway.filter;


import com.alibaba.fastjson2.JSON;
import com.jiao.model.entity.user.UserInfo;
import com.jiao.model.vo.common.Result;
import com.jiao.model.vo.common.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Component
public class AuthGlobaFilter implements GlobalFilter, Ordered {


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        log.info("path {}", path);
        UserInfo userInfo = this.getUserInfo(request);
        //api接口，异步请求，校验用户必须登录
        if (antPathMatcher.match("/api/**/auth/**", path)) {
            if (userInfo == null) {
                ServerHttpResponse response = exchange.getResponse();
                return out(response, ResultCodeEnum.LOGIN_AUTH);
            }
        }
        return chain.filter(exchange);
    }

    private Mono<Void> out(ServerHttpResponse response, ResultCodeEnum loginAuth) {
        Result result = Result.build(null, loginAuth);
        byte[] resultBytes = JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(resultBytes);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

    // 过滤器优先级 0 代表最高
    @Override
    public int getOrder() {
        return 0;
    }


    private UserInfo getUserInfo(ServerHttpRequest request) {
        String token = "";
        List<String> tokenList = request.getHeaders().get("token");
        if (null != tokenList) {
            token = tokenList.get(0);
        }
        if (!StringUtils.isEmpty(token)) {
            String userInfoJSON = redisTemplate.opsForValue().get("user:spzx:" + token);
            if (StringUtils.isEmpty(userInfoJSON)) {
                return null;
            } else {
                return JSON.parseObject(userInfoJSON, UserInfo.class);
            }
        }
        return null;
    }
}
