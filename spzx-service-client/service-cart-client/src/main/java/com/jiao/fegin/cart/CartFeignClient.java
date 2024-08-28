package com.jiao.fegin.cart;


import com.jiao.model.entity.h5.CartInfo;
import com.jiao.model.vo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "service-cart")
public interface CartFeignClient {

    @GetMapping(value = "/api/order/cart/auth/getAllCkecked")
    public abstract List<CartInfo> getAllCkecked();


    @GetMapping(value = "/api/order/cart/auth/clearCart")
    public abstract Result deleteChecked();
}
