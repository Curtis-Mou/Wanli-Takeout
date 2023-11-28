package com.itheima.reggietakeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggietakeout.entity.Employee;
import com.itheima.reggietakeout.mapper.EmployeeMapper;
import com.itheima.reggietakeout.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService {

}
