package com.itheima.reggietakeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggietakeout.entity.Orders;

public interface OrderService extends IService<Orders> {
    /**
     * 用户下单
     * */
    void submit(Orders orders);
}
