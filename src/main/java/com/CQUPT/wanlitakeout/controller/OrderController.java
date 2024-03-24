package com.CQUPT.wanlitakeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.CQUPT.wanlitakeout.common.BaseContext;
import com.CQUPT.wanlitakeout.common.R;
import com.CQUPT.wanlitakeout.dto.OrdersDto;
import com.CQUPT.wanlitakeout.entity.OrderDetail;
import com.CQUPT.wanlitakeout.entity.Orders;
import com.CQUPT.wanlitakeout.entity.ShoppingCart;
import com.CQUPT.wanlitakeout.entity.User;
import com.CQUPT.wanlitakeout.service.OrderService;
import com.CQUPT.wanlitakeout.service.impl.OrderDetailServiceImpl;
import com.CQUPT.wanlitakeout.service.impl.ShoppingCartServiceImpl;
import com.CQUPT.wanlitakeout.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    /**
     * 用户下单
     * */
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailServiceImpl orderDetailService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private ShoppingCartServiceImpl shoppingCartService;
    @PostMapping("/submit")
    public R<String>submit(@RequestBody Orders orders){
        log.info("订单数据：{}",orders);
        orderService.submit(orders);
        return R.success("下单成功！");
    }

    /***
     * 后台回显
     */
    @GetMapping("/page")
    public R<Page>pagePC(int page, int pageSize, Long number, Date beginTime, Date endTime){
        //制定基本Page
        Page<Orders> pageInfo = new Page<>(page,pageSize);;
        //定制带有名字的特殊Orders
       Page<OrdersDto>ordersDtoPage=new Page<>() ;
        //书写限制条件
        LambdaQueryWrapper<Orders> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(number != null,Orders::getId,number);
        if(beginTime != null && endTime !=null){
            queryWrapper.between(Orders::getOrderTime,beginTime,endTime);
        }
        orderService.page(pageInfo,queryWrapper);
        //普通赋值
        BeanUtils.copyProperties(pageInfo,ordersDtoPage,"records");
        //订单赋值
        List<Orders> records=pageInfo.getRecords();
        List<OrdersDto> ordersDtoList =records.stream().map((item) ->{
            //新创内部元素
            OrdersDto ordersDto=new OrdersDto();
            //普通值赋值
            BeanUtils.copyProperties(item,ordersDto);
            //特殊值赋值
            Long userId = item.getUserId();
            User user =userService.getById(userId);
            ordersDto.setUserName(user.getName());
            return ordersDto;
        }).collect(Collectors.toList());
        //完成dishDtoPage的result的内容封装
        ordersDtoPage.setRecords(ordersDtoList);
        return R.success(ordersDtoPage);
    }

    @PutMapping
    public R<String>beginSend(@RequestBody Orders orders){
        orderService.updateById(orders);
        return R.success("修改成功！");
    }

    @GetMapping("/userPage")
    public R<Page> pagePhone(int page,int pageSize){

        // 新创返回类型Page
        Page<Orders> pageInfo = new Page<>(page,pageSize);
        Page<OrdersDto> ordersDtoPage = new Page<>();

        // 用户ID
        Long currentId = BaseContext.getCurrentId();

        // 原条件写入
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getUserId,currentId);
        queryWrapper.orderByDesc(Orders::getOrderTime);

        orderService.page(pageInfo,queryWrapper);

        // 普通赋值
        BeanUtils.copyProperties(pageInfo,ordersDtoPage,"records");

        // 订单赋值
        List<Orders> records = pageInfo.getRecords();

        List<OrdersDto> ordersDtoList = records.stream().map((item) -> {

            // 新创内部元素
            OrdersDto ordersDto = new OrdersDto();

            // 普通值赋值
            BeanUtils.copyProperties(item,ordersDto);

            // 菜单详情赋值
            Long itemId = item.getId();

            LambdaQueryWrapper<OrderDetail> orderDetailLambdaQueryWrapper = new LambdaQueryWrapper<>();
            orderDetailLambdaQueryWrapper.eq(OrderDetail::getOrderId,itemId);

            int count = orderDetailService.count(orderDetailLambdaQueryWrapper);

            List<OrderDetail> orderDetailList = orderDetailService.list(orderDetailLambdaQueryWrapper);



            ordersDto.setOrderDetails(orderDetailList);

            return ordersDto;
        }).collect(Collectors.toList());

        // 完成dishDtoPage的results的内容封装
        ordersDtoPage.setRecords(ordersDtoList);

        return R.success(ordersDtoPage);
    }
    @PostMapping("/again")
    public R<String>againSubmit(@RequestBody Map<String,String>map){
        //获得ID
        String ids=map.get("id");
        long id =Long.parseLong(ids);
        //制作判断条件
        LambdaQueryWrapper<OrderDetail>queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDetail::getOrderId,id);
        //获取该订单对应的所有订单明细表
        List<OrderDetail>orderDetailList=orderDetailService.list(queryWrapper);
        //通过用户id把原来的购物车清空
        shoppingCartService.clean();
        //获取用户id
        Long userId=BaseContext.getCurrentId();
        //整体赋值
        List<ShoppingCart>shoppingCartList=orderDetailList.stream().map((item) ->{
            //以下均为赋值操作
            ShoppingCart shoppingCart=new ShoppingCart();
            shoppingCart.setUserId(userId);
            shoppingCart.setImage(item.getImage());

            Long dishId=item.getDishId();
            Long setmealId= item.getSetmealId();
            if(dishId != null){
                shoppingCart.setDishId(dishId);
            }else{
                shoppingCart.setSetmealId(setmealId);
            }
            shoppingCart.setName(item.getName());
            shoppingCart.setDishFlavor(item.getDishFlavor());
            shoppingCart.setNumber(item.getNumber());
            shoppingCart.setAmount(item.getAmount());
            shoppingCart.setCreateTime(LocalDateTime.now());
            return shoppingCart;
        }).collect(Collectors.toList());
        shoppingCartService.saveBatch(shoppingCartList);
        return R.success("操作成功");
    }
}
