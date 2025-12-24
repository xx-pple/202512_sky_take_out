package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    // 添加构造函数，用于验证是否被加载
    public GlobalExceptionHandler() {
        log.info("GlobalExceptionHandler初始化完成");
    }
    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(DuplicateKeyException ex){
        //Duplicate entry 'zhangsan' for key 'employee.idx_username'
        String errorMsg = ex.getMessage();

        if (errorMsg == null) {
            return Result.error("数据已存在");
        }

        // 按行分割错误消息
        String[] lines = errorMsg.split("\n");

        // 查找包含 "Duplicate entry" 的行
        for (String line : lines) {
            if (line.contains("Duplicate entry")) {
                // 使用正则提取用户名
                Pattern pattern = Pattern.compile("Duplicate entry\\s*'([^']+)'");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String username = matcher.group(1);
                    return Result.error(username + MessageConstant.ALREADY_EXISTS);
                }

                // 如果没有匹配到，尝试简单的单引号分割
                String[] parts = line.split("'");
                if (parts.length >= 2) {
                    // 用户名通常在第二个部分（索引1）
                    String username = parts[1];
                    return Result.error(username + MessageConstant.ALREADY_EXISTS);
                }
            }
        }

        // 如果所有方法都失败，返回通用错误
        return Result.error("数据已存在");
    }

}
