package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    //微信服务接⼝地址
    public static final String WX_LOGIN =
            "https://api.weixin.qq.com/sns/jscode2session";
    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;
    /**
     * 微信登录
     * @param userLoginDTO
     * @return
     */
    public User wxLogin(UserLoginDTO userLoginDTO) {
// 根据code码获取openid⽅法，下⾯单独定义
        String openid = getOpenid(userLoginDTO.getCode());
//判断openid是否为空，如果为空表⽰登录失败，抛出业务异常
        if(openid == null){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
//判断当前⽤⼾是否为新⽤⼾
        User user = userMapper.getByOpenid(openid);
//如果是新⽤⼾，⾃动完成注册
        if(user == null){
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);//后绪步骤实现
        }
//返回这个⽤⼾对象
        return user;
    }
    /**
     * 调⽤微信接⼝服务，获取微信⽤⼾的openid
     * @param code
     * @return
     */
    private String getOpenid(String code){
//TODO：封装4个参数到map集合中
        Map<String, String> map = new HashMap<>();
        map.put("appid",weChatProperties.getAppid());
        map.put("secret",weChatProperties.getSecret());
        map.put("js_code",code);
        map.put("grant_type","authorization_code");
//通过HttpClientUtil⼯具类发送请求
        String json = HttpClientUtil.doGet(WX_LOGIN, map);
//TODO：将字符串解析成JSON对象
        JSONObject jsonObject = JSON.parseObject(json);
//在JSON对象中获取openid的值
        String openid = jsonObject.getString("openid");
        return openid;
    }
}
