package com.CQUPT.wanlitakeout.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.CQUPT.wanlitakeout.common.BaseContext;
import com.CQUPT.wanlitakeout.common.R;
import com.CQUPT.wanlitakeout.entity.ShoppingCart;
import com.CQUPT.wanlitakeout.mapper.ShoppingCartMapper;
import com.CQUPT.wanlitakeout.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>implements ShoppingCartService {
public R<String> clean(){
    //用户对比
    LambdaQueryWrapper<ShoppingCart> queryWrapper=new LambdaQueryWrapper<>();
    queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
    //删除即可
    this.remove(queryWrapper);
    return R.success("清空成功");
}
}
