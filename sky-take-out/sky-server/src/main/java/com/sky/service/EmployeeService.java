package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);
    /**
     * 新增员⼯
     * @param employeeDTO
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * 分⻚查询
     * @param employeePageQueryDTO
     * @return
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);
    /**
     * 启⽤禁⽤员⼯账号
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);
    /**
     * 根据id查询员⼯
     * @param id
     * @return
     */
    Employee getById(Long id);
    /**
     * 编辑员⼯信息
     * @param employeeDTO
     */
    void update(EmployeeDTO employeeDTO);

}
