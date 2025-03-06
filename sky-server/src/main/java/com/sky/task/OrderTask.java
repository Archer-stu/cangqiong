package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class OrderTask {
    @Autowired
    private OrderMapper orderMapper;
    @Scheduled(cron = "0 * * * * ?")
public void processTimeoutOrder(){
        log.info("定时处理超时订单{}", new Date());
        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);
        List<Orders> orders =orderMapper.getByStatusAndOrderTimeLT(time, Orders.PENDING_PAYMENT);
        for (Orders order : orders) {
            order.setStatus(Orders.CANCELLED);
            order.setCancelReason("订单超时，自动取消");
            order.setCancelTime(LocalDateTime.now());
            orderMapper.update(order);
        }

    }


 @Scheduled(cron = "0 0 2 * * ?")
   // @Scheduled(cron = "0/5 * * * * ?")

    public void processDeliveryOrder(){
        log.info("定时处理处于派送中状态的订单{}", new Date());
        LocalDateTime time = LocalDateTime.now().plusMinutes(-120);
        //LocalDateTime time = LocalDateTime.now().plusMinutes(1);

        List<Orders> orders =orderMapper.getByStatusAndOrderTimeLT(time, Orders.DELIVERY_IN_PROGRESS);
        for (Orders order : orders) {
            order.setStatus(Orders.COMPLETED);
            orderMapper.update(order);
        }
    }


}
