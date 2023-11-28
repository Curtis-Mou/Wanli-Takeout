package com.itheima.reggietakeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggietakeout.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
