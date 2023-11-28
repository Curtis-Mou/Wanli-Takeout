package com.itheima.reggietakeout.dto;

import com.itheima.reggietakeout.entity.Setmeal;
import com.itheima.reggietakeout.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
