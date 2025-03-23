package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.OrderService;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private LocaleResolver localeResolver;

    @Override
    public TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> list = new ArrayList<>();
        List<Double> doubleList = new ArrayList<>();
        while(!begin.equals(end))
        {
            list.add(begin);
            begin=begin.plusDays(1);
        }
        list.add(begin);
        for (LocalDate date : list) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Map map =new HashMap<>();
            map.put("begin",beginTime);
            map.put("end",endTime);
            map.put("status", Orders.COMPLETED);
            Double amount = orderMapper.sumByMap(map);
            amount= amount==null?0.0:amount;
            doubleList.add(amount);
        }
        TurnoverReportVO turnoverReportVO = new TurnoverReportVO();
        turnoverReportVO.setDateList(StringUtils.join(list,","));
        turnoverReportVO.setTurnoverList(StringUtils.join(doubleList,","));
        return turnoverReportVO;
    }

    @Override
    public UserReportVO getUserStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> localDatelist = new ArrayList<>();
        List<Integer> totalUserList = new ArrayList<>();
        List<Integer> newUserList = new ArrayList<>();
        while(!begin.equals(end))
        {
            localDatelist.add(begin);
            begin=begin.plusDays(1);
        }
        localDatelist.add(begin);


        for (LocalDate date : localDatelist) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime  endTime = LocalDateTime.of(date, LocalTime.MAX);
            Map map =new HashMap<>();
            map.put("end",endTime);
            Integer totalUser = userMapper.countByMap(map);
            map.put("begin",beginTime);
            Integer newUser = userMapper.countByMap(map);
            totalUser = totalUser==null?0:totalUser;
            newUser = newUser==null?0:newUser;
            totalUserList.add(totalUser);
            newUserList.add(newUser);
        }
        UserReportVO userReportVO = new UserReportVO();
        userReportVO.setDateList(StringUtils.join(localDatelist,","));
        userReportVO.setNewUserList(StringUtils.join(newUserList,","));
        userReportVO.setTotalUserList(StringUtils.join(totalUserList,","));
        return userReportVO;
    }

    @Override
    public OrderReportVO getOrdersStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> localDatelist = new ArrayList<>();
        Integer totalOrderCount = 0;
        Integer validOrderCount = 0;
List<Integer> totalOrderCountList = new ArrayList<>();
List<Integer> validOrderCountList = new ArrayList<>();
        OrderReportVO orderReportVO = new OrderReportVO();
        while(!begin.equals(end))
        {
            localDatelist.add(begin);
            begin=begin.plusDays(1);
        }
        localDatelist.add(begin);

        for (LocalDate date : localDatelist) {
LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Map map =new HashMap<>();
            map.put("begin",beginTime);
            map.put("end",endTime);
            Integer totalOrderNumber = orderMapper.countByMap(map);
            map.put("status", Orders.COMPLETED);
            Integer validOrderNumber = orderMapper.countByMap(map);
            totalOrderNumber = totalOrderNumber==null?0:totalOrderNumber;
            validOrderNumber = validOrderNumber==null?0:validOrderNumber;
            totalOrderCount+=totalOrderNumber;
            validOrderCount+=validOrderNumber;
totalOrderCountList.add(totalOrderNumber);
validOrderCountList.add(validOrderNumber);
        }
        validOrderCountList.stream().reduce(Integer::sum);
        orderReportVO.setDateList(StringUtils.join(localDatelist,","));
        orderReportVO.setOrderCountList(StringUtils.join(totalOrderCountList,","));
        orderReportVO.setValidOrderCountList(StringUtils.join(validOrderCountList,","));
        orderReportVO.setTotalOrderCount(totalOrderCount);
        orderReportVO.setValidOrderCount(validOrderCount);
        if(totalOrderCount!=0)
        orderReportVO.setOrderCompletionRate(validOrderCount.doubleValue()/totalOrderCount.doubleValue());
        return orderReportVO;
    }

    @Override
    public SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end) {
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);
        SalesTop10ReportVO salesTop10ReportVO = new SalesTop10ReportVO();
        List<String> nameList = new ArrayList<>();
        List<Integer> numberList = new ArrayList<>();
        List<GoodsSalesDTO> goodsSalesDTOList = orderMapper.getSalesTop(beginTime,endTime);
        for (GoodsSalesDTO goodsSalesDTO : goodsSalesDTOList) {
        nameList.add(goodsSalesDTO.getName());
        numberList.add(goodsSalesDTO.getNumber());
        }

        salesTop10ReportVO.setNameList(StringUtils.join(nameList,","));
        salesTop10ReportVO.setNumberList(StringUtils.join(numberList,","));



        return salesTop10ReportVO;
    }
}
