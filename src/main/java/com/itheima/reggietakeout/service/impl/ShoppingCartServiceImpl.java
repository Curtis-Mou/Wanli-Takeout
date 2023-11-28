package com.itheima.reggietakeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggietakeout.entity.ShoppingCart;
import com.itheima.reggietakeout.mapper.ShoppingCartMapper;
import com.itheima.reggietakeout.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>implements ShoppingCartService {
}
