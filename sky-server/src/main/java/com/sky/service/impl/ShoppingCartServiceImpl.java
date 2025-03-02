package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
@Autowired
    private ShoppingCartMapper shoppingCartMapper;
@Autowired
private SetmealMapper setmealMapper;
@Autowired
private DishMapper dishMapper;
    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());
        shoppingCart.setCreateTime(LocalDateTime.now());
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.list(shoppingCart);
    if(shoppingCartList.size()>0&&shoppingCartList!=null)
    {
        shoppingCart = shoppingCartList.get(0);
        shoppingCart.setNumber(shoppingCart.getNumber()+1);
        shoppingCartMapper.updateNumberById(shoppingCart);
    }
    else {
        Long setmealId=shoppingCart.getSetmealId();
        if(setmealId!=null)
        {
            SetmealVO setmealVO=setmealMapper.getById(setmealId);
        shoppingCart.setNumber(1);
        shoppingCart.setName(setmealVO.getName());
        shoppingCart.setImage(setmealVO.getImage());
        shoppingCart.setAmount(setmealVO.getPrice());
        }
        else {
            Dish dish = dishMapper.getById(shoppingCart.getDishId());
            shoppingCart.setNumber(1);
            shoppingCart.setName(dish.getName());
            shoppingCart.setImage(dish.getImage());
            shoppingCart.setAmount(dish.getPrice());
        }
        shoppingCartMapper.insert(shoppingCart);
    }

    }

    @Override
    public List<ShoppingCart> list() {
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = ShoppingCart.builder().userId(userId).build();
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.list(shoppingCart);
        return shoppingCartList;
    }

    @Override
    public void clean() {
        shoppingCartMapper.deleteByUserId(BaseContext.getCurrentId());
    }
}
