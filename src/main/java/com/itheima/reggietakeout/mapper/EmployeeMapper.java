package com.itheima.reggietakeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggietakeout.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
