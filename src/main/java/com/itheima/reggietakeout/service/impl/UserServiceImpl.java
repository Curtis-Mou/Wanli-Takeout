package com.itheima.reggietakeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggietakeout.entity.User;
import com.itheima.reggietakeout.mapper.UserMapper;
import com.itheima.reggietakeout.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>implements UserService {
}
