package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    /**
     * 插入菜品/套餐分类
     * @param category
     *
     */
    @Insert("insert into category (id, type, name, sort, status, create_time, update_time, create_user, update_user)" +
            "values (#{id}, #{type}, #{name}, #{sort}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void insert(Category category);

    /**
     * 查询菜品
     * @param categoryPageQueryDTO
     */
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);
    /**
     * 删除菜品
     * @param id
     */
    void delete(Long id);

    /**
     * 修改分类
     * @param category
     */
    @AutoFill(value = OperationType.UPDATE)     //AOP切面编程，只要调用sql，执行，更新日期
    void update(Category category);

    /**
     * 根据类型查询分类
     * @param type
     */
    @Select("SELECT * FROM category where type = #{type} ")
    List<Category> list(Integer type);
}
