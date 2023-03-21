package com.me.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.me.reggie.dto.DishDto;
import com.me.reggie.entity.Dish;

public interface DishService extends IService<Dish> {

    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表dish dish_flavor
    public void saveWithFlavor(DishDto dishDto);

    //根据id查询菜品信息和对应口味信息
    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);
}
