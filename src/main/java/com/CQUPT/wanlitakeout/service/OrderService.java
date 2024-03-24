package com.CQUPT.wanlitakeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.CQUPT.wanlitakeout.entity.Orders;

public interface OrderService extends IService<Orders> {
    /**
     * 用户下单
     * */
    void submit(Orders orders);
}
