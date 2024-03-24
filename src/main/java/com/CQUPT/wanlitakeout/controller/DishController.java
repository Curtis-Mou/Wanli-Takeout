package com.CQUPT.wanlitakeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.CQUPT.wanlitakeout.common.R;
import com.CQUPT.wanlitakeout.dto.DishDto;
import com.CQUPT.wanlitakeout.entity.Category;
import com.CQUPT.wanlitakeout.entity.Dish;
import com.CQUPT.wanlitakeout.entity.DishFlavor;
import com.CQUPT.wanlitakeout.service.CategoryService;
import com.CQUPT.wanlitakeout.service.DishFlavorService;
import com.CQUPT.wanlitakeout.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
@Slf4j
/**
 * 菜品管理
 * */
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private CategoryService categoryService;

/**
 * 新增菜品
 * */
@PostMapping
public R<String>save(@RequestBody DishDto dishDto){
    log.info(dishDto.toString());
    dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }
/**
 *菜品信息分页查询
 * */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
//构造分页构造器对象
        Page<Dish> pageInfo=new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage=new Page<>();
//条件构造器
        LambdaQueryWrapper<Dish> queryWrapper=new LambdaQueryWrapper<>();
//添加过滤条件
        queryWrapper.like(name != null,Dish::getName,name);
//添加排序条件
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(pageInfo,queryWrapper);
//对象拷贝
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");

        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list=records.stream().map((item) ->{
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(item,dishDto);
        Long categoryId = item.getCategoryId();//分类id
    //    根据id查询分类对象
        Category category=categoryService.getById(categoryId);

        if(category != null) {
            String categoryName = category.getName();
            dishDto.setCategoryName(categoryName);
        }


    return dishDto;
    }).collect(Collectors.toList());


        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);
    }

    /**
     * 根据id查询菜品信息和口味信息
     * */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    /**
     * 修改菜品
     * */
    @PutMapping
    public R<String>update(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());
        dishService.updateWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    /**
    * 根据条件查询对应的菜品
    * */
//    @GetMapping("/list")
//    public R<List<Dish>> list(Dish dish){
//        //构造查询条件
//        LambdaQueryWrapper<Dish>lambdaQueryWrapper=new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.eq(dish.getCategoryId() != null,Dish::getCategoryId,dish.getCategoryId());
//        //状态为1，表示起售状态
//        lambdaQueryWrapper.eq(Dish::getStatus,1);
//        //添加排序条件
//        lambdaQueryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
//        List<Dish> list = dishService.list(lambdaQueryWrapper);
//
//
//        return R.success(list);

    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        //构造查询条件
        LambdaQueryWrapper<Dish>lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(dish.getCategoryId() != null,Dish::getCategoryId,dish.getCategoryId());
        //状态为1，表示起售状态
        lambdaQueryWrapper.eq(Dish::getStatus,1);
        //添加排序条件
        lambdaQueryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(lambdaQueryWrapper);

        List<DishDto> dishDtoList=list.stream().map((item) ->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long categoryId = item.getCategoryId();//分类id
            //    根据id查询分类对象
            Category category=categoryService.getById(categoryId);

            if(category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }


            //当前菜品id
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor>queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(DishFlavor::getDishId,dishId);
            List<DishFlavor> dishFlavorList= dishFlavorService.list(queryWrapper);
            dishDto.setFlavors(dishFlavorList);

            return dishDto;
        }).collect(Collectors.toList());



        return R.success(dishDtoList);
    }

    /**
     * 批量起售
     * */
    @PostMapping("/status/1")
    public R<String>openStatus(long[]ids){
        for (long id:ids
             ) {
            Dish dish=dishService.getById(id);
            dish.setStatus(1);
            dishService.updateById(dish);
        }
        return R.success("修改成功！");
    }

    /**
     * 批量停售
     * */
    @PostMapping("/status/0")
    public R<String>closeStatus(long[]ids){
        for (long id:ids
             ) {
            Dish dish=dishService.getById(id);
            dish.setStatus(0);
            dishService.updateById(dish);
        }
        return R.success("修改成功！");
    }

    /**
     * 批量删除
     * */
    @DeleteMapping
    public R<String>deleteByIds(long[]ids){
        for (long id:ids
             ) {
            dishService.removeById(id);
        }
        return R.success("删除成功！");
    }
}
