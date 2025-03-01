package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Controller
@RestController
@Slf4j
@RequestMapping("/admin/setmeal")
@Api(tags = "套餐相关接口")
public class SetmealController {
@Autowired
    private SetmealService setmealService;
@GetMapping("/{id}")
@ApiOperation("根据id查询套餐")
    public Result<SetmealVO> getById(@PathVariable Long id)
{
    log.info("根据id查询套餐：{}",id);
    SetmealVO setmealVO= setmealService.getById(id);
    return Result.success(setmealVO);
}
@GetMapping("/page")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO)
{
    log.info("分页查询套餐：{}",setmealPageQueryDTO);
    PageResult pageResult= setmealService.page(setmealPageQueryDTO);
            return Result.success(pageResult);
}
@PostMapping
    public Result save(@RequestBody SetmealDTO setmealDTO)
{
    log.info("新增套餐：{}",setmealDTO);
    setmealService.saveWithDish(setmealDTO);
    return Result.success();
}
@PutMapping
    public Result update(@RequestBody SetmealDTO setmealDTO)
{
    log.info("修改套餐：{}",setmealDTO);
    setmealService.update(setmealDTO);
    return Result.success();
}
@PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable Integer status,Long id)
{
    log.info("启用禁用套餐：{}",id);
    setmealService.startOrStop(status,id);
    return Result.success();
}
@DeleteMapping
    public Result deleteByIds(@RequestParam List<Long> ids)
{
    setmealService.deleteByIds(ids);
    return Result.success();
}
}
