package com.CQUPT.wanlitakeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.CQUPT.wanlitakeout.common.R;
import com.CQUPT.wanlitakeout.entity.Employee;
import com.CQUPT.wanlitakeout.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
   /**
    * 登入功能
    * */
   @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
//      1. 将页面提交的密码password进行md5加密处理
       String password = employee.getPassword();
       password = DigestUtils.md5DigestAsHex(password.getBytes());

//      2. 根据页面提交的username查询数据库
       LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
       queryWrapper.eq(Employee::getUsername,employee.getUsername());
       Employee emp = employeeService.getOne(queryWrapper);

//      3. 如果没有查询到则返回登录失败结果
        if(emp==null){
            return R.error("用户名错误，登录失败！");
        }

//      4. 比对密码，不一致则返回失败结果
        if(!emp.getPassword().equals(password)){
            return R.error("密码错误，登录失败！");
        }

//      5. 查看员工状态，如果为已禁用状态，则返回员工已禁用结果
       if (emp.getStatus()==0){
           return R.error("账号已禁用！");
       }

//      6. 登录成功，将用户id存入Session并返回登录成功结果
       request.getSession().setAttribute("employee",emp.getId());
       return R.success(emp);

   }

   /**
    * 员工退出
    * */
   @PostMapping("/logout")
   public R<String> logout(HttpServletRequest request){
       //1.清除Session中保存的当前员工的id
       request.getSession().removeAttribute("employee");
       return R.success("退出成功！");
   }

   /**
    * 新增员工
    * */
@PostMapping
   public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
    log.info("新增员工，员工信息：{}",employee.toString());
    //设置初始密码123456，需要进行md5加密处理
    employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
   // employee.setCreateTime(LocalDateTime.now());
   // employee.setUpdateTime(LocalDateTime.now());

    //获得当前登录用户的id
    // long empId = (Long)request.getSession().getAttribute("employee");
    // employee.setCreateUser(empId);
    // employee.setUpdateUser(empId);
    employeeService.save(employee);
       return R.success("新增员工成功");
   }

   /**
    * 员工信息分页查询
    * */
   @GetMapping("/page")
   public R<Page> page(int page,int pageSize,String name){
       log.info("page={},pageSize={},name={}",page,pageSize,name);

//       构造分页构造器
       Page pageInfo=new Page(page,pageSize);

//       构造条件构造器
       LambdaQueryWrapper <Employee>queryWrapper = new LambdaQueryWrapper();
//       添加过滤条件
       queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
//       添加排序条件
       queryWrapper.orderByDesc(Employee::getUpdateTime);
//       执行查询
       employeeService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
   }

   @PutMapping
   public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        log.info(employee.toString());

       long id = Thread.currentThread().getId();
       log.info("线程id为{}",id);
       // Long empId=(Long) request.getSession().getAttribute("employee");
        //employee.setUpdateTime(LocalDateTime.now());
        //employee.setUpdateUser(empId);
        employeeService.updateById(employee);
        return R.success("员工信息修改成功！");
   }


   /**
    * 根据id查询员工
    * */
   @GetMapping("/{id}")
   public R<Employee> getById(@PathVariable Long id){
       log.info("根据id查询员工信息...");
       Employee employee = employeeService.getById(id);
       if(employee != null){
           return R.success(employee);
       }
       return R.error("没有查询到对应员工！");
   }

}
