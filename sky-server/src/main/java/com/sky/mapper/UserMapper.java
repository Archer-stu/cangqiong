package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface UserMapper {
    @Select("SELECT * from user where openid=#{openid}")
    public User getByOpenid(String openid);
    void insert(User user);
@Select("select * from user where id=#{userId}")
    User getById(Long userId);

    Integer countByMap(Map map);
}
