package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.result.Result;
import com.sky.vo.DishVO;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Mapper
public interface DishMapper{
    /**
     * 返回category分类中 菜品数量
     * @param categoryId
     * @return
     */
    @Select("SELECT COUNT(*) FROM dish WHERE category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);
    /**
     * 根据主键查询菜品
     * @param id
     * @return
     */
    @Select("SELECT * FROM dish where id = #{id}")
    Dish getById(Long id);

    @Delete("DELETE FROM dish where id = #{id}")
    void deleteById(Long id);

    /**
     * 根据id动态修改菜品数据
     *
     * @param dish
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);
    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */


    List<Dish> listById(Long categoryId);
    /**
     * 动态条件查询菜品
     * @param dish
     * @return
     */
    List<Dish> list(Dish dish);

    /**
     * 根据套餐id查询菜品
     * @param setmealId
     * @return
     */
    @Select("select a.* from dish a left join setmeal_dish b on a.id = b.dish_id where b.setmeal_id = #{setmealId}")
    List<Dish> getBySetmealId(Long setmealId);

}
