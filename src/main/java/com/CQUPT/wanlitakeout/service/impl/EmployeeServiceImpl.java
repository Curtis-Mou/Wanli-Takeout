package com.CQUPT.wanlitakeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.CQUPT.wanlitakeout.entity.Employee;
import com.CQUPT.wanlitakeout.mapper.EmployeeMapper;
import com.CQUPT.wanlitakeout.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService {

}
