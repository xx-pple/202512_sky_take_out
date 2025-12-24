package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import javax.validation.constraints.Email;
import java.util.List;

public interface CategoryService {

    /**
     * 新建菜品套餐分类
     * @param categoryDTO
     */
    void save(CategoryDTO categoryDTO);

    /**
     * 分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    PageResult  pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 删除 by id
     * @param id
     */
    void delete(Long id);

    /**
     * 修改分类
     * @param categoryDTO
     */
    void update(CategoryDTO categoryDTO);

    /**
     * 修改启用禁用状态
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     *根据类型查询分类
     * @param type
     * @return
     */
    List<Category> list(Integer type);
}
