package com.itheima.reggietakeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggietakeout.dto.SetmealDto;
import com.itheima.reggietakeout.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    /**
     *新增套餐，保存套餐和菜品的关联消息
     * */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐，同时删除套餐和菜品的关联数据
     * */
    public void removeWithDish(List<Long> ids);
}
