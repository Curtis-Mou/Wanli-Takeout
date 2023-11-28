package com.itheima.reggietakeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggietakeout.entity.OrderDetail;
import com.itheima.reggietakeout.mapper.OrderDetailMapper;
import com.itheima.reggietakeout.service.OrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>implements OrderDetailService {
}
