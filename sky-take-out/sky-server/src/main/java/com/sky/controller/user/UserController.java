package com.sky.controller.user;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.mapper.UserMapper;
import com.sky.properties.JwtProperties;
import com.sky.result.Result;
import com.sky.service.UserService;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/user")
@Api(tags = "C端⽤⼾相关接⼝")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private UserMapper userMapper;
    /**
     * 微信登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("微信登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){
        log.info("微信⽤⼾登录：{}",userLoginDTO.getCode());
//微信登录
        User user = userService.wxLogin(userLoginDTO);//后绪步骤实现
//为微信⽤⼾⽣成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(), claims);
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token)
                .build();

        return Result.success(userLoginVO);
    }
    /**
     * CachePut：将⽅法返回值放⼊缓存
     * value：缓存的名称，每个缓存名称下⾯可以有多个key
     * key：缓存的key
     */
    @PostMapping
    @CachePut(value = "userCache", key = "#user.id")//key的⽣成：userCache::1
    public User save(@RequestBody User user){
        userMapper.insert(user);
        return user;
    }
    /**
     * Cacheable：在⽅法执⾏前spring先查看缓存中是否有数据，如果有数据，则直接返回缓存数
     据；若没有数据， *调⽤⽅法并将⽅法返回值放到缓存中
     * value：缓存的名称，每个缓存名称下⾯可以有多个key
     * key：缓存的key
     */
    @GetMapping
    @Cacheable(cacheNames = "userCache",key="#id")
    public User getById(Long id){
        User user = userMapper.getByid(id);
        return user;
    }

    @DeleteMapping
    @CacheEvict(cacheNames = "userCache",key = "#id")//删除某个key对应的缓存数据
    public void deleteById(Long id){
        userMapper.deleteById(id);
    }

    @DeleteMapping("/delAll")
    @CacheEvict(cacheNames = "userCache",allEntries = true)//删除userCache下所有的缓存数据

    public void deleteAll(){
        userMapper.deleteAll();
    }
}