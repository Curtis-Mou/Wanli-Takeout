package com.CQUPT.wanlitakeout.dto;

import com.CQUPT.wanlitakeout.entity.Setmeal;
import com.CQUPT.wanlitakeout.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
