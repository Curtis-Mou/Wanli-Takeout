package com.itheima.reggietakeout.controller;

import com.itheima.reggietakeout.common.R;
import com.itheima.reggietakeout.entity.Orders;
import com.itheima.reggietakeout.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    /**
     * 用户下单
     * */
    @Autowired
    private OrderService orderService;
    @PostMapping("/submit")
    public R<String>submit(@RequestBody Orders orders){
        log.info("订单数据：{}",orders);
        orderService.submit(orders);
        return R.success("下单成功！");
    }
}
