package com.CQUPT.wanlitakeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.CQUPT.wanlitakeout.entity.OrderDetail;
import com.CQUPT.wanlitakeout.mapper.OrderDetailMapper;
import com.CQUPT.wanlitakeout.service.OrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>implements OrderDetailService {
}
