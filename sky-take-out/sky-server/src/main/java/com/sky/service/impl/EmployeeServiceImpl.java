package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // TODO 需要进行md5加密，然后再进行比对
        password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        // done 已补全
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }
    /**
     * 新增员⼯
     *
     * @param employeeDTO
     */
    public void save(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();

        // DTO已经具备的属性可利用BeanUtils工具类直接拷贝
        BeanUtils.copyProperties(employeeDTO, employee);

        // 设置账号的状态，默认正常状态 1表示正常 0表示锁定
        employee.setStatus(StatusConstant.ENABLE);

        // 设置密码，默认密码123456，存到数据库里默认是加密后的密文
        String password = DigestUtils.md5DigestAsHex("123456".getBytes());
        employee.setPassword(password);

        // 设置当前记录的创建时间和修改时间   ，已经AOP，因此注释
//        LocalDateTime now = LocalDateTime.now();
//        employee.setCreateTime(now);
//        employee.setUpdateTime(now);
//        employee.setCreateUser(BaseContext.getCurrentId());
//        employee.setUpdateUser(BaseContext.getCurrentId());
        // TODO：在threadLocal中取值，并设置当前记录创建人id和修改人id
        /////////////////Start of the code ///////////////////
        Long currentId = BaseContext.getCurrentId();
        if (currentId == null) {
            // 如果ThreadLocal中没有值，说明可能是测试或特殊场景，使用默认值-1
            currentId = -1L;
            log.info("currentId为空");
        }
        //设置id
        employee.setCreateUser(currentId);
        employee.setUpdateUser(currentId);
        ///////////////// End of the code ///////////////////

        employeeMapper.insert(employee);
    }

    /**
     * 分⻚查询
     *
     * @param employeePageQueryDTO
     * @return
     */
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        // select * from employee limit 0,10
        //TODO: 使⽤PageHelper插件开始分⻚查询
        /////////////////Start of the code ///////////////////
        int pages = employeePageQueryDTO.getPage();
        int pageSize = employeePageQueryDTO.getPageSize();
        PageHelper.startPage(pages, pageSize);
        ///////////////// End of the code ///////////////////
        // 此处传的DTO对象，返回的是PageHelper封装好的Page对象
        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);//后续定义
        //从page对象中可以获取相关信息
        long total = page.getTotal();
        List<Employee> records = page.getResult();
        return new PageResult(total, records);
    }
    /**
     * 启⽤禁⽤员⼯账号
     *
     * @param status
     * @param id
     */
    public void startOrStop(Integer status, Long id) {
        // TODO: 使⽤建造者模式（Builder Pattern）来构造employee对象
        /////////////////Start of the code ///////////////////
        Employee employee = Employee.builder()
                .id(id)  // 设置要更新的员工ID
                .status(status)  // 设置新的状态
                .updateTime(LocalDateTime.now())  // 设置更新时间
                .updateUser(BaseContext.getCurrentId())  // 设置更新人（从线程局部变量获取当前用户ID）
                .build();
        ///////////////// End of the code ///////////////////
        employeeMapper.update(employee);
    }
    /**
     * 根据id查询员⼯
     *
     * @param id
     * @return
     */
    public Employee getById(Long id) {
        Employee employee = employeeMapper.getById(id);
    // 密码等信息如果是明⽂，通常需要重新编码后再返回给前端
        employee.setPassword("*********");
        return employee;
    }
    /**
     * 编辑员⼯信息
     *
     * @param employeeDTO
     */
    public void update(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.update(employee);
    }

}
