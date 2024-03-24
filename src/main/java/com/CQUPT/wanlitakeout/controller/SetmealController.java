package com.CQUPT.wanlitakeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.CQUPT.wanlitakeout.common.R;
import com.CQUPT.wanlitakeout.dto.DishDto;
import com.CQUPT.wanlitakeout.dto.SetmealDto;
import com.CQUPT.wanlitakeout.entity.Category;
import com.CQUPT.wanlitakeout.entity.Dish;
import com.CQUPT.wanlitakeout.entity.Setmeal;
import com.CQUPT.wanlitakeout.entity.SetmealDish;
import com.CQUPT.wanlitakeout.service.CategoryService;
import com.CQUPT.wanlitakeout.service.SetmealService;
import com.CQUPT.wanlitakeout.service.impl.DishServiceImpl;
import com.CQUPT.wanlitakeout.service.impl.SetmealDishServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/*
* 套餐管理
* */
@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealDishServiceImpl setmealDishService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DishServiceImpl dishService;

    /**
     * 新增套餐
     * */
@PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
    log.info("套餐信息：{}",setmealDto);
   setmealService.saveWithDish(setmealDto);
    return R.success("新增套餐成功");
    }

/**
 * 套餐分页查询
 * */
    @GetMapping("/page")
    public R<Page>page(int page,int pageSize,String name){
        //构造分页构造器
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> dtoPage = new Page<>();
        //构造条件构造器
        LambdaQueryWrapper<Setmeal>queryWrapper=new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name),Setmeal::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        //执行查询
        setmealService.page(pageInfo,queryWrapper);
        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        List<Setmeal> records = pageInfo.getRecords();
       List<SetmealDto> list= records.stream().map((item) ->{
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);
            //分类id
            Long categoryId=item.getCategoryId();
            //根据分类id查询分类对象
            Category category = categoryService.getById(categoryId);
            if(category != null){
                //分类名称
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }

    /**
     * 删除套餐
     * */
    @DeleteMapping
    public R<String>delete(@RequestParam List<Long> ids){
        log.info("ids:{}",ids);
        setmealService.removeWithDish(ids);
        return R.success("套餐数据删除成功！");
    }

    /***
     * 修改套餐
     * @param setmeal
     * @return
     */



@GetMapping("/list")
    public R<List<Setmeal>> list( Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null,Setmeal::getCategoryId,setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null,Setmeal::getStatus,setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> list = setmealService.list(queryWrapper);

        return R.success(list);
    }

    @PostMapping("/status/0")
    public R<String>closeStatus(@RequestParam List<Long>ids){
        LambdaQueryWrapper<Setmeal> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids);
        List<Setmeal> setmeals=setmealService.list(queryWrapper);
        for (Setmeal setmeal:setmeals
             ) {
            setmeal.setStatus(0);
            setmealService.updateById(setmeal);
        }
        return R.success("修改成功！");
    }

    @PostMapping("/status/1")
    public R<String>openStatus(@RequestParam List<Long>ids){
        LambdaQueryWrapper<Setmeal> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids);
        List<Setmeal> setmeals=setmealService.list(queryWrapper);
        for (Setmeal setmeal:setmeals
             ) {
            setmeal.setStatus(1);
            setmealService.updateById(setmeal);
        }
        return R.success("修改成功！");
    }

    /**
     * 回显操作
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<SetmealDto> getById(@PathVariable Long id){
        // 我们需要把setmealDto返回回去，定义一个新的setmealDto用于保存数据
        SetmealDto setmealDto=new SetmealDto();
        // 将普通数据传入
        Setmeal setmeal=setmealService.getById(id);
        BeanUtils.copyProperties(setmeal,setmealDto);
        // 将菜品信息传递进去
        LambdaQueryWrapper<SetmealDish>queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish>list=setmealDishService.list(queryWrapper);
        setmealDto.setSetmealDishes(list);
        // 返回setmealDto即可
        return R.success(setmealDto);
    }

    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto){
        setmealService.updateById(setmealDto);
        return R.success("修改成功！");
    }

    @GetMapping("/dish/{id}")
    public R<List<DishDto>> dish(@PathVariable("id") Long SetmealId){
        LambdaQueryWrapper<SetmealDish>queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,SetmealId);
        //获得数据
        List<SetmealDish>list=setmealDishService.list(queryWrapper);
        List<DishDto>dishDtos=list.stream().map((setmealDish) -> {
            DishDto dishDto=new DishDto();
            //基本信息拷贝
            BeanUtils.copyProperties(setmealDish,dishDto);
            //设置其他信息
            Long dishId=setmealDish.getDishId();
            Dish dish = dishService.getById(dishId);
            BeanUtils.copyProperties(dish,dishDto);
            return dishDto;
        }).collect(Collectors.toList());
        return R.success(dishDtos);

    }
}
