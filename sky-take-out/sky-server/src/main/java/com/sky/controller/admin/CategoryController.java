package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜品/套餐分类管理
 */
@RestController
@RequestMapping("/admin/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增菜品
     * @param categoryDTO
     */
    @PostMapping
    @ApiOperation("新增菜品分类")
    public Result<String> insert(@RequestBody CategoryDTO categoryDTO){
        log.info("新增菜品：{}",categoryDTO);
        categoryService.save(categoryDTO);//该⽅法后续步骤会定义
        return Result.success();
    }
    /**
     * 分页查询
     * @param categoryPageDTO
     */
    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageDTO){
        log.info("菜品分页查询，参数为：{}",categoryPageDTO);
        PageResult pageResult=categoryService.pageQuery(categoryPageDTO);
        return Result.success(pageResult);
    }

    /**
     *
     * @param categoryDTO
     * @return
     */
    @DeleteMapping
    @ApiOperation("菜品删除")
    public Result<String> delete(CategoryDTO categoryDTO){
        log.info("删除菜品: {}",categoryDTO);
        categoryService.delete(categoryDTO.getId());
        return Result.success();
    }

    /**
     *
     * @param categoryDTO
     * @return
     */
    @PutMapping()
    @ApiOperation("修改分类")
    public Result<String> update(@RequestBody CategoryDTO categoryDTO){
        log.info("修改分类: {}",categoryDTO);
        categoryService.update(categoryDTO);
        return Result.success();
    }

    /**
     *
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("修改启用禁用")
    public Result<String> startOrStop(@PathVariable("status") Integer status, Long id){
        log.info("修改启用禁用: status: {}, id: {}",status,id);
        categoryService.startOrStop(status,id);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    public Result<List<Category>> list(Integer type){
        log.info("根据类型查询分类: {}",type);
        List<Category> categories = categoryService.list(type);
        return Result.success(categories);
    }
}
