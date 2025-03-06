package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OrderMapper {
    void insert(Orders orders);
    /**
     * 根据订单号查询订单
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);
    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);
@Select("select count(*) from orders where status=#{toBeConfirmed}")
    Integer countByStatus(Integer toBeConfirmed);
@Update("update orders set status=#{status} where id=#{id}")
    void changeStatusById(Long id, Integer status);
}
