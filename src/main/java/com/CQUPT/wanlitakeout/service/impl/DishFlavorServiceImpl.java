package com.CQUPT.wanlitakeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.CQUPT.wanlitakeout.entity.DishFlavor;
import com.CQUPT.wanlitakeout.mapper.DishFlavorMapper;
import com.CQUPT.wanlitakeout.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>implements DishFlavorService {
}
