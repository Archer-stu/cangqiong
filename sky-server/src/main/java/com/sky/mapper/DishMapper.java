package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);
@AutoFill(OperationType.INSERT)
    void insert(Dish dish);

    @Select("select * from dish where id=#{id}")
    Dish getById(Long id);
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);
@Delete("delete from dish where id=#{id}")
    void deleteById(Long id);

    void deleteByIds(List<Long> ids);
@AutoFill(OperationType.UPDATE)
    void update(Dish dish);
@Update("update dish set status=#{status} where id=#{id}")
    void updateStatusById(Integer status, Long id);
@Select("select * from dish where category_id=#{categoryId} and status=#{status}")
    List<Dish> list(Dish dish);
}
