package com.itheima.reggietakeout.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggietakeout.common.CustomException;
import com.itheima.reggietakeout.dto.SetmealDto;
import com.itheima.reggietakeout.entity.Setmeal;
import com.itheima.reggietakeout.entity.SetmealDish;
import com.itheima.reggietakeout.mapper.SetmealMapper;
import com.itheima.reggietakeout.service.SetmealDishService;
import com.itheima.reggietakeout.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;
    /**
     *新增套餐，保存套餐和菜品的关联消息
     * */
    @Transactional
    @Override
    public void saveWithDish(SetmealDto setmealDto) {
    //保存套餐基本信息，操作setmeal，执行insert操作
    this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) ->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        //保存套餐和菜品的关联信息，操作setmeal_dish,执行insert操作
        setmealDishService.saveBatch(setmealDishes);
    }

    /**
     * 删除套餐，同时删除套餐和菜品的关联数据
     * */
    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
    //查询套餐状态，确定是否能删除
        LambdaQueryWrapper<Setmeal> queryWrapper= new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);
        int count =this.count(queryWrapper);
        //如果不能删除，抛出一个业务异常
        if(count>0){
            throw new CustomException("套餐正在售卖中，无法删除！");
        }

    //如果可以删除，先删除套餐表中的数据 setmeal表
        this.removeByIds(ids);
    //删除关系表中的数据 setmeal_dish表
        LambdaQueryWrapper <SetmealDish>lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(lambdaQueryWrapper);
    }
}
