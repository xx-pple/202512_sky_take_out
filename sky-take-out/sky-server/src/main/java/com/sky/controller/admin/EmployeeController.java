package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.CategoryDTO;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }
    /**
     * 新增员⼯
     * @param employeeDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增员⼯")
    // TODO1: 补充接收前端JSON类型参数注解
    public Result<String> save(@RequestBody EmployeeDTO employeeDTO){
        log.info("新增员⼯：{}",employeeDTO);
        employeeService.save(employeeDTO);//该⽅法后续步骤会定义
        return Result.success();
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("员⼯分⻚查询")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO){
        log.info("员⼯分⻚查询，参数为：{}", employeePageQueryDTO);
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);//后续定义
        return Result.success(pageResult);
    }
    /**
     * 启⽤禁⽤员⼯账号
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("启⽤禁⽤员⼯账号")
    // TODO: 使⽤注解完成路径参数与⽅法参数的绑定，⽽Query参数会根据参数名称⾃动绑定
    public Result<String> startOrStop(@PathVariable Integer status, Long id){
        log.info("启⽤禁⽤员⼯账号：{},{}",status,id);
        employeeService.startOrStop(status, id);//后绪步骤定义
        return Result.success();
    }
    /**
     * 根据id查询员⼯信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询员⼯信息")
    public Result<Employee> getById(@PathVariable Long id){
// 根据⽂档可知这⾥返回的是Entity对象，⽽不是VO对象
        Employee employee = employeeService.getById(id);
        return Result.success(employee);
    }
    /**
     * 编辑员⼯信息
     * @param employeeDTO
     * @return
     */
    @PutMapping
    @ApiOperation("编辑员⼯信息")
    public Result<String> update(@RequestBody EmployeeDTO employeeDTO){
        log.info("编辑员⼯信息：{}", employeeDTO);
        employeeService.update(employeeDTO);
        return Result.success();
    }

}
