package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    /**
     * 根据openid查询⽤⼾
     * @param openid
     * @return
     */
    @Select("select * from user where openid = #{openid}")
    User getByOpenid(String openid);
    /**
     * 插⼊数据
     * @param
     */
    @Select("SELECT * FROM user where id = #{id}")
    User getByid(Long id);


    void insert(User user);

    /**
     *
     * @param id
     */
    @Delete("DELETE FROM user WHERE id = #{id}")
    void deleteById(Long id);

    /**
     *
     */
    @Delete("DELETE * FROM user")
    void deleteAll();

}
