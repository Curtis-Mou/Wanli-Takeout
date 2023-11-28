package com.itheima.reggietakeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggietakeout.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
