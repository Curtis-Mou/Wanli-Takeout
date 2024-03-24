package com.CQUPT.wanlitakeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.CQUPT.wanlitakeout.entity.User;
import com.CQUPT.wanlitakeout.mapper.UserMapper;
import com.CQUPT.wanlitakeout.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>implements UserService {
}
