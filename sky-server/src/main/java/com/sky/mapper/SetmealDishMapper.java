package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
List<Long>getSetmealIdsByDishIds(List<Long> ids);


    void insert(List<SetmealDish> list, Long setmealId);
@Select("select * from setmeal_dish where setmeal_id=#{setmealId}")
    List<SetmealDish> getBySetmealId(Long setmealId);
//@Delete("delete from setmeal_dish where setmeal_id=#{setmealId}")
//    void deleteBySetmealId(Long setmealId);

    void deleteBySetmealIds(List<Long> setmealIds);
}
