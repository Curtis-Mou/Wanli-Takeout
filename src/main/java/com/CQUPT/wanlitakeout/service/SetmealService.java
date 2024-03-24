package com.CQUPT.wanlitakeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.CQUPT.wanlitakeout.dto.SetmealDto;
import com.CQUPT.wanlitakeout.entity.Setmeal;

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

    /***
     * 修改操作
     */
    public void updateWithDish(SetmealDto setmealDto);
}
