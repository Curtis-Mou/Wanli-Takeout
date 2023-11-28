package com.itheima.reggietakeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggietakeout.entity.DishFlavor;
import com.itheima.reggietakeout.mapper.DishFlavorMapper;
import com.itheima.reggietakeout.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>implements DishFlavorService {
}
